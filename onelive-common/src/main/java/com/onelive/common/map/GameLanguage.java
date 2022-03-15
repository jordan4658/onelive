package com.onelive.common.map;


import com.ob.constant.LanguageEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统语言映射第三方语言关系
 */
public class GameLanguage {

    public static final Map<String, LanguageEnum> LanguageEnumMap=new HashMap<>();


    static {
        //初始化数据
        LanguageEnumMap.put("zh_CN",LanguageEnum.ZH_CN);
        LanguageEnumMap.put("id_ID",LanguageEnum.ID);
        LanguageEnumMap.put("zh_TW",LanguageEnum.ZH_TW);
        LanguageEnumMap.put("th_TH",LanguageEnum.TH);
        LanguageEnumMap.put("vi_VN",LanguageEnum.VI);
        LanguageEnumMap.put("ko_KR",LanguageEnum.KO);
        LanguageEnumMap.put("ms_MY",LanguageEnum.MY);
        LanguageEnumMap.put("en_US",LanguageEnum.EN_US);
    }

}
