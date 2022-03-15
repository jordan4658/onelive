package com.onelive.gateway.model;

import java.io.Serializable;

public class JsonResponse implements Serializable {

	private static final long serialVersionUID = 9087410717667408519L;

	public static String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	public static String DEFAULT_FAIL_MESSAGE = "网络繁忙，请稍后重试";

	private Integer code;// 状态，0为成，其他为各种异常情况
	private Object data;// 返回数据
	private String msg;// 状态消息

	public JsonResponse() {
	}

	public JsonResponse(Integer code) {
		super();
		this.code = code;
		this.data = null;
	}

	public JsonResponse(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = null;
	}

	public JsonResponse(Integer code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public JsonResponse(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static JsonResponse success() {
		return new JsonResponse(200, DEFAULT_SUCCESS_MESSAGE);
	}

	public static JsonResponse success(String msg) {
		return new JsonResponse(200, msg);
	}

	public static JsonResponse fail() {
		return new JsonResponse(999, DEFAULT_FAIL_MESSAGE);
	}

	public static JsonResponse fail(String msg) {
		return new JsonResponse(550, msg);
	}

	public static JsonResponse fail(Integer code, String msg) {
		return new JsonResponse(code, msg);
	}

	public void setResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		this.data = null;
	}

	public void setResult(Object data, Integer code, String msg) {
		this.code =  code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the status to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the message to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
