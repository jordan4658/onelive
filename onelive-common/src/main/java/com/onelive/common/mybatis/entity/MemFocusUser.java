package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemFocusUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 关注人用户id
     */
    private Long focusId;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 用户关注主播后给主播奖励的钱
     */
    private BigDecimal award;

    /**
     * 商户code值
     */
    private String merchantCode;


    /**
     * 	关注主播是否开播进行推送提醒 默认提醒
     */
    private Boolean isRemind;
    
    /**
     * 	0:取消关注 1:关注  (取消关注不删除记录数据,用以记录主播奖励,单个用户反复关注不多给奖励)
     */
    private Boolean isFocus;

}
