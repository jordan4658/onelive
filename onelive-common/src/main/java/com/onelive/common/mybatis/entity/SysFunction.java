package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台系统(运营后台)功能
 * </p>
 *
 * @author 
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFunction implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 功能id
     */
      @TableId(value = "func_id", type = IdType.AUTO)
    private Long funcId;

    /**
     * 父功能id
     */
    private Long parentFuncId;

    /**
     * 所属系统  live-manage运营管理后台
     */
    private String ofSystem;

    /**
     * 功能类别  menu菜单   button按钮   tabTAB
     */
    private String funcType;

    /**
     * 功能名称
     */
    private String funcName;

    /**
     * 功能状态 0正常   9停用
     */
    private Integer funcStatus;

    /**
     * 功能url或参数
     */
    private String funcUrl;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


    /**
     * 序号
     */
    private Integer orderSeq;
}
