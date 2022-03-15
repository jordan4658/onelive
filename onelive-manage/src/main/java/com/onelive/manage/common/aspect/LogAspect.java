package com.onelive.manage.common.aspect;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;import com.onelive.common.config.aspect.ReqLogHandler;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.mybatis.entity.SysLog;
import com.onelive.common.utils.others.BaseUtil;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.service.sys.SysLogService;
import com.onelive.manage.utils.http.HttpRespons;
import com.onelive.manage.utils.http.HttpUtils;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @date 2018-11-24
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final SysLogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(SysLogService logService) {
        this.logService = logService;
    }


    @Pointcut("execution(public * com.onelive..*.controller..*.*(..))")
    public void controllerLog() {
    }

    /**
     * @throws
     * @Title: doBefore
     * @Description: 打印请求的信息
     * @param: @param joinPoint
     * @param: @throws Throwable
     * @return: void
     */
    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            String ip = BaseUtil.getIpAddress(request);
            //获取请求地址
            String requestPath = request.getRequestURL().toString();
            //获取请求头
            String requestHeader = request.getHeader(HeaderConstants.AUTHORIZATION);
            //获取请求BODY
            String requestBody = JSON.toJSONString(joinPoint.getArgs());
            String requestParam = ReqLogHandler.getParamFromRequest(request);
            StringBuffer sb = new StringBuffer();
            sb.append("\n【请求头】：").append(requestHeader);
            sb.append("\n【请求URL】：").append(requestPath);
            sb.append("\n【请求IP】：").append(ip);
            sb.append("\n【请求参数】：").append(requestParam);
            sb.append("\n【请求body】：").append(requestBody);
            log.info(sb.toString());
        } catch (Exception e) {
            //log.error("请求参数解析报错", e);
        }

    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.onelive.manage.common.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Order(999)
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        SysLog sysLog = new SysLog("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();

        HttpServletRequest request = getRequest();
        String username = getUsername(request);
        String browser = getBrowser(request);
        String ip = BaseUtil.getIpAddress(request);

        logService.saveLog(username, browser, ip, joinPoint, sysLog);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SysLog sysLog = new SysLog("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        sysLog.setExceptionDetail(getStackTrace(e));
        HttpServletRequest request = getRequest();
        String username = getUsername(request);
        String browser = getBrowser(request);
        String ip = BaseUtil.getIpAddress(request);
        ProceedingJoinPoint jp = (ProceedingJoinPoint) joinPoint;
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
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }

        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(params.toString() + " }");
        sysLog.setBrowser(browser);

        log.error("异常日志: {}", JSON.toJSONString(sysLog));
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取登录用户名
     *
     * @param request
     * @return
     */
    private String getUsername(HttpServletRequest request) {
        try {
            String json = (String) SystemRedisUtils.getLoginUserJson(request.getHeader(HeaderConstants.AUTHORIZATION));
            LoginUser loginUser = JSON.parseObject(json, LoginUser.class);
            return loginUser.getAccLogin();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取堆栈信息
     */
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    private static String getServerIp() {
        try {
            HttpRespons hr = HttpUtils.sendGet("http://ifconfig.me");
            int code = hr.code;
            return code == 200 ? hr.content.trim() : "获取外网ip地址失败";
        } catch (Exception var2) {
            log.error("获取外网ip地址失败", var2);
            return var2.getMessage();
        }
    }

    private String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }
}
