package com.onelive.common.utils.others;

import com.onelive.common.constants.other.SymbolConstant;

import java.util.*;

/**
 * @类名 ListUtil
 * @开发者 muyu
 * @开发时间 2019-11-27 14:29:15.655
 * @描述: list工具类
 */
public final class ListUtil {

    private ListUtil() {
        throw new IllegalStateException("ListUtil class");
    }

    /**
     * list是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(Collection list) {
        if (list == null) {
            return true;
        }
        return list.size() == 0;
    }

    /**
     * list是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(Collection list) {
        return list != null && list.size() > 0;
    }

    /**
     * 内存分页
     *
     * @param dataList
     * @param pageSize
     * @param pageNumber
     * @param <T>
     * @return
     */
    public static <T> List<T> queryByPage(List<T> dataList, Integer pageSize, Integer pageNumber) {
        if (isEmpty(dataList)) {
            return new ArrayList();
        }
        final Integer len = dataList.size();
        final Integer start = (pageNumber - 1) * pageSize;

        if (pageSize >= len) {
            return dataList;
        }
        final Integer end = pageSize * pageNumber;
        if (start >= len) {
            return new ArrayList();
        }
        if (end < len) {
            return dataList.subList(start, end);
        } else {
            return dataList.subList(start, len);
        }
    }

    /**
     * 把List 对象转换成字符串
     *
     * @param items
     * @return
     * @throws
     */
    public static String GoodIn2Json(List<Map<String, Object>> items) {
        if (items == null) {
            return "";
        }
        /**JSONArray array = new JSONArray();
         JSONObject jsonObject = null;
         Map<String, Object> info = null;
         for (int i = 0; i < items.size(); i++) {
         info = items.get(i);
         jsonObject = new JSONObject();
         jsonObject.put("n", info.get("n"));
         jsonObject.put("v", info.get("v"));
         array.add(jsonObject);
         }*/
        return JacksonUtil.toJson(items);
    }

    /**
     * 删除ArrayList中重复元素，保持顺序
     */
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
    }


    public static List<Set<String>> getIdsList(Set<String> idsSet, int len) {
        List<Set<String>> idsList = new ArrayList<>();
        Object[] idsArrObj = idsSet.toArray();
        Set<String> pIdsSet = null;
        int arrLength = 0;
        int count = 0;
        arrLength = idsArrObj.length;
        if (arrLength > len) {
            count = arrLength / len;
            count = ((arrLength % len) > 0) ? count + 1 : count;
            for (int x = 1; x <= count; x++) {
                pIdsSet = new HashSet<>();
                for (int i = (x - 1) * len; i < arrLength; i++) {
                    if (i >= (x - 1) * len && i < x * len) {
                        pIdsSet.add(String.valueOf(idsArrObj[i]));
                    } else if (i >= x * len) {
                        continue;
                    }
                }
                idsList.add(pIdsSet);
            }
        } else {
            idsList.add(idsSet);
        }
        return idsList;
    }

    public static String listToString(List<String> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return " ";
    }


    /**
     * [格式化查询条件 将Ids转化为SQL 的 IN 语句
     * a,b -> 'a', 'b'
     * ]
     *
     * @return java.lang.StringBuffer
     * @author muyu
     */
    public static StringBuilder formatCondition(List<Long> values, StringBuilder sql) {
        if (sql == null) {
            sql = new StringBuilder();
        }
        for (int i = 0; i < values.size(); i++) {
            sql.append(SymbolConstant.SQL_SM).append(values.get(i)).append(SymbolConstant.SQL_SM);
            if (i != values.size() - 1) {
                sql.append(SymbolConstant.COMMA);
            }
        }
        return sql;
    }

}