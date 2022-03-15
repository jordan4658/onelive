package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @since 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformShareConfig implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分享类型
     */
    private String shareType;

    /**
     * 每次分享后的奖励金额
     */
    private BigDecimal award;

    /**
     * 	每天可以领取的次数
     */
    private Integer awardTimes;

    /**
     * 	适用国家id,多个逗号分隔,空即:所有
     */
    private String useCountry;

    /**
     * 	是否展示 0：否
     */
    private Boolean isShow;

    /**
     * 		修改人(后台操作人登录账号)
     */
    private String updateUser;

    /**
     * 	修改时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
