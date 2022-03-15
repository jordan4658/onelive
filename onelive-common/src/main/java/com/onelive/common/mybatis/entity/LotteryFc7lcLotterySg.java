package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 北京PK10的开奖结果
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryFc7lcLotterySg implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 期号
     */
    private String issue;

    /**
     * 日期
     */
    private String date;

    /**
     * 开奖号码
     */
    private String number;

    /**
     * 彩票控开奖结果
     */
    private String cpkNumber;

    /**
     * 开彩网开奖结果
     */
    private String kcwNumber;

    /**
     * 实际开奖时间
     */
    private String time;

    /**
     * 官方开奖时间
     */
    private String idealTime;

    /**
     * 状态：WAIT 等待开奖 | AUTO 自动开奖 | HANDLE 手动开奖
     */
    private String openStatus;


}
