package com.onelive.manage.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.log.LogQueryParam;
import com.onelive.common.mybatis.entity.SysLog;
import com.onelive.common.mybatis.mapper.master.sys.SysLogMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysLogMapper;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.service.sys.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-02
 */
@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Resource
    private SlaveSysLogMapper slaveSysLogMapper;

    @Override
    public void saveLog(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log aopLog = method.getAnnotation(Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");

        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }
        // 描述
        if (sysLog != null) {
            sysLog.setDescription(aopLog.value());
        }
        assert sysLog != null;
        sysLog.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                username = new JSONObject(argValues.get(0)).get("username").toString();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        sysLog.setCreateTime(new Date());
        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(params.toString() + " }");
        sysLog.setBrowser(browser);

        save(sysLog);
    }

    @Override
    public PageInfo<SysLog> getLogList(LogQueryParam param) {
        LambdaQueryWrapper<SysLog> queryWrapper = Wrappers.<SysLog>lambdaQuery();
        Date startTime = null, endTime = null;
        if (param.getStartTime() != null) {
            startTime = new Date(param.getStartTime());
        }
        if (param.getEndTime() != null) {
            endTime = new Date(param.getEndTime());
        }
        queryWrapper.eq(StrUtil.isNotBlank(param.getUsername()), SysLog::getUsername, param.getUsername())
                .eq(StrUtil.isNotBlank(param.getLogType()), SysLog::getLogType, param.getLogType())
                .like(StrUtil.isNotBlank(param.getDescription()), SysLog::getDescription, param.getDescription())
                .gt(startTime != null, SysLog::getCreateTime, startTime)
                .lt(endTime != null, SysLog::getCreateTime, endTime).orderByDesc(SysLog::getCreateTime);

        return PageHelper.startPage(param.getPageNum(), param.getPageSize()).doSelectPageInfo(() -> slaveSysLogMapper.selectList(queryWrapper));
    }

    @Override
    public void delByLogType(String logType) {
        remove(Wrappers.<SysLog>lambdaQuery().eq(SysLog::getLogType, logType));
    }

	/* （非 Javadoc）
	 * @see com.onelive.manage.service.sys.SysLogService#getList(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageInfo<SysLog> getList(String operator, Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<SysLog> queryWrapper = Wrappers.<SysLog>lambdaQuery();
       
        queryWrapper.eq(StrUtil.isNotBlank(operator), SysLog::getUsername, operator).orderByDesc(SysLog :: getCreateTime);

        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> slaveSysLogMapper.selectList(queryWrapper));
	}
}
