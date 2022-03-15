package com.onelive.manage.utils.other;

import com.dd.plist.*;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apkinfo.api.util.AXmlResourceParser;
import org.apkinfo.api.util.TypedValue;
import org.apkinfo.api.util.XmlPullParser;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 获取 APK、IPA app包的信息
 */
public class AppPackageUtil {

    public static void main(String[] args) throws Exception {
        System.out.println();

//        String apkUrl = "C:\\Users\\pc\\Desktop\\app\\SZ-isAgentApk-0-1-v1.0-debug.apk";
//        Map mapApk = readAPK(apkUrl);
//        System.out.println("mapApk======="+mapApk);

//        Map<String, Map<String, Object>> map = analyzeApk("C:\\Users\\pc\\Desktop\\app\\SZ-isAgentApk-0-1-v1.0-debug.apk");
//        System.out.println("================:" + JSONObject.toJSONString(map));

//        Map<String, String> map2 = getApkInfo("C:\\Users\\pc\\Desktop\\app\\SZ-isAgentApk-1-1-v1.0-debug.apk");
//        System.out.println("================:" + JSONObject.toJSONString(map2));

       // Map<String, String> map = getIpaInfo("C:\\Users\\pc\\Desktop\\app\\包\\官方包\\CL.ipa");

    }

    /**
     * @param apkPath
     * @return Map<String, String>
     */
    public static Map<String, String> getApkInfo2(String apkPath) {
        Map<String, String> map = new HashMap<>();
        try (ApkFile apkFile = new ApkFile(new File(apkPath))) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            map.put("versionCode", apkMeta.getVersionCode().toString());
            map.put("versionName", apkMeta.getVersionName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 解析 IPA 文件
     *
     * @param ipaPath ipa包地址
     * @return Map<String, String>
     */
    public static Map<String, String> getIpaInfo(String ipaPath) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            InputStream is = new FileInputStream(ipaPath);
            ZipInputStream zipIs = new ZipInputStream(is);
            ZipEntry ze;
            InputStream infoIs = null;
            while ((ze = zipIs.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    String name = ze.getName();
                    // 读取 info.plist 文件
                    // FIXME: 包里可能会有多个 info.plist 文件！！！
                    if (name.contains(".app/Info.plist")) {
                        ByteArrayOutputStream abos = new ByteArrayOutputStream();
                        int chunk = 0;
                        byte[] data = new byte[256];
                        while (-1 != (chunk = zipIs.read(data))) {
                            abos.write(data, 0, chunk);
                        }
                        infoIs = new ByteArrayInputStream(abos.toByteArray());
                        break;
                    }
                }
            }
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(infoIs);
            String[] keyArray = rootDict.allKeys();
            for (String key : keyArray) {
                NSObject value = rootDict.objectForKey(key);
                if (key.equals("CFBundleSignature")) {
                    continue;
                }
                if (value.getClass().equals(NSString.class) || value.getClass().equals(NSNumber.class)) {
                    resultMap.put(key, value.toString());
                }
            }
            zipIs.close();
            is.close();
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
        }
        return resultMap;
    }


    /**
     * 读取apk
     *
     * @param apkUrl
     * @return
     */
    public static Map<String, String> getApkInfo(String apkUrl) {
        ZipFile zipFile;
        Map<String, String> map = new HashMap<String, String>();
        try {
            zipFile = new ZipFile(apkUrl);
            Enumeration<?> enumeration = zipFile.entries();
            ZipEntry zipEntry = null;
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                if (zipEntry.isDirectory()) {

                } else {
                    if ("androidmanifest.xml".equals(zipEntry.getName().toLowerCase())) {
                        AXmlResourceParser parser = new AXmlResourceParser();
                        parser.open(zipFile.getInputStream(zipEntry));
                        while (true) {
                            int type = parser.next();
                            if (type == XmlPullParser.END_DOCUMENT) {
                                break;
                            }
                            String name = parser.getName();
                            if (null != name && name.toLowerCase().equals("manifest")) {
                                for (int i = 0; i < parser.getAttributeCount(); i++) {
                                    if ("versionName".equals(parser.getAttributeName(i))) {
                                        String versionName = getAttributeValue(parser, i);
                                        if (null == versionName) {
                                            versionName = "";
                                        }
                                        map.put("versionName", versionName);
                                    } else if ("package".equals(parser.getAttributeName(i))) {
                                        String packageName = getAttributeValue(parser, i);
                                        if (null == packageName) {
                                            packageName = "";
                                        }
                                        map.put("package", packageName);
                                    } else if ("versionCode".equals(parser.getAttributeName(i))) {
                                        String versionCode = getAttributeValue(parser, i);
                                        if (null == versionCode) {
                                            versionCode = "";
                                        }
                                        map.put("versionCode", versionCode);
                                    } else if ("isAgentApk".equals(parser.getAttributeName(i))) {
                                        String isAgentApk = getAttributeValue(parser, i);
                                        if (null == isAgentApk) {
                                            isAgentApk = "";
                                        }
                                        map.put("isAgentApk", isAgentApk);
                                    }
                                }
                            } else if (null != name && name.toLowerCase().equals("meta-data")) {
                                for (int i = 0; i < parser.getAttributeCount(); i++) {
                                    if ("isAgentApk".equals(parser.getAttributeValue(i))) {
                                        String isAgentApk = getAttributeValue(parser, i + 1);
                                        if (null == isAgentApk) {
                                            isAgentApk = "";
                                        }
                                        map.put("isAgentApk", isAgentApk);
                                    }
                                }

                            }
                        }
                    }

                }
            }
            zipFile.close();
        } catch (Exception e) {
            map.put("code", "fail");
            map.put("error", "读取apk失败");
        }
        return map;
    }

    private static String getAttributeValue(AXmlResourceParser parser, int index) {
        int type = parser.getAttributeValueType(index);
        int data = parser.getAttributeValueData(index);
        if (type == TypedValue.TYPE_STRING) {
            return parser.getAttributeValue(index);
        }
        if (type == TypedValue.TYPE_ATTRIBUTE) {
            return String.format("?%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_REFERENCE) {
            return String.format("@%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_FLOAT) {
            return String.valueOf(Float.intBitsToFloat(data));
        }
        if (type == TypedValue.TYPE_INT_HEX) {
            return String.format("0x%08X", data);
        }
        if (type == TypedValue.TYPE_INT_BOOLEAN) {
            return data != 0 ? "true" : "false";
        }
        if (type == TypedValue.TYPE_DIMENSION) {
            return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type == TypedValue.TYPE_FRACTION) {
            return Float.toString(complexToFloat(data)) + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return String.format("#%08X", data);
        }
        if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
            return String.valueOf(data);
        }
        return String.format("<0x%X, type 0x%02X>", data, type);
    }

    private static String getPackage(int id) {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    // / ILLEGAL STUFF, DONT LOOK :)
    public static float complexToFloat(int complex) {
        return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
    }

    private static final float RADIX_MULTS[] =
            {
                    0.00390625F, 3.051758E-005F,
                    1.192093E-007F, 4.656613E-010F
            };
    private static final String DIMENSION_UNITS[] = {"px", "dip", "sp", "pt", "in", "mm", "", ""};
    private static final String FRACTION_UNITS[] = {"%", "%p", "", "", "", "", "", ""};


    private static Map<String, Object> getError(Exception e) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("cause", e.getCause());
        errorMap.put("message", e.getMessage());
        errorMap.put("stack", e.getStackTrace());
        return errorMap;
    }



}
