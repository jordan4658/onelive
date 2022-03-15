package com.onelive.common.mybatis.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysStreamConfig implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 线路名
     */
    private String name;

    /**
     * 状态 默认打开
     */
    private Boolean status;

    /**
     * 排序号
     */
    private Integer sortNum;
    
    /**
     * cdn服务商code
     */
    private String cdnCode;

    /**
     * 推流域名RTMP
     */
    private String pushDomainRtmp;

    /**
     * 推流CNAME
     */
    private String pushCname;

    /**
     * 推流鉴权主KEY
     */
    private String pushCheckKey;

    /**
     * 推流鉴权备KEY
     */
    private String pushCheckKeyBak;

    /**
     * 拉流域名RTMP
     */
    private String pullDomainRtmp;

    /**
     * 拉流CNAME
     */
    private String pullCname;

    /**
     * 拉流鉴权主KEY
     */
    private String pullCheckKey;

    /**
     * 拉流鉴权备KEY
     */
    private String pullCheckKeyBak;

    /**
     * secretId
     */
    private String secretId;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 成人直播 是否支持 默认支持
     */
    private Boolean isAdult;

    /**
     * 直播码率 1:1080 2:720 3:540
     */
    private String sharpness;

    /**
     * 备注
     */
    private String remark;


}
