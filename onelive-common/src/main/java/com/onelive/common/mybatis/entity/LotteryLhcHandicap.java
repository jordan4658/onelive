package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 六合彩盘口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryLhcHandicap implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 期号
     */
    private String issue;

    /**
     * 开奖时间
     */
    private String startlottoTime;

    /**
     * 自动开盘时间
     */
    private String startTime;

    /**
     * 自动封盘时间
     */
    private String endTime;

    /**
     * 允许自动开盘: 0 不允许,1 允许
     */
    private Integer automation;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}
