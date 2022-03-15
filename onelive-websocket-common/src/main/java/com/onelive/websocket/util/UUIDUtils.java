package com.onelive.websocket.util;

import java.util.Random;
import java.util.UUID;

public class UUIDUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 根据长度得到唯一编号
	 *
	 * @param length 长度
	 * @return 唯一编号
	 */
	public static String uuid(int length) {
		UUID uid = UUID.randomUUID();
		String temp = uid.toString().replace("-", "");
		if (length > 0 && length < temp.length()) {
			temp = temp.substring(temp.length() - length);
		}
		return temp;
	}

	/**
	 * 根据长度得到随机字符串，一位字母一位数字
	 *
	 * @param length 长度
	 * @return 字符串
	 */
	public static String unique(int length) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i % 2 == 0) {
				str.append(getRandom(97, 122));
			} else {
				str.append(getRandom(48, 57));
			}
		}
		return str.toString();
	}

	/**
	 * 得到纯数字编号
	 *
	 * @param length 长度
	 * @return
	 */
	public static String number(int length) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0)
				str.append(getRandom(49, 57));
			else
				str.append(getRandom(48, 57));
		}
		return str.toString();
	}

	/**
	 * 根据开始和结束大小得到单一字符
	 *
	 * @param begin 开始值
	 * @param end   结束值
	 * @return 单一字符
	 */
	private static String getRandom(int begin, int end) {
		String str = "";
		Random rd = new Random();
		int number = 0;
		while (str.length() == 0) {
			number = rd.nextInt(end + 1);
			if (number >= begin && number <= end)
				str = String.valueOf((char) number);
		}
		return str;
	}
}
