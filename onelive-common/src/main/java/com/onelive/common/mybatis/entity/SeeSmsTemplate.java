package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 短信模板表
 * </p>
 *
 * @author ${author}
 * @since 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SeeSmsTemplate implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 短信模板主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 短信模板内容
     */
    @TableField("templateContent")
    private String templateContent;

    /**
     * 短信模板标识
     */
    @TableField("templateCode")
    private String templateCode;

    /**
     * 排序号
     */
    @TableField("sortNum")
    private Integer sortNum;

    /**
     * 是否开启：true-是、false-否
     */
    @TableField("isOpen")
    private Boolean isOpen;

    /**
     * 是否删除：true-是、false-否
     */
    @TableField("isDelete")
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
