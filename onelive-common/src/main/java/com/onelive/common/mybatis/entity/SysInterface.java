package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台系统接口信息
 * </p>
 *
 * @author 
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysInterface implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 接口id
     */
      @TableId(value = "itf_id", type = IdType.AUTO)
    private Long itfId;

    /**
     * 所属系统 live-manage live-app live
     */
    private String ofSystem;

    /**
     * 接口名称
     */
    private String itfName;

    /**
     * 接口地址
     */
    private String itfUrl;

    /**
     * 接口说明
     */
    private String itfDesc;

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


}
