package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台系统用户信息
 * </p>
 *
 * @author ${author}
 * @since 2022-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
      @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 系统账号(登录用)
     */
    private String accLogin;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 登录密码MD5
     */
    private String passwordMd5;

    /**
     * 登录总次数
     */
    private Integer loginNum;

    /**
     * 账号状态 0正常 9禁止登陆 
     */
    private Integer accStatus;

    /**
     * 登录ip地址
     */
    private String clientIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;

    /**
     * 国家sys_country.country_code
     */
    private String countryCode;


}
