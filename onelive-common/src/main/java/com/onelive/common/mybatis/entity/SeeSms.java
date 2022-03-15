package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信方式表
 * </p>
 *
 * @author ${author}
 * @since 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SeeSms implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 短信主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 短信方式名称
     */
    private String smsName;

    /**
     * apikey(或者账号)
     */
    private String apiKey;

    /**
     * 秘钥(或者密码)
     */
    private String secretKey;

    /**
     * 发送短信的UrL
     */
    private String sendUrl;

    /**
     * 查询短信余额url
     */
    private String queryUrl;

    /**
     * 短信发送有效时间单位：分钟(默认5分钟)
     */
    private Integer validTime;

    /**
     * 短信方式标识
     */
    private String smsCode;

    /**
     * 排序号
     */
    private Integer sortNum;

    /**
     * 是否开启：true-是、false-否
     */
    private Boolean isOpen;

    /**
     * 是否删除：true-是、false-否
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
