package com.onelive.common.model.dto.common;

import cn.hutool.core.util.StrUtil;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 国家和语种
 */
@Data
@ApiModel("当前用户国家和语言")
public class CurrentUserCountryLangDTO implements Serializable {
    @ApiModelProperty("国家code")
    private String countryCode;

    @ApiModelProperty("语种")
    private String lang;

    public CurrentUserCountryLangDTO(){
        this.countryCode = LoginInfoUtil.getCountryCode();
        if(StringUtils.isBlank(this.countryCode)){
            this.countryCode = LangConstants.LANG_VN;
        }
        this.lang = LoginInfoUtil.getLang();
        if(StrUtil.isBlank(this.lang)){
            this.lang = LangConstants.LANG_VN;
        }
    }
}
