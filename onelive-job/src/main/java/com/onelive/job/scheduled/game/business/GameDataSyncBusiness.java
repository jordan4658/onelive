package com.onelive.job.scheduled.game.business;

import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.job.service.game.GameDataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameDataSyncBusiness {
    @Resource
    private GameDataSyncService gameDataSynService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 检测当前任务数据,未执行的数据是否小于200条,如果是则创建数据
     */
    public void createJobData() {
        log.info("====================createJobData start ======================");
        // 获取分布式写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("createJobData");
        // 写锁（等待时间1s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
        try {
            boolean bool = lock.writeLock().tryLock(3, 60, TimeUnit.SECONDS);
            if (bool) {
                //查询有几个游戏分类
                List<GameCategory> categoryList = gameDataSynService.listCategory();
                if (CollectionUtil.isNotEmpty(categoryList)) {
                    //查询分类对应的任务数据
                    categoryList.stream().forEach(category -> {
                        //查询该分类中未执行的任务数量
                        Integer categoryId = category.getCategoryId();
                        int count = gameDataSynService.countCategoryJobInFuture(categoryId);
                        if (count < 200) {
                            //创建该分类的任务数据
                            gameDataSynService.createJobData(categoryId);
                        }
                    });
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("---------------------------createJobData failure-!!!!!!!!!!-----------------");
        }finally {
            lock.writeLock().unlock();
        }
        log.info("====================createJobData end ======================");
    }


    /**
     * 查询最新任务,如果有,就执行
     *
     * @param categoryIdStr
     */
    public void gameDataSync(String categoryIdStr) {
        if (StringUtils.isBlank(categoryIdStr)) {
            return;
        }
        Integer categoryId = Integer.valueOf(categoryIdStr);
        gameDataSynService.queryNewestJob(categoryId);
    }

    /**
     * 查询已经过期, 但是没有执行成功的任务
     */
    public void checkExpSyncJob() {
        gameDataSynService.checkExpSyncJob();
    }


    public void gameSyncJob() {
        log.info("====================gameSyncJob start ======================");
        // 获取分布式写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("gameSyncJob");
        // 写锁（等待时间30s，超时时间120S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
        try {
            boolean bool = lock.writeLock().tryLock(30, 120, TimeUnit.SECONDS);
            if (bool) {
                //查询有几个游戏分类
                List<GameCategory> categoryList = gameDataSynService.listCategory();
                if (CollectionUtil.isNotEmpty(categoryList)) {
                    //查询分类对应的任务数据
                    categoryList.stream().forEach(category -> {
                        //查询该分类中未执行的任务数量
                        Integer categoryId = category.getCategoryId();
                        if(categoryId ==8001 || categoryId==8002){
                            return;
                        }
                        //同步游戏列表
                        gameDataSynService.syncGameList(category);
                    });
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("---------------------------gameSyncJob failure-!!!!!!!!!!-----------------");
        }finally {
            lock.writeLock().unlock();
        }
        log.info("====================gameSyncJob end ======================");
    }



    /**
     * 查询第三方游戏记录, 生成账变信息
     */
    public void gameRecordCreateAccountChangeInfoJob() {
        log.info("====================gameRecordCreateAccountChangeInfoJob start ======================");
        // 获取分布式写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock("gameRecordCreateAccountChangeInfoJob");
        // 写锁（等待时间1s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
        try {
            boolean bool = lock.writeLock().tryLock(5, 60, TimeUnit.SECONDS);
            if (bool) {
                gameDataSynService.gameRecordCreateAccountChangeInfoJob();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("---------------------------gameRecordCreateAccountChangeInfoJob failure-!!!!!!!!!!-----------------");
        }finally {
            lock.writeLock().unlock();
        }
        log.info("====================gameRecordCreateAccountChangeInfoJob end ======================");
    }
}
