package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "首页栏目")
public class SysStreamConfigDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("id")
    private Integer id;

    /**
     * 线路名
     */
    @ApiModelProperty("线路名")
    private String name;

    /**
     * 状态 默认打开
     */
    @ApiModelProperty("状态 默认打开")
    private Boolean status;

    /**
     * 排序号
     */
    @ApiModelProperty("排序号")
    private Integer sortNum;
    
    /**
     * cdn服务商code
     */
    @ApiModelProperty("cdn服务商code")
    private String cdnCode;

    /**
     * 推流域名RTMP
     */
    @ApiModelProperty("推流域名RTMP")
    private String pushDomainRtmp;

    /**
     * 推流CNAME
     */
    @ApiModelProperty("推流CNAME")
    private String pushCname;

    /**
     * 推流鉴权主KEY
     */
    @ApiModelProperty("推流鉴权主KEY")
    private String pushCheckKey;

    /**
     * 推流鉴权备KEY
     */
    @ApiModelProperty("推流鉴权备KEY")
    private String pushCheckKeyBak;

    /**
     * 拉流域名RTMP
     */
    @ApiModelProperty("拉流域名RTMP")
    private String pullDomainRtmp;

    /**
     * 拉流CNAME
     */
    @ApiModelProperty("拉流CNAME")
    private String pullCname;

    /**
     * 拉流鉴权主KEY
     */
    @ApiModelProperty("拉流鉴权主KEY")
    private String pullCheckKey;

    /**
     * 拉流鉴权备KEY
     */
    @ApiModelProperty("拉流鉴权备KEY")
    private String pullCheckKeyBak;

    /**
     * secretId
     */
    @ApiModelProperty("secretId")
    private String secretId;

    /**
     * secretKey
     */
    @ApiModelProperty("secretKey")
    private String secretKey;

    /**
     * 成人直播 是否支持 默认支持
     */
    @ApiModelProperty("成人直播 是否支持 默认支持")
    private Boolean isAdult;

    /**
     * 直播码率 1:1080 2:720 3:540
     */
    @ApiModelProperty("直播码率 1:1080 2:720 3:540")
    private String sharpness;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
