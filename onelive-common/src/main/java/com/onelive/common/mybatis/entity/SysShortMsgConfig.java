package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 		短信配置
 * </p>
 *
 * @author ${author}
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysShortMsgConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 	状态 true启用false禁用
	 */
	private Boolean isFrozen;

	/**
	 * 区号
	 */
	private String areaCode;

	/**
	 * 配置名称
	 */
	private String configName;

	/**
	 * 用户名
	 */
	private String msgUser;

	/**
	 * 密码
	 */
	private String msgPwd;

	/**
	 * 不同服务商的格式不一,代码里有具体的dto类
	 */
	private String config;

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
