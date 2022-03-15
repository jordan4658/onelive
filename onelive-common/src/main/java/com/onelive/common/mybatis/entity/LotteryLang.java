package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 彩种名称国际化
 * </p>
 *
 * @author ${author}
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联彩种ID lottery.lottery_id
     */
    private Integer lotteryId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 彩种名称
     */
    private String lotteryName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
