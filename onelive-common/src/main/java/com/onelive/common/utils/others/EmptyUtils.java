package com.onelive.common.utils.others;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @description ：
 * @date ：2021/06/02
 * @Description :
 */
public class EmptyUtils {

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj){
        boolean empty=false;
        if(obj==null){
            empty=true;
        }else if(obj instanceof String ||obj instanceof StringBuffer ||obj instanceof StringBuilder){
            empty="".equals(String.valueOf(obj));
        }
        else if(obj instanceof Collection){
            Collection conn=(Collection)obj;
            empty=conn.isEmpty();
        }
        else if(obj instanceof Map){
            Map map=(Map)obj;
            empty=map.isEmpty();
        }
        else if(obj.getClass().isArray()){
            empty= Array.getLength(obj)<1;
        }else{

        }
        return empty;
    }

    public static boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }
}
