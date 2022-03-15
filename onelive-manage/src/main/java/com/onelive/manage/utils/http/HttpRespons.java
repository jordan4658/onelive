package com.onelive.manage.utils.http;

import lombok.Data;

import java.util.Vector;

/**
 *
 * @Description httpUtils封装类
 */
@Data
public class HttpRespons {
	public String urlString;
	public int defaultPort;
	public String file;
	public String host;
	public String path;
	public int port;
	public String protocol;
	public String query;
	public String ref;
	public String userInfo;
	public String content;
	public String contentEncoding;
	public int code;
	public String message;
	public String contentType;
	public String method;
	public int connectTimeout;
	public int readTimeout;
	public Vector<String> contentCollection;
}
