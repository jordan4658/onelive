package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 *  直播tab栏配置信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveTabItem implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 选中的图标
     */
    private String iconSelectedUrl;

    /**
     * 未选中的图标
     */
    private String iconUnselectedUrl;


    /**
     *  商户编码
     */
    private String merchantCode;


    /**
     * 唯一标识: live直播 game游戏 recharge充值 my我的
     */
    private String iconCode;

    /**
     * 排序 从小到大排序
     */
    private Integer sort;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDelete;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

}
