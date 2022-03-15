package com.onelive.websocket.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mao
 * 
 *         直播项目项目自定义个性化字符串处理工具类
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 解析get请求 urn 参数,拼装成map
	 *  例: 
	 *  	url = "/ws?userId=1&studioNum=2";
	 *  	返回 {userId=1,chatId=2}
	 * @param uri
	 * @return
	 */
	public static Map<String, String> parseUri(String uri) {
		Map<String, String> result = new HashMap<>(4);
		int lastIndexOf = uri.lastIndexOf('?');
		if (lastIndexOf > 0) {
			String paramStr = uri.substring(lastIndexOf + 1, uri.length());
			String[] paramArr = paramStr.split("&");
			for (int i = 0; i < paramArr.length; i++) {
				String[] param = paramArr[i].split("=");
				if (param.length > 1) {
					result.put(param[0], param[1]);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("/ws?userId=1");
	}
}
