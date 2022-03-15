package com.onelive.common.utils.webSocket;

import org.springframework.util.StringUtils;

public class ParamUtil {

	public static String getParamFormUrl(String url, String key) {
		if (StringUtils.isEmpty(url))
			return null;
		String[] params = url.split("&");
		if (params.length == 0)
			return null;
		for (String str : params) {
			String[] k = str.split("=");
			if (k[0].equals(key) && k.length == 2)
				if (!StringUtils.isEmpty(k[1]))
					return k[1];
				else
					return null;
		}
		return null;
	}
}
