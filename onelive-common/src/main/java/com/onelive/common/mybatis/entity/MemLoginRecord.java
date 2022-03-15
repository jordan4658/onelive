package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 登录记录表
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemLoginRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员账号
     */
    private String account;

    /**
     * 会员类型，0-普通用户
     */
    private Integer userType;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登录地区
     */
    private String area;

    /**
     * 登录终端：app、mobile、pc
     */
    private String loginSource;

    /**
     * 登录设备型号
     */
    private String loginDevice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
