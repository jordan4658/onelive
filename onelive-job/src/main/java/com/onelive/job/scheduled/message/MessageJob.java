package com.onelive.job.scheduled.message;

import com.onelive.job.scheduled.message.business.MessageJobBusiness;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统消息推送给用户
 */
@Slf4j
@Component
public class MessageJob {

    @Resource
    private MessageJobBusiness business;

    /**
     * 生成用户数据(1分钟执行1次)
     * @param param
     * @return
     */
    @XxlJob("createUserMsgDataHandler")
    public ReturnT<String> createUserMsgData(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            //检测系统消息, 生成用户消息
            business.createUserMsgData();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }


    /**
     * 检测用户消息, 把没有推送过的消息推送给用户(1分钟推送一次)
     * @param param
     * @return
     */
    @XxlJob("pushUserMsgHandler")
    public ReturnT<String> pushUserMsg(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            //检测用户消息, 推送给用户
            business.pushUserMsg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }

}
