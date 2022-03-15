package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间悬浮窗")
public class LiveFloatDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("礼物多语言数组")
	private List<LiveFloatLangDto> LiveFloatLangList;
    
    @ApiModelProperty("card:主播名片  game:游戏轮播")
    private String showType;
    
    @ApiModelProperty("轮播名称")
    private String floatName;

    @ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
    private String useCountry;

    @ApiModelProperty("跳转模块")
    private String skipModel;

    @ApiModelProperty("跳转路径")
    private String skipUrl;
    
    @ApiModelProperty("游戏分类id")
    private Integer categoryId;
    
    @ApiModelProperty("游戏id")
    private Integer lotteryId;

    @ApiModelProperty("状态 true启用 false禁用")
    private Boolean isShow;

    @ApiModelProperty("商户code值，默认值为0")
    private String merchantCode;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;
}
