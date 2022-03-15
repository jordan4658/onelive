package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 彩种玩法名称国际化
 * </p>
 *
 * @author ${author}
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryPlayLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联玩法ID
     */
    private Integer playId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 玩法名称
     */
    private String playName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
