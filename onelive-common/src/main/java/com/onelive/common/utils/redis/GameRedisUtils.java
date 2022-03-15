package com.onelive.common.utils.redis;

import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.utils.others.JacksonUtil;
import com.onelive.common.utils.others.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 第三方游戏Redis工具类
 */
public class GameRedisUtils extends RedisUtil{

    /**
     * 通过categoryId获取GameCategory对象
     * @param categoryId
     * @return
     */
    public static GameCategory getGameCategory(Integer categoryId) {
        String key = RedisKeys.GAME_CATEGORY+categoryId;

        String json = get(key);
        if(StringUtils.isNotBlank(json)){
            return JacksonUtil.fromJson(json,GameCategory.class);
        }
        return null;
    }

    /**
     * 保存GameCategory对象
     * @param category
     */
    public static void setGameCategory(GameCategory category) {
        if(category!=null) {
            String key = RedisKeys.GAME_CATEGORY + category.getCategoryId();
            String json = JacksonUtil.toJson(category);
            set(key, json);
        }
    }

    /**
     * 获取多语言列表
     * @return
     */
    public static List<String> getLangList(){
        List<String> langList = get(RedisKeys.SYS_LANG_LIST);
        if(langList==null){
            langList = new LinkedList<>();
        }
        return langList;
    }
    /**
     * 获取多语言列表
     * @return
     */
    public static LinkedList<String> getLangList2(){
        LinkedList<String> langList = get(RedisKeys.SYS_LANG_LIST);
        if(langList==null){
            langList = new LinkedList<>();
        }
        return langList;
    }
}
