package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业务参数
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysBusParameter implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 业务参数ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务参数名称
     */
    private String paramName;

    /**
     * 业务参数代码
     */
    private String paramCode;

    /**
     * 业务参数父代码
     */
    private String pParamCode;

    /**
     * 业务参数值
     */
    private String paramValue;

    /**
     * 参数说明
     */
    private String remark;

    /**
     * 系统参数启用状态 0启用 9未启用
     */
    private Integer status;

    /**
     * 排序权重
     */
    private Integer sortby;

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
