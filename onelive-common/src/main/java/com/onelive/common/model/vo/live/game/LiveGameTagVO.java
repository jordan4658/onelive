package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 游戏标签实体类
 */
@Data
@ApiModel("直播游戏标签实体类")
public class LiveGameTagVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 国家地区ID
     */
    @ApiModelProperty("国家地区code")
    private List<String> countryCodeList;

    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String name;

    /**
     * 排序
     */
//    @ApiModelProperty("排序")
//    private Integer sort;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示 0否1是")
    private Boolean isShow;

    @ApiModelProperty("多语言列表")
    private List<LiveGameTagLangVO> langList;

}
