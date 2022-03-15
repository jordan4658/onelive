package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryPlaySettingLang implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 玩法id
     */
    private Integer settingId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 投注示例
     */
    private String example;

    /**
     * 示例号码
     */
    private String exampleNum;

    /**
     * 玩法说明
     */
    private String playRemark;

    /**
     * 玩法简要说明
     */
    private String playRemarkSx;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}
