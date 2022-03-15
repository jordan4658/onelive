package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 第三方游戏列表
 * </p>
 */
@Data
@ApiModel
public class GameCategoryListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("关联第三方游戏平台代码")
    private String platformCode;

    @ApiModelProperty("分类编号")
    private Integer categoryId;

    @ApiModelProperty("游戏类型")
    private String gameType;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否开售 0否1是")
    private Boolean isWork;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


}
