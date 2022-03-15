package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * app版本管理
 * </p>
 *
 * @author ${author}
 * @since 2022-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAppVersion implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * app客户端编码
     */
    private String code;

    /**
     * app名称
     */
    private String name;

    /**
     * 展示版本号
     */
    private String showVersion;

    /**
     * 升级版本号
     */
    private Integer upgradeVersion;

    /**
     * 是否静默下载 0否 1是
     */
    private Boolean isSilentDownload;

    /**
     * 下载地址
     */
    private String downUrl;

    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * app类型 1客户端, 2主播端
     */
    private Integer appType;

    /**
     * 更新内容
     */
    private String content;

    /**
     * 强制更新 0不强制 1强制
     */
    private Integer isForced;

    /**
     * 状态 0待发布 1已发布
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 后台更新人账号
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
