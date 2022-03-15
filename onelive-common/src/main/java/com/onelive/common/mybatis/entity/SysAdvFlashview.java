package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 广告首页轮播表
 * </p>
 *
 * @author ${author}
 * @since 2021-11-29
 */
/**
 * @author admin
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAdvFlashview implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 轮播名称
     */
    private String flashviewName;
    
	/**
	 * 类型code，显示在app不同的页面
	 */
	private String typeCode;

    /**
     * 适用国家id,多个逗号分隔,空即:所有
     */
    private String useCountry;

    /**
     * 展示开始时间，系统时间
     */
    private Date startDate;

    /**
     * 展示结束时间，系统时间
     */
    private Date endDate;

	/**
	 * 	是否展示：1展示 0不展示
	 */
	private Boolean isShow;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人(后台操作人登录账号)
     */
    private String createUser;

    /**
     * 修改人(后台操作人登录账号)
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
