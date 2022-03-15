package com.onelive.common.utils.http;

import com.onelive.common.model.common.HttpRespons;
import com.onelive.common.utils.others.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * @Author muyu
 * @Description 自定义访问地址工具类
 * @Create 2020/6/26 20:45
 */
@Component
public class AccessAddressUtil {

    // 连接超时时间2s
    private static int timeout = 2000;
    private String defaultContentEncoding;

    public AccessAddressUtil() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    /**
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送GET请求
     */
    public static HttpRespons sendGet(String urlString) throws IOException {
        return new AccessAddressUtil().send(urlString, "GET", null, null);
    }


    /**
     * @param urlString  地址
     * @param method     get/post
     * @param parameters 添加由键值对指定的请求参数
     * @param propertys  添加由键值对指定的一般请求属性
     * @return 响映对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 发送HTTP请求
     */
    private HttpRespons send(String urlString, String method, Map<String, Object> parameters, Map<String, Object> propertys) throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
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

        if (propertys != null)
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, (String) propertys.get(key));
            }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }
        return this.makeContent(urlString, urlConnection);
    }

    /**
     * @param urlString
     * @param urlConnection
     * @return 响应对象
     * @throws IOException
     * @author abu
     * <p>
     * Description:<br>
     * 得到响应对象
     */
    private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
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
            if (ecod == null)
                ecod = this.defaultContentEncoding;
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
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }


    /**
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 发送POST请求
     */
    public static URLConnection sendPostRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        StringBuilder buf = new StringBuilder();
        Set<Map.Entry<String, Object>> entrys = null;
        // 如果存在参数，则放在HTTP请求体，形如name=aaa&age=10
        if (params != null && !params.isEmpty()) {
            entrys = params.entrySet();
            for (Map.Entry<String, Object> entry : entrys) {
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
            for (Map.Entry<String, Object> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        conn.getResponseCode(); // 为了发送成功
        return conn;
    }


    /**
     * @param inStream
     * @return
     * @throws Exception
     * @author abu
     * <p>
     * Description:<br>
     * 将输入流转为字节数组
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
     * 中国南北方省市区分
     */
    public final static String southhpro = "江苏,江苏省,安徽,安徽省,湖北,湖北省,重庆,重庆市,四川,四川省,西藏,西藏自治区,云南,云南省,贵州,贵州省,湖南,湖南省,江西,江西省,广西,广西壮族自治区,广东,广东省,福建,福建省,浙江,浙江省,上海,上海市,海南,海南省,台湾,台湾省,香港,香港特别行政区,澳门,澳门特别行政区";
    public final static String northpro = "山东,山东省,河南,河南省,山西,山西省,陕西,陕西省,甘肃,甘肃省,青海,青海省,新疆,新疆维吾尔自治区,河北,河北省,天津,天津市,北京,北京市,内蒙古,内蒙古自治区,辽宁,辽宁省,吉林,吉林省,黑龙江,黑龙江省,宁夏,宁夏回族自治区";

    /**
     * ip地址转换省市区
     *
     * @param ip
     * @return
     * @throws IOException
     */
    public synchronized static Map<String, String> ipParse(String ip) throws IOException {
        Map<String, String> map = new HashMap<>();
        try {
            if (StringUtils.isNotEmpty(ip) && !ip.equals("127.0.0.1") && !ip.equals("0:0:0:0:0:0:0:1")) {
                map = getAddressWhois(ip);
                if (StringUtils.isEmpty(map.get("province"))) {
                    map = getAddress126(ip);
                    if (StringUtils.isEmpty(map.get("province")))
                        map = getAddressTaobao(ip);
                }
                if (northpro.contains(map.get("province")))
                    map.put("country", "北方");
                else if (southhpro.contains(map.get("province")))
                    map.put("country", "南方");
                else
                    map.put("country", map.get("province"));
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * ip转地址 淘寶ip解析接口 容易被封 慎用
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddressTaobao(String ip) throws Exception {
        Map<String, String> map = new HashMap<>();
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

    /**
     * ip转地址 國外ip無法識別
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddress126(String ip) throws Exception {
        Map<String, String> map = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://ip.ws.126.net/ipquery?ip=" + ip);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(2000).setSocketTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "text/html;charset=GBK");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
            String[] strs = content.split("localAddress=")[1].split(",");
            String province = strs[1].split(":")[1].replaceAll("\"", "").replaceAll("}", "");
            String city = strs[0].split(":")[1].replaceAll("\"", "");
            map.put("province", province);
            map.put("city", city);
        } catch (Exception e) {
        } finally {
            try {
                if (httpResponse != null)
                    httpResponse.close();
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {

            }
        }

        return map;
    }

    /**
     * ip转地址 國內國外通用
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public synchronized static Map<String, String> getAddressWhois(String ip) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(2000).setSocketTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "text/html;charset=GBK");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity).trim();
            AccessAddressUtil.WhoisBean wb = JacksonUtil.fromJson(content, AccessAddressUtil.WhoisBean.class);
            if (wb != null) {
                map.put("province", StringUtils.isEmpty(wb.getPro()) ? wb.getAddr() : wb.getPro());
                map.put("city", wb.getCity());
            }
        } catch (Exception e) {
        } finally {
            try {
                if (httpResponse != null)
                    httpResponse.close();
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {

            }
        }

        return map;
    }

    static class WhoisBean {
        // 省
        private String pro;
        // 市
        private String city;
        // 地址(國外只有國家名稱)
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
