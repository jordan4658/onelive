package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 彩票中奖公告文案多语言内容
 * </p>
 *
 * @author ${author}
 * @since 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveNoticeTextLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 语言
     */
    private String lang;

    /**
     * 文案内容
     */
    private String text;

    /**
     * 配置ID, 关联live_notice_text.id
     */
    private Long configId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
