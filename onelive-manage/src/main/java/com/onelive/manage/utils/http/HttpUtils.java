package com.onelive.manage.utils.http;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 * HTTP 请求工具类
 *
 * @SuppressWarnings("此类后续禁用，http工具类统一使用 HttpClientUtil 工具类")
 */
@Slf4j
public abstract class HttpUtils {

    public static final String DEFAULT_CHARSET = "utf-8";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final int MAX_TIMEOUT = 27000;
    public static final int DEFAULT_TIMEOUT = 5000;
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=";
    public static final String CONTENT_TYPE_HTML = "text/html;charset=";
    public static final String TAOBAO_IPADDRESS_QUERY_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    // 连接超时时间2s
    private static int timeout = 20000;
    private static String defaultContentEncoding = "utf-8";

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private HttpUtils() {
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url) throws IOException {
        return doPost(url, new HashMap<>());
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, String params) throws IOException {
        String ctype = "";
        byte[] content = params.getBytes(DEFAULT_CHARSET);
        return _doPost(url, ctype, content, null);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset) throws IOException {
        return doPost(url, params, charset, null);
    }

    public static String doPost(String url, Map<String, String> params, String charset, Map<String, String> headerMap)
            throws IOException {
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, CONTENT_TYPE_FORM + charset, content, headerMap);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url     请求地址
     * @param ctype   请求类型
     * @param content 请求字节数组
     * @return 响应字符串
     * @throws IOException
     */
    @Deprecated
    private static String doPost(String url, String ctype, byte[] content) throws IOException {
        return _doPost(url, ctype, content, null);
    }

