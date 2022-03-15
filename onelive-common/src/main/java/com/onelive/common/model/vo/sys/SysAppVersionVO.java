package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * app版本管理VO
 * </p>
 */
@Data
@ApiModel
public class SysAppVersionVO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("app客户端编码 ios/android")
    private String code;

	@ApiModelProperty("app名称")
    private String name;

    @ApiModelProperty("app类型 1客户端, 2主播端")
    private Integer appType;

    @ApiModelProperty("展示版本号")
    private String showVersion;

	@ApiModelProperty("升级版本号")
    private Integer upgradeVersion;

	@ApiModelProperty("下载地址")
    private String downUrl;

    @ApiModelProperty("文件大小")
    private BigDecimal fileSize;

    @ApiModelProperty("更新内容")
    private String content;

	@ApiModelProperty("强制更新 0不强制 1强制")
    private Integer isForced;

    @ApiModelProperty("是否静默下载")
    private Boolean isSilentDownload;

	@ApiModelProperty("更新时间")
    private Date updateTime;

}
