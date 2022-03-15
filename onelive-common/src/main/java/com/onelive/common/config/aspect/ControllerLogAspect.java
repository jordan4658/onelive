package com.onelive.common.config.aspect;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.annotation.AopEncryption;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    @Value("${fl.secretKey}")
    private String secretKey;

    @Pointcut("execution(public * com.onelive..*.controller..*.*(..))")
    public void controllerLog() {
    }

    /**
     * 定义切入点，切入点为Controller中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("@annotation(com.onelive.common.annotation.AopEncryption)")
    public void BrokerAspect(){}


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

            // 获取请求头
            Enumeration<String> enumeration = request.getHeaderNames();
            StringBuffer headers = new StringBuffer();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                String value = request.getHeader(name);
                if (HeaderConstants.AUTHORIZATION.equalsIgnoreCase(name)) {
                    headers.append(name + "=" + value).append("; ");
                }
                if (HeaderConstants.ONELIVESOURCE.equalsIgnoreCase(name)) {
                    headers.append(name + "=" + value).append("; ");
                }
//                if (HeaderConstants.USER_AGENT.equalsIgnoreCase(name)) {
//                    headers.append(name + "=" + value).append("; ");
//                }
                if (HeaderConstants.ONELIVEDEVICES.equalsIgnoreCase(name)) {
                    headers.append(name + "=" + value).append("; ");
                }
            }
            String requestParam = ReqLogHandler.getParamFromRequest(request);

            StringBuffer sb = new StringBuffer();
            sb.append("\n【请求头】：").append(headers);
            sb.append("\n【请求URL】：").append(request.getRequestURL());
            sb.append("\n【请求IP】：").append(LoginInfoUtil.getIp());
            sb.append("\n【请求类名】：").append(joinPoint.getSignature().getDeclaringTypeName());
            sb.append("\n【请求方法名】：").append(joinPoint.getSignature().getName());
            sb.append("\n【请求参数】：").append(requestParam);
            sb.append("\n【请求body】：").append(JSONUtil.toJsonStr(joinPoint.getArgs()));
            log.info(sb.toString());
        } catch (Exception e) {
            log.error("请求报错", e);
        }


    }

    /**
     * @throws
     * @Title: doAfter
     * @Description: 在切点之后织入
     * @param: @throws Throwable
     * @return: void
     */
    @After("controllerLog()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE,LoginInfoUtil.getKeyType());
    }


    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "controllerLog()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE,LoginInfoUtil.getKeyType());
    }


    /**
     * 环绕通知(用于加密解密)
     * @param pjd
     * @return
     */
    @Around(value = "BrokerAspect()")
    public Object aroundMethod(ProceedingJoinPoint pjd){
        try {
            // 获取被代理对象
            Object target = pjd.getTarget();
            // 获取通知签名
            MethodSignature signature = (MethodSignature )pjd.getSignature();
            // 获取被代理方法
            Method method = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
            // 获取被代理方法上面的注解AopEncryption
            AopEncryption aopEncryption = method.getAnnotation(AopEncryption.class);
            if (aopEncryption == null){
                // 被代理方法上没有，则说明@AopEncryption注解在被代理类上
                aopEncryption = target.getClass().getAnnotation(AopEncryption.class);
            }
            if (aopEncryption == null){
                log.info("未加密, 不拦截:"+method.getName());
                return pjd.proceed();
            }

            Object[] obj = null;
            if (aopEncryption.decrypt()){
                do {
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpServletRequest request = attributes.getRequest();
                    String encryption = request.getParameter("encryption");
                    if (StringUtils.isEmpty(encryption)){
                        break;
                    }
                    encryption = encryption.replaceAll(" ","+");
                    //解密
                    String decrypt = AesUtil.aesDecrypt(encryption, secretKey);
                    JSONObject decryptJson = JSONObject.parseObject(decrypt);
                    // 获取被代理方法参数
                    Object[] args = pjd.getArgs();

                    // 参数名称数组
                    String[] param = signature.getParameterNames();
                    if (param == null || param.length == 0){
                        break;
                    }
                    obj = new Object[param.length];

                    for (int i = 0; i < param.length; i++){
                        Class<?> aClass = aopEncryption.paramType()[i];
                        if (aClass.equals(MultipartFile.class) || aClass.equals(MultipartFile[].class)){
                            obj[i] = args[i];
                        }
                        else {
                            obj[i] = decryptJson.getObject(param[i], aClass);
                            if(obj[i]==null){
                                obj[i] = JSON.toJavaObject(decryptJson,aClass);
                            }
                        }
                    }
                    break;
                }while (true);
            }

            Object result;
            if (ObjectUtils.isEmpty(obj)){
                result = pjd.proceed();
            } else {
                result = pjd.proceed(obj);
            }


//
//            if (aopEncryption.encryption() && result != null){
//                JSONObject objCheck = JSONObject.parseObject(JSON.toJSONString(result));
//                String data =  AesUtil.aesEncrypt(JSONObject.toJSONString(objCheck),  LoginInfoUtil.getKeyType());
//                if(objCheck.get("data") != null){
//                    data =   AesUtil.aesEncrypt(JSONObject.toJSONString(objCheck.get("data")),  LoginInfoUtil.getKeyType());
//                }
//                return   ResultInfo.getInstance(data,objCheck.getInteger("code"),objCheck.getString("msg"));
//            }



            return result;
        }
        catch (Throwable e){
            log.error("解析数据错误",e);
            return ResultInfo.error();
        }
    }

}