    private static String _doPost(String url, String ctype, byte[] content, Map<String, String> headerMap)
            throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), METHOD_POST, ctype, headerMap);
            conn.setConnectTimeout(MAX_TIMEOUT);
            conn.setReadTimeout(MAX_TIMEOUT);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    public static String doGetUseTimeOut(String url, Integer timeOut) throws IOException {
        return doGet(url, null, DEFAULT_CHARSET, timeOut);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET, null);
    }

    private static String doGet(String url, Map<String, String> params, String charset, Integer timeOut)
            throws IOException {
        return doGet(url, params, CONTENT_TYPE_FORM, charset, timeOut);
    }

    public static String doGet(String url, Map<String, String> params, Map<String, String> header)
            throws IOException {
        return doGet(url, params, CONTENT_TYPE_FORM, DEFAULT_CHARSET, DEFAULT_TIMEOUT, header);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @param timeOut
     * @return 响应字符串
     */

    private static String doGet(String url, Map<String, String> params, String contentType, String charset,
                                Integer timeOut, Map<String, String> header) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, contentType + charset, header);
            if (timeOut != null && timeOut > 0) {
                conn.setConnectTimeout(timeOut);
                conn.setReadTimeout(timeOut);
            }
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static String doGet(String url, Map<String, String> params, String contentType, String charset,
                                Integer timeOut) throws IOException {
        return doGet(url, params, contentType, charset, timeOut, null);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap)
            throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "ASIT");
        conn.setRequestProperty("Content-Type", ctype);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (null == query || query.trim().length() == 0) {
            return url;
        }

        if (null == url.getQuery() || url.getQuery().trim().length() == 0) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }

        return new URL(strUrl);
    }

    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (name == null || value == null || name.trim().length() == 0 || value.trim().length() == 0) {
                continue;
            }
            if (hasParam) {
                query.append("&");
            } else {
                hasParam = true;
            }

            query.append(name).append("=").append(URLEncoder.encode(value, charset));
        }

        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (null == msg || msg.trim().length() == 0) {
                log.info("getResponseAsString is empty. responseCode:{}, responseMessage:{}", conn.getResponseCode(), conn.getResponseMessage());
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                return msg;
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read = 0;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }
            log.info("getStreamAsString response:{}", response);
            return response.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (ctype != null && ctype.trim().length() > 0) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (null != pair[1] && pair[1].trim().length() > 0) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (null != value && value.trim().length() > 0) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (null != value && value.trim().length() > 0) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<>();

        String[] pairs = query.split("[?]")[1].split("[&]");
        if (pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    // 获取HttpServletRequest对象
    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }

    /**
     * 使用代理
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     */
    private static String doGetProxy(String url, Map<String, String> params, String charset, Integer timeOut,
                                     String userAgent, String ip, int port) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String query = buildQuery(params, charset);
            conn = getProxyConnection(buildGetUrl(url, query), METHOD_GET, CONTENT_TYPE_FORM + charset, null, ip, port);
            if (timeOut != null && timeOut > 0) {
                conn.setConnectTimeout(timeOut);
                conn.setReadTimeout(timeOut);
            }
            conn.setRequestProperty("User-Agent", userAgent);
            rsp = getResponseAsString(conn);
            log.info(rsp + "==================================");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * @param url
     * @param timeOut   请求超时时间
     * @param userAgent 代理标识
     * @return
     * @throws IOException
     */
    public static String doGetUseTimeOutAndAgent(String url, Integer timeOut, String userAgent, String ip, int port)
            throws IOException {
        return doGetProxy(url, null, DEFAULT_CHARSET, timeOut, userAgent, ip, port);
    }

    /**
     * 使用代理获取HttpURLConnection
     */
    private static HttpURLConnection getProxyConnection(URL url, String method, String ctype,
                                                        Map<String, String> headerMap, String ip, int port) throws IOException {
        HttpURLConnection conn = null;
        // 代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection(proxy);
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection(proxy);
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "ASIT");
        conn.setRequestProperty("Content-Type", ctype);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }


    /**
     * 发送GET请求
     *
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException
     */
    public static HttpRespons sendGet(String urlString) throws IOException {
        return send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @return 响应对象
     * @throws IOException
     */
    public static HttpRespons sendGet(String urlString, Map<String, Object> params) throws IOException {
        return send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException <p>
     */
    public static HttpRespons sendGet(String urlString, Map<String, Object> params, Map<String, Object> propertys) throws IOException {
        return send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     *
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException <p>
     */
    public static HttpRespons sendPost(String urlString) throws IOException {
        return send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @return 响应对象
     * @throws IOException <p>
     */
    public static HttpRespons sendPost(String urlString, Map<String, Object> params) throws IOException {
        return send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException <p>
     */
    public static HttpRespons sendPost(String urlString, Map<String, Object> params, Map<String, Object> propertys) throws IOException {
        return send(urlString, "POST", params, propertys);
    }

    /**
     * 发送HTTP请求
     *
     * @param urlString  地址
     * @param method     get/post
     * @param parameters 添加由键值对指定的请求参数
     * @param propertys  添加由键值对指定的一般请求属性
     * @return 响映对象
     * @throws IOException <p>
     */
    private static HttpRespons send(String urlString, String method, Map<String, Object> parameters, Map<String, Object> propertys) throws IOException {
        HttpURLConnection urlConnection = null;

        if ("GET".equalsIgnoreCase(method) && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }

        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(timeout);

        if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, (String) propertys.get(key));
            }
        }

        if ("POST".equalsIgnoreCase(method) && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }
        return makeContent(urlString, urlConnection);
    }

    /**
     * 得到响应对象
     *
     * @param urlString
     * @param urlConnection
     * @return 响应对象
     * @throws IOException <p>
     */
    private static HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpRespons httpResponser = new HttpRespons();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            httpResponser.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = defaultContentEncoding;
            }
            httpResponser.urlString = urlString;
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
            httpResponser.content = new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();
            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * @return <p>
     * Description:<br>
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * 发送GET请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception <p>
     */
    public static URLConnection sendGetRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        StringBuilder buf = new StringBuilder(url);
        Set<Entry<String, Object>> entrys = null;
        // 如果是GET请求，则请求参数在URL中
        if (params != null && !params.isEmpty()) {
            buf.append("?");
            entrys = params.entrySet();
            for (Entry<String, Object> entry : entrys) {
                buf.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(buf.toString());
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(timeout);
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Entry<String, Object> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        conn.getResponseCode();
        return conn;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception <p>
     */
    public static URLConnection sendPostRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        StringBuilder buf = new StringBuilder();
        Set<Entry<String, Object>> entrys = null;
        // 如果存在参数，则放在HTTP请求体，形如name=aaa&age=10
        if (params != null && !params.isEmpty()) {
            entrys = params.entrySet();
            for (Entry<String, Object> entry : entrys) {
                buf.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setConnectTimeout(timeout);
        OutputStream out = conn.getOutputStream();
        out.write(buf.toString().getBytes("UTF-8"));
        if (headers != null && !headers.isEmpty()) {
            entrys = headers.entrySet();
            for (Entry<String, Object> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        conn.getResponseCode(); // 为了发送成功
        return conn;
    }


    /**
     * 将输入流转为字节数组
     *
     * @param inStream
     * @return
     * @throws Exception <p>
     */
    public static byte[] read2Byte(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    /**
     * 将输入流转为字符串
     *
     * @param inStream
     * @return
     * @throws Exception <p>
     */
    public static String read2String(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return new String(outSteam.toByteArray(), "UTF-8");
    }

    /**
     * @param path     请求地址
     * @param xml      xml数据
     * @param encoding 编码
     * @return
     * @throws Exception <p>
     */
    public static byte[] postXml(String path, String xml, String encoding) throws Exception {
        byte[] data = xml.getBytes(encoding);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "text/xml; charset=" + encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(5 * 1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200) {
            return read2Byte(conn.getInputStream());
        }
        return null;
    }


    /**
     * 快递100测试
     *
     * @throws Exception <p>
     */
    public static void testPost() throws Exception {
        String url = "https://m.kuaidi100.com/query";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "yunda");
        params.put("postid", "3966750242380");
        params.put("id", "1");
        params.put("valicode", "");
        HttpURLConnection conn = (HttpURLConnection) sendPostRequest(url, params, null);
        int code = conn.getResponseCode();
        if (code == 200) {
            InputStream in = conn.getInputStream();
            byte[] data = read2Byte(in);
            String json = new String(data);
            System.out.print(json);
        } else {
            throw new Exception();
        }
    }

    /**
     * 设置默认的响应字符集
     *
     * @param defaultContentEncoding <p>
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }

    /**
     * 中国南北方省市区分
     */
    public final static String southhpro = "江苏,江苏省,安徽,安徽省,湖北,湖北省,重庆,重庆市,四川,四川省,西藏,西藏自治区,云南,云南省,贵州,贵州省,湖南,湖南省,江西,江西省,广西,广西壮族自治区,广东,广东省,福建,福建省,浙江,浙江省,上海,上海市,海南,海南省,台湾,台湾省,香港,香港特别行政区,澳门,澳门特别行政区";
    public final static String northpro = "山东,山东省,河南,河南省,山西,山西省,陕西,陕西省,甘肃,甘肃省,青海,青海省,新疆,新疆维吾尔自治区,河北,河北省,天津,天津市,北京,北京市,内蒙古,内蒙古自治区,辽宁,辽宁省,吉林,吉林省,黑龙江,黑龙江省,宁夏,宁夏回族自治区";

    /**
     * ip转地址 淘宝ip解析接口 容易被封 慎用
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddressTaobao(String ip) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        HttpRespons hr = sendGet("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
        if (hr.code == 200) {
//		{"code":0,"data":{"ip":"202.36.60.35","country":"柬埔寨","area":"","region":"XX","city":"XX","county":"XX","isp":"XX","country_id":"KH","area_id":"","region_id":"xx","city_id":"xx","county_id":"xx","isp_id":"xx"}}
//		{"code":0,"data":{"ip":"114.218.96.52","country":"中国","area":"","region":"江苏","city":"苏州","county":"XX","isp":"电信","country_id":"CN","area_id":"","region_id":"320000","city_id":"320500","county_id":"xx","isp_id":"100017"}}
            String content = hr.content;
            String[] strs = content.split(",");
            String province = strs[4].split(":")[1].replaceAll("\"", "");// .substring(10);
            String city = strs[5].split(":")[1].replaceAll("\"", "");
            String country = strs[2].split(":")[1].replaceAll("\"", "");
            map.put("country", country);
            map.put("province", province);
            map.put("city", city);
            return map;
        }

        return map;
    }


    static class WhoisBean {
        // 省
        private String pro;
        // 市
        private String city;
        // 地址(国外只有国家名称)
        private String addr;

        public WhoisBean() {
            super();
        }

        public WhoisBean(String pro, String city, String addr) {
            super();
            this.pro = pro;
            this.city = city;
            this.addr = addr;
        }

        public String getPro() {
            return pro;
        }

        public void setPro(String pro) {
            this.pro = pro;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

    }

}
