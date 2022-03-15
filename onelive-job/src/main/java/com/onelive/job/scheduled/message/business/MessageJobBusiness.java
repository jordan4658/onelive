package com.onelive.job.scheduled.message.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserMessage;
import com.onelive.common.mybatis.entity.OperateMessage;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.job.service.mem.MemUserMessageService;
import com.onelive.job.service.mem.MemUserService;
import com.onelive.job.service.mem.OperateMessageService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageJobBusiness {
    @Resource
    private OperateMessageService operateMessageService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private MemUserMessageService memUserMessageService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 检测系统消息, 生成用户消息
     */
    public void createUserMsgData() {
        log.info("==================== createUserMsgData start ======================");
        // 获取分布式写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("createUserMsgData");
        // 写锁（等待时间3s，超时时间60S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
        try {
            boolean bool = lock.writeLock().tryLock(3, 60, TimeUnit.SECONDS);
            if (bool) {
                //查询系统消息列表
                QueryWrapper<OperateMessage> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(OperateMessage::getIsDelete, false);
                List<OperateMessage> list = operateMessageService.list(queryWrapper);

                if (CollectionUtil.isNotEmpty(list)) {
                    for (OperateMessage msg : list) {
                        Long lastUserId = msg.getLastUserId();
                        if (lastUserId == null) {
                            lastUserId = 0L;
                        }
                        //接收类型 1层级 2等级 3地区 4账号
                        Integer receiveType = msg.getReceiveType();
                        String receiver = msg.getReceiver();
                        //查询一万个用户ID
                        QueryWrapper<MemUser> userQueryWrapper = new QueryWrapper<>();
                        LambdaQueryWrapper<MemUser> lambda = userQueryWrapper.lambda();
                        if(receiveType!=null && StrUtil.isNotBlank(receiver)){
                            String[] arr = receiver.split(SymbolConstant.COMMA);
                            if(receiveType==1){//1层级
                                List<Long> groupIds = Arrays.asList(arr).stream().map(Long::parseLong).collect(Collectors.toList());
                                lambda.in(MemUser::getGroupId,groupIds);
                            }
                            if(receiveType==2){//2等级
                                List<Integer> levelList = Arrays.asList(arr).stream().map(Integer::parseInt).collect(Collectors.toList());
                                lambda.in(MemUser::getUserLevel,levelList);
                            }
                            if(receiveType==3){//3地区
                                List<String> countryCodeList = Arrays.asList(arr);
                                lambda.in(MemUser::getRegisterCountryCode,countryCodeList);
                            }
                            if(receiveType==4){//4账号
                                lambda.in(MemUser::getUserAccount,arr);
                            }
                        }
                        lambda.gt(MemUser::getId, lastUserId).select(MemUser::getId).orderByAsc(MemUser::getId).last(" LIMIT 10000");
                        List<MemUser> userList = memUserService.list(userQueryWrapper);
                        if (CollectionUtil.isNotEmpty(userList)) {
                            List<MemUserMessage> msgList = new LinkedList<>();
                            QueryWrapper<MemUserMessage> countWrapper = new QueryWrapper<>();
                            Long msgId = msg.getId();
                            countWrapper.lambda().eq(MemUserMessage::getMsgId, msgId);
                            Long newLastUserId = null;
                            for (MemUser user : userList) {
                                //查询该用户是否已经收到该消息了
                                Long userId = user.getId();
                                countWrapper.lambda().eq(MemUserMessage::getUserId, userId);
                                int count = memUserMessageService.count(countWrapper);
                                if (count == 0) {
                                    MemUserMessage userMessage = new MemUserMessage();
                                    userMessage.setUserId(userId);
                                    userMessage.setMsgId(msgId);
                                    userMessage.setMsgType(msg.getMsgType());
                                    userMessage.setCreateTime(new Date());
                                    msgList.add(userMessage);
                                    newLastUserId = userId;
                                }
                            }
                            if (msgList.size() > 0) {
                                //手动批量插入
                                memUserMessageService.saveAll(msgList);
                                //更新消息中的最后更新
                                msg.setLastUserId(newLastUserId);
                                operateMessageService.updateById(msg);
                            }
                        }
                    }
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("---------------------------createUserMsgData failure-!!!!!!!!!!-----------------");
        } finally {
            lock.writeLock().unlock();
        }
        log.info("====================  createUserMsgData end  ======================");
    }

    /**
     * 检测用户未推送的消息, 推送给用户
     */
    public void pushUserMsg() {
        log.info("==================== pushUserMsg start ======================");
        // 获取分布式写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("pushUserMsg");
        // 写锁（等待时间3s，超时时间60S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
        try {
            boolean bool = lock.writeLock().tryLock(3, 60, TimeUnit.SECONDS);
            if (bool) {
                QueryWrapper<MemUserMessage> queryWrapper=new QueryWrapper<>();
                queryWrapper.lambda().eq(MemUserMessage::getIsDelete,false).eq(MemUserMessage::getIsPush,false).last(" LIMIT 10000");
                List<MemUserMessage> list = memUserMessageService.list(queryWrapper);
                if(CollectionUtil.isNotEmpty(list)){
                    list.stream().forEach(msg->{
                        // todo 第三方推送逻辑
                        msg.setIsPush(true);
                        memUserMessageService.updateById(msg);
                    });
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("---------------------------pushUserMsg failure-!!!!!!!!!!-----------------");
        } finally {
            lock.writeLock().unlock();
        }
        log.info("====================  pushUserMsg end  ======================");
    }
}
