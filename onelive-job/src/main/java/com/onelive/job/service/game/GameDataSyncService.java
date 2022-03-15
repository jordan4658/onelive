package com.onelive.job.service.game;

import com.onelive.common.mybatis.entity.GameCategory;

import java.util.List;

/**
 * 第三方游戏数据同步服务
 */
public interface GameDataSyncService {

    /**
     * 查询所有游戏分类
     * @return
     */
    List<GameCategory> listCategory();

    /**
     * 查询还有几个任务未执行
     * @param categoryId
     * @return
     */
    int countCategoryJobInFuture(Integer categoryId);

    /**
     * 生成第三方游戏记录同步任务数据
     * @param categoryId
     */
    void createJobData(Integer categoryId);

    /**
     * 查询并执行最新的第三方游戏记录同步任务
     * @param categoryId
     */
    void queryNewestJob(Integer categoryId);

    /**
     * 查询并执行过期或失败的第三方游戏记录同步任务
     */
    void checkExpSyncJob();

    /**
     * 同步第三方游戏列表
     * @param category
     */
    void syncGameList(GameCategory category);

    /**
     * 查询第三方游戏记录, 生成账变信息
     */
    void gameRecordCreateAccountChangeInfoJob();
}
