package com.onelive.common.utils.Login;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.model.common.HttpRespons;
import com.onelive.common.utils.http.AccessAddressUtil;
import com.onelive.common.utils.others.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Slf4j
public class IPAddressUtil {

    private static IPdatabaseConfig config;

    private static IPdatabaseConfig getConfig() {
        if (config == null) {
            config = SpringUtil.getBean(IPdatabaseConfig.class);
        }
        return config;
    }

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    public static String getIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                }
                return ip;
            }
        }
        String ip = request.getRemoteAddr();
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //ip = inet.getHostAddress();
            //获取外网ip
            ip =   getServerIp();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            //ip = "127.0.0.1";
            //获取外网ip
            ip = getServerIp();
        }

        return ip;
    }

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
//    public static String getIpAddress(HttpServletRequest request) {
//        String Xip = request.getHeader("X-Real-IP");
//        String XFor = request.getHeader("X-Forwarded-For");
//        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = XFor.indexOf(",");
//            if (index != -1) {
//                return XFor.substring(0, index);
//            } else {
//                return XFor;
//            }
//        }
//        XFor = Xip;
//        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
//            return XFor;
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getRemoteAddr();
//        }
//
//        if (XFor.equals("127.0.0.1") || XFor.equals("0:0:0:0:0:0:0:1")) {
//            // 根据网卡取本机配置的IP
//            InetAddress inet = null;
//            try {
//                inet = InetAddress.getLocalHost();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//            XFor = inet.getHostAddress();
//        }
//        if (XFor.equals("0:0:0:0:0:0:0:1")) {
//
//            XFor = "127.0.0.1";
//        }
//
//        return XFor;
//    }


//    /**
//     * 功能描述: 获取客户端所在地方
//     *
//     * @auther: muyu
//     * @param: []
//     * @return: java.lang.String
//     * @date: 2020/7/3 16:41
//     */
//    public static String getClientArea(String ip) {
//        String area = null;
//        try {
//            if (StringUtils.isBlank(ip)) return area;
//            Map<String, String> map = AccessAddressUtil.ipParse(ip);
//            ;
//            if (map != null) {
//                log.info("根据ip获取城市地址ipmap：{}", map);
//                String country = map.get("country");
//                String province = map.get("province");
//                String city = map.get("city");
//
//                if (org.apache.commons.lang3.StringUtils.isNotEmpty(province) || org.apache.commons.lang3.StringUtils.isNotEmpty(city)) {
//                    if ("XX".equals(province) || "内网IP".equals(province)) {
//                        province = SymbolConstant.BLANK;
//                    }
//                    if ("XX".equals(city) || "内网IP".equals(city)) {
//                        city = SymbolConstant.BLANK;
//                    }
//                    area = province + city;
//                }
//
//            }
//        } catch (Exception e) {
//            log.error("获取域名出错", e);
//        }
//        return area;
//    }


    public static String getClientArea(String ip){
        String area = null;
        // IP V6 也是可以的
        DatabaseReader reader = null;
        CityResponse response;
        //CountryResponse response2 = null;
        try {
            File database = new File(getConfig().getIpDatabase());
            // 读取数据库内容
            reader = new DatabaseReader.Builder(database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            // 获取查询结果
            response = reader.city(ipAddress);
            area = response.getCountry().getNames().get("zh-CN");
        } catch (Exception e) {
            log.error("通过ip获取所在国家地区报错",e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("通过ip获取所在国家地区报错",e);
                }
            }
        }
        return area;
    }

    /**
     * 获取地区英文名字
     * @param ip
     * @return
     */
    public static String getClientAreaEn(String ip){
        String area = null;
        // IP V6 也是可以的
        DatabaseReader reader = null;
        CityResponse response;
        //CountryResponse response2 = null;
        try {
            File database = new File(getConfig().getIpDatabase());
            // 读取数据库内容
            reader = new DatabaseReader.Builder(database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            // 获取查询结果
            response = reader.city(ipAddress);
            area = response.getCountry().getNames().get("en");
        } catch (Exception e) {
            log.error("通过ip获取所在国家地区报错",e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("通过ip获取所在国家地区报错",e);
                }
            }
        }
        return area;
    }



    /**
     * getMobileDevice:(获取手机设备型号)
     *
     * @param request
     * @return
     */
    public static String getMobileDevice(HttpServletRequest request) {
        String deviceStr = request.getHeader(HeaderConstants.ONELIVEDEVICES);
        if (StringUtils.isNotBlank(deviceStr)) return deviceStr;
        String userAgentStr = request.getHeader("user-agent");
        try {
            UserAgent ua = UserAgentUtil.parse(userAgentStr);
            deviceStr = ua.getOs().toString();
        } catch (Exception e) {
            log.error("获取手机设备型号出错", e);
        }

        return deviceStr;
    }


//    public static void main(String[] args) {
//        String userAgent4 = "user-agent = Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12B440 Safari/600.1.4";
//        UserAgent ua = UserAgentUtil.parse(userAgent4);
//        System.err.println(ua.getOs().toString());
//    }


    public static String getServerIp() {
        // 获取操作系统类型
        try {
            HttpRespons  respons =    AccessAddressUtil.sendGet("http://ifconfig.me");
            ///HttpRespons hr = HttpUtils.sendGet("http://ifconfig.me");
            int code = respons.code;
            if (code == 200) {
                return respons.content.trim();
            } else {
                return "獲取外網ip地址失敗";
            }
        } catch (Exception e) {
         //   logger.error("獲取外網ip地址失敗", e);
            return e.getMessage();
        }
    }


}
