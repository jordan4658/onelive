package com.onelive.common.model.dto.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "直播间快捷回复文字传输类")
public class RoomFastWordsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@ApiModelProperty("自增id")
	private Integer id;

	/**
	 * 快捷回复语句
	 */
	@ApiModelProperty("快捷回复语句")
	private String context;

	/**
	 * 是否展示，默认是
	 */
	@ApiModelProperty("是否展示，默认是")
	private Boolean isShow;

	/**
	 * 语言标识
	 */
	@ApiModelProperty("语言标识")
	private String lang;
	
	@ApiModelProperty("排序字段,越大越前面")
	private Integer sortNum;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
    
    @ApiModelProperty("创建时间")
    private String createTime;
    
}
