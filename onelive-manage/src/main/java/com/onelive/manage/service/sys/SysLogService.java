package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.log.LogQueryParam;
import com.onelive.common.mybatis.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-02
 */
public interface SysLogService extends IService<SysLog> {

    @Async
    void saveLog(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log);

    /**
     * 按条件分页查询系统日志
     *
     * @param param
     * @return
     */
    PageInfo<SysLog> getLogList(LogQueryParam param);
    
    /**
     * 按条件分页查询操作日志
     *
     * @param param
     * @return
     */
    PageInfo<SysLog> getList(String operator,Integer pageNum,Integer pageSize);
    

    /**
     * 按类型删除系统日志
     */
    void delByLogType(String logType);


}
