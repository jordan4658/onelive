package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * app包信息Vo
 */
@Data
@ApiModel
public class AppPackageInfoVO {

    @ApiModelProperty("升级版本号")
    private String upgradeVersion;

    @ApiModelProperty("展示版本号")
    private String showVersion;

    @ApiModelProperty("app下载地址")
    private String downUrl;

    @ApiModelProperty("app包大小单位：MB")
    private BigDecimal fileSize;

//    @ApiModelProperty("是否代理安装包：0=否，1=是")
//    private Integer isAgent;
//
//    @ApiModelProperty("是否安卓：1-android安装包、2-IOS安装包")
//    private Integer appType;


}
