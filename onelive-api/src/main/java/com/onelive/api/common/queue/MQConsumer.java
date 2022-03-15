package com.onelive.api.common.queue;

import com.alibaba.fastjson.JSONObject;
import com.onelive.api.service.agent.AgentInviteCodeService;
import com.onelive.api.service.agent.AgentInviteRecordService;
import com.onelive.api.service.mem.*;
import com.onelive.api.service.operate.OperateMessageService;
import com.onelive.api.service.sys.SysShortMsgService;
import com.onelive.common.config.RabbitConfig;
import com.onelive.common.enums.SmsStatusEnum;
import com.onelive.common.enums.SmsThirdEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.sys.SendSmsDTO;
import com.onelive.common.model.vo.sms.SmsResultVo;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.service.sms.SmsSendUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName : MQConsumer
 * @Description : 消息队列消费者
 */
@Component
@Slf4j
public class MQConsumer {
    @Resource
    private SysShortMsgService sysShortMsgService;
    @Resource
    private AgentInviteRecordService agentInviteRecordService;
    @Resource
    private AgentInviteCodeService agentInviteCodeService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private MemUserEmpiricalValueService memUserEmpiricalValueService;
    @Resource
    private MemUserEmpiricalRecordService memUserEmpiricalRecordService;
    @Resource
    private MemLevelVipService memLevelVipService;
    @Resource
    private MemUserExpenseAmountService memUserExpenseAmountService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private OperateMessageService operateMessageService;
    @Resource
    private MemUserMessageService memUserMessageService;
    @Resource
    private MemUserMsgRepushRecordService repushRecordService;

