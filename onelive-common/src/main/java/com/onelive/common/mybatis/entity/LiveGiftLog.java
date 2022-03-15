package com.onelive.common.mybatis.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveGiftLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 礼物记录ID
     */
      @TableId(value = "gift_log_id", type = IdType.AUTO)
    private Long giftLogId;
      
  /**
   * 	礼物购买订单号
   */
  private String giftLogNo;
      
  /**
   *		 资金流水表订单号
   */
  private String goldChangeNo;


    /**
     * 接收礼物人ID
     */
    private Long hostId;
    
    /**
     * 		一次直播的日志id
     */
    private Integer studioLogId;

    /**
     * 赠送礼物人ID
     */
    private Long givingId;

    /**
     * 礼物ID
     */
    private Integer giftId;

    /**
     * 礼物数量
     */
    private Integer giftNumber;

    /**
     * 礼物平台币价格
     */
    private BigDecimal giftPrice;

    /**
     * 赠送礼物时间
     */
    private Date givingTime;
    
    /**
     * 赠送人当地时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date givingLocalTime;

    /**
     * 赠送礼物人角色标识
     */
    private Integer givingRoleValue;

    /**
     * 是否是系统送的 0：否，1：是
     */
    private Boolean isSys;

    /**
     *  礼物连击ID（用于判断是否连击）
     */
    private String giftComboId;

    /**
     * 商户code值，默认为0
     */
    private String merchantCode = "0";


}
