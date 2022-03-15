package com.onelive.common.mybatis.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 广告公告语种表
 * </p>
 *
 * @author ${author}
 * @since 2021-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAdvNoticeLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告id
     */
    private Long noticeId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 公告内容
     */
    private String noticeContent;

}
