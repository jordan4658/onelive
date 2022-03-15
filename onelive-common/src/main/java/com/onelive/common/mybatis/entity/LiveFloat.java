package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveFloat implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 轮播名称
     */
    private String floatName;
    
    /**
     *	展示类型(位置)
     */
    private String showType;
    
    /**
     * 	游戏分类id
     */
    private Integer categoryId;
    
    /**
     * 	游戏id
     */
    private Integer lotteryId;
    
    /**
     * 适用国家id,多个逗号分隔,空即:所有
     */
    private String useCountry;

    /**
     * 跳转模块
     */
    private String skipModel;

    /**
     * 跳转路径
     */
    private String skipUrl;

    /**
     * 状态 1启用 0禁用
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
