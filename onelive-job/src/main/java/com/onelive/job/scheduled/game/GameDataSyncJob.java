package com.onelive.job.scheduled.game;

import com.onelive.job.scheduled.game.business.GameDataSyncBusiness;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 第三方游戏数据同步任务
 */
@Slf4j
@Component
public class GameDataSyncJob {

    @Resource
    private GameDataSyncBusiness business;

    /**
     * 生成任务数据(1分钟执行1次)
     * @param param
     * @return
     */
    @XxlJob("createJobDataHandler")
    public ReturnT<String> createJobData(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + " createJobDataHandler 开始执行任务");
            //检测当前任务数据,未执行的数据是否小于500条,如果是则创建数据
            business.createJobData();
            XxlJobLogger.log(name + " createJobDataHandler 执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + " createJobDataHandler 执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + " createJobDataHandler 执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }

    /**
     * 查询任务数据, 同步数据, 需要传入categoryId (3秒执行一次)
     */
    @XxlJob("gameDataSyncHandler")
    public ReturnT<String> gameDataSyncJob(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + " gameDataSyncHandler 开始执行任务");
            //检测当前任务数据,查询最新的一个任务执行
            business.gameDataSync(param);
            XxlJobLogger.log(name + " gameDataSyncHandler 执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + " gameDataSyncHandler 执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + " gameDataSyncHandler 执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }

    /**
     * 检测没有成功的任务, 再次同步(3秒执行一次)
     * @param param
     * @return
     */
    @XxlJob("checkSyncJobHandler")
    public ReturnT<String> checkSyncJob(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + " checkSyncJobHandler 开始执行任务");
            business.checkExpSyncJob();
            XxlJobLogger.log(name + " checkSyncJobHandler 执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + " checkSyncJobHandler 执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + " checkSyncJobHandler 执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }


    /**
     * 第三方游戏列表同步 (5分钟执行一次)
     * @param param
     * @return
     */
    @XxlJob("gameSyncJobHandler")
    public ReturnT<String> gameSyncJob(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + " gameSyncJobHandler 开始执行任务");
            business.gameSyncJob();
            XxlJobLogger.log(name + " gameSyncJobHandler 执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + " gameSyncJobHandler 执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + " gameSyncJobHandler 执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }

    /**
     * 第三方游戏记录生成账变信息 (1分钟执行一次)
     * @param param
     * @return
     */
    @XxlJob("gameRecordCreateAccountChangeInfoHandler")
    public ReturnT<String> gameRecordCreateAccountChangeInfoJob(String param) {
        try {
            String name = this.getClass().getName();
            XxlJobLogger.log(name + " gameRecordCreateAccountChangeInfoHandler 开始执行任务");
            business.gameRecordCreateAccountChangeInfoJob();
            XxlJobLogger.log(name + " gameRecordCreateAccountChangeInfoHandler 执行任务成功");
        } catch (Exception e) {
            XxlJobLogger.log(this.getClass().getName() + " gameRecordCreateAccountChangeInfoHandler 执行任务失败,原因:" + e.getMessage());
            log.error(this.getClass().getName() + " gameRecordCreateAccountChangeInfoHandler 执行任务失败,原因:" + e.getMessage(), e);
        }
        return  ReturnT.SUCCESS;
    }

}
