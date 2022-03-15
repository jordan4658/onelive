package com.onelive.manage.modules.base;


import com.onelive.common.constants.business.LoginConstants;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.utils.others.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;

/**
 * @version 1.0
 * @Description
 * @revise
 */
@Controller
@Slf4j
public class BaseAdminController {

    @Resource
    protected HttpServletRequest request;

    /**
     * @return <p>
     * Description:<br>
     * 运营后台登录
     */
    public LoginUser getLoginAdmin() {
        return (LoginUser)request.getSession().getAttribute(LoginConstants.ADMIN_LOGIN_INFO);
    }

    // 获取客户端IP
    public String getIpAddress() {
        return BaseUtil.getIpAddress(request);
    }

    /**
     * 获取服务器地址
     *
     * @return Ip地址
     */
    public String getServerIp() {
        // 获取操作系统类型
        String sysType = System.getProperties().getProperty("os.name");
        String ip;
        if (sysType.toLowerCase().startsWith("win")) { // 如果是Windows系统，获取本地IP地址
            String localIP = null;
            try {
                localIP = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {

            }
            if (localIP != null) {
                return localIP;
            }
        } else {
            ip = getIpByEthNum("eth0"); // 兼容Linux
            if (ip != null) {
                return ip;
            }
        }
        return "获取服务器IP错误";
    }

    /**
     * 根据网络接口获取IP地址
     *
     * @param ethNum 网络接口名，Linux下是eth0
     * @return
     */
    private String getIpByEthNum(String ethNum) {
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

        }
        return "获取服务器IP错误";
    }

    /**
     * 获取前端参数 userIP，为空则通过后台获取请求IP
     *
     * @param request
     * @return
     */
    protected String resolveIp(HttpServletRequest request) {
        return BaseUtil.getIpAddress(request);
    }

}
