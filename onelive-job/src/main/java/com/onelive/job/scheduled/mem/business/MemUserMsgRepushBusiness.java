package com.onelive.job.scheduled.mem.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserMessage;
import com.onelive.common.mybatis.entity.MemUserMsgRepushRecord;
import com.onelive.common.mybatis.entity.OperateMessage;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.job.service.mem.MemUserMessageService;
import com.onelive.job.service.mem.MemUserService;
import com.onelive.job.service.mem.OperateMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务类
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemUserMsgRepushBusiness {

    @Resource
    private MemUserMsgRepushRecordService repushRecordService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private OperateMessageService operateMessageService;
    @Resource
    private MemUserMessageService memUserMessageService;

    /**
     * 检测是否需要重新推送用户信息的记录
     */
    public void repushUserMsg() {
        QueryWrapper<MemUserMsgRepushRecord> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.lambda().eq(MemUserMsgRepushRecord::getStatus, 0).last(" LIMIT 1000");
        List<MemUserMsgRepushRecord> recordList = repushRecordService.list(recordQueryWrapper);
        if (CollectionUtil.isNotEmpty(recordList)) {
            //查询系统中的消息
            QueryWrapper<OperateMessage> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(OperateMessage::getIsDelete, false);
            List<OperateMessage> list = operateMessageService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                for (MemUserMsgRepushRecord record : recordList) {
                    Long userId = record.getUserId();
                    MemUser memUser = memUserService.getById(userId);
                    if (memUser != null) {
                        for (OperateMessage msg : list) {
                            //接收类型 1层级 2等级 3地区 4账号
                            Integer receiveType = msg.getReceiveType();
                            String receiver = msg.getReceiver();
                            boolean needPush = false;
                            //查询一万个用户ID
                            if (receiveType != null && StrUtil.isNotBlank(receiver)) {
                                String[] arr = receiver.split(SymbolConstant.COMMA);
                                if (receiveType == 1) {//1层级
                                    List<Long> groupIds = Arrays.asList(arr).stream().map(Long::parseLong).collect(Collectors.toList());
                                    Long groupId = memUser.getGroupId();
                                    if (groupIds.contains(groupId)) {
                                        needPush = true;
                                    }
                                }
                                if (receiveType == 2) {//2等级
                                    List<Integer> levelList = Arrays.asList(arr).stream().map(Integer::parseInt).collect(Collectors.toList());
                                    Integer userLevel = memUser.getUserLevel();
                                    if (levelList.contains(userLevel)) {
                                        needPush = true;
                                    }
                                }
                                if (receiveType == 3) {//3地区
                                    List<String> countryCodeList = Arrays.asList(arr);
                                    String countryCode = memUser.getRegisterCountryCode();
                                    if (StringUtils.isNotBlank(countryCode) && countryCodeList.contains(countryCode)) {
                                        needPush = true;
                                    }
                                }
                                if (needPush) {
                                    //先查询是否已经有这条消息存在了
                                    QueryWrapper<MemUserMessage> countWrapper = new QueryWrapper<>();
                                    Long msgId = msg.getId();
                                    countWrapper.lambda().eq(MemUserMessage::getMsgId, msgId);
                                    countWrapper.lambda().eq(MemUserMessage::getUserId, userId);
                                    int count = memUserMessageService.count(countWrapper);
                                    if (count == 0) {
                                        MemUserMessage userMessage = new MemUserMessage();
                                        userMessage.setUserId(userId);
                                        userMessage.setMsgId(msgId);
                                        userMessage.setMsgType(msg.getMsgType());
                                        userMessage.setCreateTime(new Date());
                                        memUserMessageService.save(userMessage);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
