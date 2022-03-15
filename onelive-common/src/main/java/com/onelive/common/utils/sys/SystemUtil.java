package com.onelive.common.utils.sys;

import java.util.Date;

import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.DateUtils;

public class SystemUtil {

	/**
	 * 返回请求头地区的当前时间
	 * 
	 * @param lang
	 * @return
	 */
	public static Date getLangTime() {
		switch (LoginInfoUtil.getLang()) {
		case "id_ID":
			return DateUtils.getByGMT(5);
		case "vi_VN":
			return DateUtils.getByGMT(7);
		case "th_TH":
			return DateUtils.getByGMT(7);
		case "ko_KR":
			return DateUtils.getByGMT(9);
		case "en_US":
			return DateUtils.getByGMT(0);
		default:
			return new Date();
		}
	}

	/**
	 * 返回指定地区相对于当前时间的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLangTime(Date date) {
		switch (LoginInfoUtil.getLang()) {
		case "id_ID":
			return DateUtils.getByGMT(5, date);
		case "vi_VN":
			return DateUtils.getByGMT(7, date);
		case "th_TH":
			return DateUtils.getByGMT(7, date);
		case "ko_KR":
			return DateUtils.getByGMT(9, date);
		case "en_US":
			return DateUtils.getByGMT(0, date);
		default:
			return date;
		}
	}

}