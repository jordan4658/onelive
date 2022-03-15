package com.onelive.common.model.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 查询活动实体类
 */
@Data
@ApiModel
public class SysAdvActivityVO {

    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 活动名称
     */
    @ApiModelProperty("活动名称")
    private String activityName;

    /**
     * 活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗
     */
    @ApiModelProperty("活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗")
    private Integer activityType;

    /**
     * 活动开始时间
     */
    @ApiModelProperty("活动开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 活动结束时间
     */
    @ApiModelProperty("活动结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 活动地区ID列表
     */
    @ApiModelProperty("活动地区code列表")
    private List<String> countryCodeList;

    /**
     * 活动地区名称列表
     */
    @ApiModelProperty("活动地区名称列表")
    private List<String> countryNameList;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imgUrl;

    /**
     * 跳转模块
     */
    @ApiModelProperty("跳转模块")
    private String skipModel;

    /**
     * 跳转路径
     */
    @ApiModelProperty("跳转路径")
    private String skipUrl;

    /**
     * 关联活动配置ID operate_activity_config.id
     */
    @ApiModelProperty("关联活动配置ID")
    private Long configId;

    /**
     * 游戏分类ID,关联lotter_category.id
     */
    @ApiModelProperty("游戏分类ID")
    private Integer lotteryCategoryId;

    /**
     * 游戏ID，关联lottery.id
     */
    @ApiModelProperty("游戏ID")
    private Integer lotteryId;

    /**
     * 活动排序
     */
    @ApiModelProperty("活动排序")
    private Integer sort;

    /**
     * 状态 0启用1禁用
     */
    @ApiModelProperty("状态 true启用false禁用")
    private Boolean isFrozen;

    @ApiModelProperty("多国语言列表")
    List<SysAdvActivityLangVO> langList;

}
