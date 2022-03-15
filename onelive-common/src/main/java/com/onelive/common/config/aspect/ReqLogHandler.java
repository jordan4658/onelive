package com.onelive.common.config.aspect;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.onelive.common.constants.other.HeaderConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ReqLogHandler {

    private static final String USER_AGENT = "user-agent";

    //从request中获取header
    public static String getHeaderFromRequest(final HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        List<String> headersList = new ArrayList<>();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            if (HeaderConstants.ONELIVESOURCE.equalsIgnoreCase(header)) {
                headersList.add(header + "=" + request.getHeader(header));
            }
            if (HeaderConstants.AUTHORIZATION.equalsIgnoreCase(header)) {
                headersList.add(header + "=" + request.getHeader(header));
            }
            if (HeaderConstants.ONELIVEDEVICES.equalsIgnoreCase(header)) {
                headersList.add(header + "=" + request.getHeader(header));
            }
        }
        return headersList.toString();
    }

    //从request中获取请求参数
    public static String getParamFromRequest(final HttpServletRequest request) {
        String requestParam = "";
        if (request instanceof ReqReReadWrapper) {
            try {
                requestParam = IoUtil.read(request.getReader());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //get直接获取
            requestParam = JSONUtil.parse(request.getParameterMap() == null ? JSONUtil.createObj() : request.getParameterMap()).toJSONString(0);
        }
        return requestParam;
    }
}
