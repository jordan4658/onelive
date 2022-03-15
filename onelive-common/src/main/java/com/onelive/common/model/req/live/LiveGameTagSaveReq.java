package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 游戏标签保存请求参数
 */
@Data
@ApiModel("直播游戏标签保存请求参数")
public class LiveGameTagSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    /**
     * 国家地区ID
     */
    @ApiModelProperty(value = "国家地区code[必填]",required = true)
    private List<String> countryCodeList;
    /**
     * 排序
     */
//    @ApiModelProperty(value = "排序[必填]",required = true)
//    private Integer sort;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty(value = "是否显示 0否1是[必填]",required = true)
    private Boolean isShow;


    @ApiModelProperty(value = "标签多语言列表[必填]",required = true)
    private List<LiveGameTagLangSaveReq> langList;

}
