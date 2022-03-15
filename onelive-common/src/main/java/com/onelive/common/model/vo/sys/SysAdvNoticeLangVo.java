package com.onelive.common.model.vo.sys;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "公告多语言")
public class SysAdvNoticeLangVo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 公告id
     */
    @ApiModelProperty("公告id")
    private Long noticeId;

    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String lang;

    /**
     * 公告名称
     */
    @ApiModelProperty("公告名称")
    private String noticeName;

    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private String noticeContent;

}
