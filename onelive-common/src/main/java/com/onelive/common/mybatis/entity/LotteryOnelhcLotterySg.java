package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 六合彩的开奖结果
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryOnelhcLotterySg implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日期
     */
    private String date;

    /**
     * 第几期
     */
    private String issue;

    /**
     * 开奖号码
     */
    private String number;

    /**
     * 更新时间
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
