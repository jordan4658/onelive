package com.onelive.common.utils.others;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static <E> Map<E, E> convertToMap(Collection<E> collection) {
        Map<E, E> map = new HashMap();
        for (E e : collection) {
            map.put(e, e);
        }
        return map;
    }

    public static List convertToList(Map<Object, Object> map) {
        List list = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public static String toAppendString(Collection collection) {
        return toAppendString(collection, ",");
    }

    public static String toAppendString(Collection collection, String separator) {
        if (isEmpty(collection)) {
            return "";
        }
        separator = (null == separator || "".equals(separator.trim())) ? "," : separator;
        StringBuilder sb = new StringBuilder();
        for (Object obj : collection) {
            sb.append(obj).append(separator);
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * @Title: sort
     * @Description: 降序排序Map类型的
     * @author HANS
     * @date 2019年5月13日下午8:43:44
     */
    public static void sort(List<Map<String, Object>> data) {
        Collections.sort(data, (Map<String, Object> o1, Map<String, Object> o2) -> {
            Integer a = (Integer) o1.get("dragonSum");
            Integer b = (Integer) o2.get("dragonSum");
            return b - a;
        });
    }

    public static String buildUrlParams(Map<Object, Object> map) {
        return buildUrlParams(map, null);
    }

    public static String buildUrlParams(Map<Object, Object> map, String prefix) {
        if (map == null || map.size() == 0) {
            return "";
        }

        prefix = StringUtils.isEmpty(prefix) ? "" : prefix;
        StringBuffer sb = new StringBuffer(prefix);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

//    public static void main(String[] args) {
//        Map map = new HashMap();
//        map.put(1, 1);
//        map.put("2", 2);
////        map.put(3.33, 3.33);
////        System.out.println(JSONObject.toJSONString(convertToList(map)));
//        System.out.println(buildUrlParams(map, "&"));
//    }

}
