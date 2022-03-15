package com.onelive.common.model.req.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动管理列表保存接口请求参数
 */
@Data
@ApiModel
public class SysAdvActivitySaveReq {
    /**
     * 主键
     */
    @ApiModelProperty("主键ID, 更新的时候传入")
    private Long id;
    /**
     * 活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗
     */
    @ApiModelProperty(value = "活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗[必填]",required = true)
    private Integer activityType;

    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间[必填]",required = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间[必填]",required = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 活动地区ID列表
     */
    @ApiModelProperty(value = "活动地区code列表[必填]",required = true)
    private List<String> countryCodeList;

    /**
     * 活动地区名称列表
     */
    @ApiModelProperty(value = "活动地区名称列表[必填]",required = true)
    private List<String> countryNameList;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imgUrl;

    /**
     * 跳转模块 0无 1链接 2活动 3游戏
     */
    @ApiModelProperty("跳转模块 0无 1链接 2活动 3游戏")
    private String skipModel;

    /**
     * 跳转路径
     */
    @ApiModelProperty("跳转路径")
    private String skipUrl;

    /**
     * 关联活动配置ID operate_activity_config.id
     */
    @ApiModelProperty(value = "关联活动配置ID[必填]",required = true)
    private Long configId;

    /**
     * 游戏分类ID,关联lotter_category.id
     */
    @ApiModelProperty(value = "游戏分类ID[必填]",required = true)
    private Integer lotteryCategoryId;

    /**
     * 游戏ID，关联lottery.id
     */
    @ApiModelProperty(value = "游戏ID[必填]",required = true)
    private Integer lotteryId;

    /**
     * 活动排序
     */
    @ApiModelProperty(value = "活动排序[必填]",required = true)
    private Integer sort;

    /**
     * 状态 0启用1禁用
     */
    @ApiModelProperty(value = "状态 true禁用 false启用[必填]",required = true)
    private Boolean isFrozen;

    @ApiModelProperty(value = "多国语言信息列表[必填]",required = true)
    private List<SysAdvActivityLangSaveReq> langList;

}
