package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户投注单
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryOrderRecord implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 购买的期号
     */
    private String issue;

    /**
     * 购彩用户id
     */
    private Integer userId;

    /**
     * 彩种id
     */
    private Integer lotteryId;

    /**
     * 追号id（为0则为非追号订单）
     */
    private Integer appendId;

    /**
     * 开奖号码
     */
    private String openNumber;

    /**
     * 来源：Android | IOS | WEB
     */
    private String source;

    /**
     * 购彩来源
     */
    private Integer buySource;

    /**
     * 订单状态：正常：NORMAL；撤单：BACK;
     */
    private String status;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 国家id
     */
    private Long countryId;


}
