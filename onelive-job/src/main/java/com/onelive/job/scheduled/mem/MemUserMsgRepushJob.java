package com.onelive.job.scheduled.mem;

import com.onelive.job.scheduled.mem.business.MemUserMsgRepushBusiness;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统消息重新推送给用户
 */
@Slf4j
@Component
public class MemUserMsgRepushJob {

    @Resource
    private MemUserMsgRepushBusiness business;

    /**
     * 检测系统消息, 如果有需要重新推送的消息, 再次推送给用户
     * @param param
     * @return
     */
    @XxlJob("repushUserMsgHandler")
    public ReturnT<String> repushUserMsgHandler(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + "开始执行任务");
            //检测系统消息, 重新推送用户消息
            business.repushUserMsg();
            XxlJobLogger.log(name + "执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + "执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }

}
