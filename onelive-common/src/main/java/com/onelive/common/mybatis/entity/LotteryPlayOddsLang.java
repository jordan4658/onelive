package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 赔率选项名称国际化
 * </p>
 *
 * @author ${author}
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryPlayOddsLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联赔率选项ID
     */
    private Integer oddsId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 选项名称
     */
    private String oddsName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