    /**
     * @description: 接收短信信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_message", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.SMS_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"*.message"}
    ), containerFactory = "customContainerFactory")
    public void getSmsMessage(SendSmsDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("接收短信处理，msg:{}", JSONObject.toJSONString(dto));
        try {
            if (dto != null) {
                String areaCode = dto.getAreaCode();
                String tel = dto.getTel();
                String code = dto.getSmsCode();
                Long id = dto.getId();

                //发送短信
                SmsResultVo vo = SmsSendUtil.send(areaCode, tel, code, SmsSendUtil.validRange + "分钟", SmsThirdEnum.YunTongXin.getCode());
                //状态码不为空，则失败
                if (StringUtils.isNotBlank(vo.getCode())) {
                    SysShortMsg sysShortMsg = new SysShortMsg();
                    sysShortMsg.setMasStatus(SmsStatusEnum.fail.getCode());
                    //错误内容长度限制在 200内
                    if (StringUtils.isNotBlank(vo.getMsg()) && vo.getMsg().length() > 200) {
                        vo.setMsg(vo.getMsg().substring(0, 200));
                    }
                    sysShortMsg.setMasRemark(vo.getMsg());
                    sysShortMsg.setId(id);
                    sysShortMsgService.updateById(sysShortMsg);
                }
            }

        } catch (Exception e) {
            log.error("接收短信信息异常", e);
        } finally {
            //无论如何都默认成功
            try {
                channel.basicAck(tag, false);
            } catch (Exception e) {
                log.error("短信确认异常", e);
            }
        }
    }


    /**
     * 逻辑发生变化. 该方法暂时停用!!!
     *
     * @param id 需要更新信息的记录ID
     * @description: 更新处理新注册的用户的的邀请码信息
     */
    @Deprecated
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_invite_user", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.AGENT_INVITE_USER_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"invite_user"}
    ), containerFactory = "customContainerFactory")
    @Transactional
    public void updateInviteUserStatus(Long id, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("邀请码信息更新处理，id:{}", id);
        try {
            AgentInviteRecord inviteRecord = agentInviteRecordService.getById(id);
            if (inviteRecord != null) {
                int level = 1; //默认代理等级
                String inviteCode = inviteRecord.getInviteCode();

                //注册用户 系统生成的邀请码
                AgentInviteCode userInviteCodeObj = agentInviteCodeService.getSysInviteCodeByUserId(inviteRecord.getUserId());

                if (userInviteCodeObj != null) {
                    //注册用户使用的邀请码
                    AgentInviteCode inviteCodeObj = agentInviteCodeService.getOneByInviteCode(inviteCode);

                    if (inviteCodeObj != null) {
                        //查询到邀请用户的信息
                        MemUser inviteUser = memUserService.getById(inviteCodeObj.getUserId());
                        if (inviteUser != null) {
                            userInviteCodeObj.setInviteUserId(inviteUser.getId());
                            userInviteCodeObj.setInviteUserAccount(inviteUser.getUserAccount());
                        }
                        userInviteCodeObj.setRootUserId(inviteCodeObj.getRootUserId());
                        level = inviteCodeObj.getAgentLevel() + 1;
                    } else {
                        //如果没有邀请码的, 设置为平台邀请
                        userInviteCodeObj.setInviteUserId(0L);
                        userInviteCodeObj.setRootUserId(userInviteCodeObj.getUserId());
                    }
                    userInviteCodeObj.setAgentLevel(level);
                    //更新到数据库
                    agentInviteCodeService.updateById(userInviteCodeObj);
                }
            }
            //确认
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("更新状态异常", e);
        }
    }


    /**
     * @description: 经验值处理
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_empirical_value", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.EMPIRICAL_VALUE_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"*.empirical"}
    ), containerFactory = "customContainerFactory")
    @Transactional
    public void sumEmpirical(MemUserEmpiricalRecord dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("经验值处理，msg:{}", JSONObject.toJSONString(dto));
        try {
            if (dto != null) {
                Long userId = dto.getUserId();
                // 获取分布式写锁
                RReadWriteLock lock = redissonClient.getReadWriteLock("sumEmpirical" + userId);
                // 写锁（等待时间3s，超时时间60S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
                try {
                    boolean bool = lock.writeLock().tryLock(3, 60, TimeUnit.SECONDS);
                    if (bool) {
                        //查询用户的经验值
                        if (userId != null) {
                            MemUserEmpiricalValue userEmpiricalValue = memUserEmpiricalValueService.getByUserId(userId);
                            if (userEmpiricalValue == null) {
                                userEmpiricalValue = new MemUserEmpiricalValue();
                                userEmpiricalValue.setUserId(userId);
                            }
                            BigDecimal empiricalValue = userEmpiricalValue.getEmpiricalValue();
                            if (empiricalValue == null) {
                                empiricalValue = new BigDecimal("0");
                            }
                            empiricalValue = empiricalValue.add(dto.getEmpiricalValue());
                            userEmpiricalValue.setEmpiricalValue(empiricalValue);
                            //更新回数据库
                            memUserEmpiricalValueService.saveOrUpdate(userEmpiricalValue);
                            //查询用户当前等级
                            MemUser user = memUserService.getById(userId);
                            if (user != null) {
                                //查询经验值是否够升级下一个等级
                                Integer level = user.getUserLevel();
                                //查询当前经验值可升级的最大等级
                                MemLevelVip levelInfo = memLevelVipService.getMaxUpgradeableLevelByEmpirical(empiricalValue);
                                if (levelInfo != null) {
                                    //条件满足
                                    if (level < levelInfo.getLevelWeight()) {
                                        user.setUserLevel(levelInfo.getLevelWeight());
                                        //更新数据库
                                        memUserService.updateById(user);
                                        repushRecordService.addRecordByUser(user);
                                    }
                                }
                            }
                        }
                    }
                } catch (InterruptedException a) {
                    throw new BusinessException("获取锁失败");
                } finally {
                    lock.writeLock().unlock();
                }
                log.info("====================  sumEmpirical end  ======================");
            }
            //确认消息
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("数据异常", e);
        }
    }

    /**
     * @description: 统计消费总金额处理
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_expense_amount", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.EXPENSE_AMOUNT_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"*.expense"}
    ), containerFactory = "customContainerFactory")
    @Transactional
    public void sumExpense(MemUserExpenseRecord dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("统计消费总金额处理，msg:{}", JSONObject.toJSONString(dto));
        try {
            if (dto != null) {
                Long userId = dto.getUserId();
                // 获取分布式写锁
                RReadWriteLock lock = redissonClient.getReadWriteLock("sumExpense" + userId);
                // 写锁（等待时间3s，超时时间60S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
                try {
                    boolean bool = lock.writeLock().tryLock(3, 60, TimeUnit.SECONDS);
                    if (bool) {
                        //查询用户的消费总金额
                        if (userId != null) {
                            MemUserExpenseAmount memUserExpenseAmount = memUserExpenseAmountService.getByUserId(userId);
                            if (memUserExpenseAmount == null) {
                                memUserExpenseAmount = new MemUserExpenseAmount();
                                memUserExpenseAmount.setUserId(userId);
                            }
                            //查询用户当前消费总金额
                            BigDecimal totalAmount = memUserExpenseAmount.getAmount();
                            if (totalAmount == null) {
                                totalAmount = new BigDecimal("0");
                            }
                            //增加当前消费金额
                            totalAmount = totalAmount.add(dto.getAmount());
                            memUserExpenseAmount.setAmount(totalAmount);
                            //更新回数据库
                            memUserExpenseAmountService.saveOrUpdate(memUserExpenseAmount);
                            //增加经验值
                            memUserEmpiricalRecordService.addEmpiricalRecord(userId, dto.getAmount(), dto.getType());
                        }
                    }
                } catch (InterruptedException a) {
                    throw new BusinessException("获取锁失败");
                } finally {
                    lock.writeLock().unlock();
                }
                log.info("====================  sumExpense end  ======================");
            }
            //确认消息
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("数据异常", e);
        }
    }


    /**
     * @description: 用户信息发生变化, 重新检查系统消息是否需要推送给他
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_user_id_msg", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.REPUSH_USER_ID_MSG_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"repush_user_id_msg"}
    ), containerFactory = "customContainerFactory")
    @Transactional
    public void repushUserMsg(List<Long> userIds, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("用户信息发生变化，msg:{}", JSONObject.toJSONString(userIds));
        try {
            if (CollectionUtil.isNotEmpty(userIds)) {
                List<MemUserMsgRepushRecord> recordList = new LinkedList<>();
                for(Long userId : userIds){
                    MemUserMsgRepushRecord record = new MemUserMsgRepushRecord();
                    record.setUserId(userId);
                    recordList.add(record);
                }
                repushRecordService.saveAll(recordList);
            }
            //确认消息
            channel.basicAck(tag, false);
            log.info("====================  repushUserMsg end  ======================");
        } catch (Exception e) {
            log.error("数据异常", e);
        }
    }
    /**
     * @description: 用户信息发生变化, 重新检查系统消息是否需要推送给他
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "onelive_queue_user_info_msg", durable = "true", autoDelete = "false"), //队列持久化
            exchange = @Exchange(value = RabbitConfig.REPUSH_USER_INFO_MSG_EXCHANGE_TOPIC, type = ExchangeTypes.TOPIC),
            key = {"repush_user_info_msg"}
    ), containerFactory = "customContainerFactory")
    @Transactional
    public void repushUserInfoMsg(List<MemUser> users, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("用户信息发生变化，msg:{}", JSONObject.toJSONString(users));
        try {
            if (CollectionUtil.isNotEmpty(users)) {
                List<MemUserMsgRepushRecord> recordList = new LinkedList<>();
                for(MemUser user : users){
                    MemUserMsgRepushRecord record = new MemUserMsgRepushRecord();
                    record.setUserId(user.getId());
                    recordList.add(record);
                }
                repushRecordService.saveAll(recordList);
            }
            //确认消息
            channel.basicAck(tag, false);
            log.info("====================  repushUserMsg end  ======================");
        } catch (Exception e) {
            log.error("数据异常", e);
        }
    }

}
