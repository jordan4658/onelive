package com.onelive.common.model.req.live;

import cn.hutool.core.util.StrUtil;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.utils.Login.LoginInfoUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("App端直播分类游戏列表查询请求参数")
public class AppLiveGameListReq {

    @ApiModelProperty(value = "语种", hidden = true)
    private String lang;

    @ApiModelProperty(value = "标签code值", hidden = true)
    private String code;

    public AppLiveGameListReq(){
        this.lang = LoginInfoUtil.getLang();
        if(StrUtil.isBlank(this.lang)){
            this.lang = LangConstants.LANG_CN;
        }
    }
}
