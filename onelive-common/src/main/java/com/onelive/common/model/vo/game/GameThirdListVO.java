package com.onelive.common.model.vo.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询第三方游戏列表
 */
@Data
@ApiModel("第三方游戏列表实体类")
public class GameThirdListVO {
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 游戏编号
     */
    @ApiModelProperty("游戏编号")
    private Long gameId;
    /**
     * 游戏平台代码, 关联game_third_platform.platform_code
     */
    @ApiModelProperty("游戏平台代码")
    private String platformCode;

    /**
     * 游戏名称
     */
    @ApiModelProperty("游戏名称")
    private String name;

    @ApiModelProperty("唯一标识")
    private String code;

    /**
     * 游戏分类id 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类id")
    private Integer categoryId;

    /**
     * 游戏运行状态：1：维护中，2：启用，3：停用
     */
    @ApiModelProperty("游戏运行状态：1：维护中，2：启用，3：停用")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 是否开售 0否1是
     */
    @ApiModelProperty("是否开售")
    private Boolean isWork;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 支持地区 zh_CN,en_US
     */
    @ApiModelProperty(value = "支持地区", hidden = true)
    private String countryCodeList;

}
