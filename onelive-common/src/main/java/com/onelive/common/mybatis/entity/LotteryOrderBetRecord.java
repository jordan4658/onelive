package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户投注单-注号记录
 * </p>
 *
 * @author ${author}
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryOrderBetRecord implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 投注单id
     */
    private Integer orderId;

    /**
     * 彩种类别id
     */
    private Integer cateId;

    /**
     * 彩种id
     */
    private Integer lotteryId;

    /**
     * 玩法id
     */
    private Integer playId;

    /**
     * 玩法配置id
     */
    private Integer settingId;

    /**
     * 玩法名称
     */
    private String playName;

    /**
     * 购买的期号
     */
    private String issue;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 投注号码
     */
    private String betNumber;

    /**
     * 投注总注数
     */
    private Integer betCount;

    /**
     * 投注金额
     */
    private BigDecimal betAmount;

    /**
     * 中奖金额
     */
    private BigDecimal winAmount;

    /**
     * 返点金额
     */
    private BigDecimal backAmount;

    /**
     * 大神推单id, 0为自主投注
     */
    private Integer godOrderId;

    /**
     * 状态：中奖:WIN | 未中奖:NO_WIN | 等待开奖:WAIT | 和:HE | 撤单:BACK
     */
    private String tbStatus;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 中奖注数
     */
    private String winCount;

    /**
     * 是否推单 0 否 1 是
     */
    private Integer isPush;

    /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 来源：Android | IOS | WEB
     */
    private String source;

    /**
     * 直播间购彩对应的家族id
     */
    private Long familyId;

    /**
     * 直播房间id, 关联的是live_studio_list.studio_num
     */
    private String studioNum;

    /**
     * 显示的彩票名称
     */
    private String showLotteryName;

    /**
     * 显示的玩法名称
     */
    private String showPlayName;

    /**
     * 显示的投注号码
     */
    private String showBetNumber;

    /**
     * 玩法ID,关联lottery_play.id
     */
    private Integer pid;


}
