package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 德州快三开奖结果
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryDzksLotterySg implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 期号
     */
    private String issue;

    /**
     * 百位
     */
    private Integer bai;

    /**
     * 十位
     */
    private Integer shi;

    /**
     * 个位
     */
    private Integer ge;

    /**
     * 开奖结果
     */
    private String number;

    /**
     * 实际开奖时间
     */
    private Date time;

    /**
     * 官方开奖时间
     */
    private Date idealTime;

    /**
     * 状态：WAIT 等待开奖 | AUTO 自动开奖 | HANDLE 手动开奖
     */
    private String openStatus;


}
