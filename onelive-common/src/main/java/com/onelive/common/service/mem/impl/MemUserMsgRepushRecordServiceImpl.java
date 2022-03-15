package com.onelive.common.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.config.RabbitConfig;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserMsgRepushRecord;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMsgRepushRecordMapper;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.utils.others.CollectionUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 用户消息重新推送记录 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-19
 */
@Service
public class MemUserMsgRepushRecordServiceImpl extends ServiceImpl<MemUserMsgRepushRecordMapper, MemUserMsgRepushRecord> implements MemUserMsgRepushRecordService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MemUserMsgRepushRecordMapper memUserMsgRepushRecordMapper;

    @Override
    public void addRecordByUser(MemUser user) {
        if (user != null && user.getId() != null) {
            MemUserMsgRepushRecord record = new MemUserMsgRepushRecord();
            record.setUserId(user.getId());
            this.save(record);
        }
    }

    @Override
    public void addRecordByUserIdList(List<Long> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            log.debug("------执行  sendUpdateInfoMQ --------");
            List<Long> sendList = new LinkedList<>();
            for(int i=0;i<list.size();i++){
                Long userId = list.get(i);
                sendList.add(userId);
                if(i!=0 && i%1000==0){
                    rabbitTemplate.convertAndSend(RabbitConfig.REPUSH_USER_ID_MSG_EXCHANGE_TOPIC, "repush_user_id_msg", sendList);
                    sendList = new LinkedList<>();
                }
            }
            if(sendList.size()>0) {
                rabbitTemplate.convertAndSend(RabbitConfig.REPUSH_USER_ID_MSG_EXCHANGE_TOPIC, "repush_user_id_msg", sendList);
            }
        }
    }
    @Override
    public void addRecordByUserList(List<MemUser> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            log.debug("------执行  sendUpdateInfoMQ --------");
            List<MemUser> sendList = new LinkedList<>();
            for(int i=0;i<list.size();i++){
                MemUser user = list.get(i);
                sendList.add(user);
                if(i!=0 && i%1000==0){
                    rabbitTemplate.convertAndSend(RabbitConfig.REPUSH_USER_INFO_MSG_EXCHANGE_TOPIC, "repush_user_info_msg", sendList);
                    sendList = new LinkedList<>();
                }
            }
            if(sendList.size()>0) {
                rabbitTemplate.convertAndSend(RabbitConfig.REPUSH_USER_INFO_MSG_EXCHANGE_TOPIC, "repush_user_info_msg", sendList);
            }
        }
    }


    @Override
    public void saveAll(List<MemUserMsgRepushRecord> list) {
        memUserMsgRepushRecordMapper.saveAll(list);
    }
}
