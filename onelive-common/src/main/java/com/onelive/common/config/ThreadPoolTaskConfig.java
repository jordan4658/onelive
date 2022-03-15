package com.onelive.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName : ThreadPoolTaskConfig
 * @Description :
 * 1. 当一个任务被提交到线程池时，首先查看线程池的核心线程是否都在执行任务，否就选择一条线程执行任务，是就执行第二步。
 * 2. 查看核心线程池是否已满，不满就创建一条线程执行任务，否则执行第三步。
 * 3. 查看任务队列是否已满，不满就将任务存储在任务队列中(SynchronousQueue同步队直接执行第四步)，否则执行第四步。
 * 4. 查看线程池是否已满，不满就创建一条线程执行任务，否则就按照策略处理无法执行的任务。
 * @Author : muyu
 * @Date: 2020-06-26 20:41
 */
@Configuration
public class ThreadPoolTaskConfig {

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core);//设置核心线程数
        executor.setMaxPoolSize(core * 2 + 1);//设置最大线程数
        executor.setKeepAliveSeconds(3);//除核心线程外的线程存活时间
        executor.setQueueCapacity(40);//如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setThreadNamePrefix("thread-execute");//线程名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//设置拒绝策略
        return executor;
    }

}
