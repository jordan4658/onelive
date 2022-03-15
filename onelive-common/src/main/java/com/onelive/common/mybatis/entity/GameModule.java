package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 游戏模块表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameModule implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型id
     */
    private Long typeId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 状态 0显示1隐藏
     */
    private Boolean isHide;

    /**
     * 显示数量
     */
    private Integer showNum;

    /**
     * 语言
     */
    private String lang;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 后台更新人账号
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
