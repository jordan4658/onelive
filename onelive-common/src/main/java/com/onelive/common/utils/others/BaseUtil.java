package com.onelive.common.utils.others;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.onelive.common.enums.StatusCode;
import com.onelive.common.model.common.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @Description:基础工具类
 * @Version: 1.0.0
 */
@Slf4j
public class BaseUtil {

    /**
     * @param request
     * @Author: admin
     * @Description: nginx代理后获取ip方法
     * @Version: 1.0.0
     * @Date; 2018/5/17 9:31
     * @return: java.lang.String
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.contains(",")) {
            // 多个路由时，取第一个非unknown的ip
            final String[] arr = ip.split(",");
            for (final String str : arr) {
                if (!"unknown".equalsIgnoreCase(str)) {
                    ip = str;
                    break;
                }
            }
        }
        return ip;
    }


    /**
     * 根据网络接口获取IP地址
     *
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    @SuppressWarnings({"unused", "rawtypes"})
    public static String getIpByEthNum(String ethNum) {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (ethNum.equals(netInterface.getName())) {
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error("getIpByEthNum occur error", e);
        }
        return "获取服务器IP错误";
    }



    public static void writerResponse(HttpServletResponse response, StatusCode code) throws IOException {
        writerResponse(response, code.getCode(), code.getMsg());
    }


    public static void writer401Response(HttpServletResponse response) throws IOException {
        writerResponse(response, StatusCode.UNLOGIN_CODE.getCode(), StatusCode.UNLOGIN_CODE.getMsg());
    }


    public static void writer401Response(HttpServletResponse response, String msg) throws IOException {
        writerResponse(response, StatusCode.UNLOGIN_CODE.getCode(), StatusCode.UNLOGIN_CODE.getMsg(), msg);
    }


    public static void writerResponse(HttpServletResponse response, Integer code, String msg) throws IOException {
        writerResponse(response, code, msg, null);
    }

    public static void writerResponse(HttpServletResponse response, Integer code, String msg, Object data) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        ResultInfo resultInfo = new ResultInfo<>();
        resultInfo.setCode(code);
        resultInfo.setMsg(msg);
        resultInfo.setData(data);
        response.getWriter().print(JSON.toJSONString(resultInfo, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteMapNullValue));
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        uri = request.getQueryString() != null ? uri + "?" + request.getQueryString() : uri;
        return uri;
    }

}
