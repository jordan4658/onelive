package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统参数
 * </p>
 *
 * @author ${author}
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysParameter implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 系统参数id
     */
      @TableId(value = "param_id", type = IdType.AUTO)
    private Long paramId;

      /**
       * 系统参数类型
       */
      private String paramType;
      
    /**
     * 系统参数代码
     */
    private String paramCode;

    /**
     * 系统参数名称
     */
    private String paramName;

    /**
     * 参数说明
     */
    private String remark;

    /**
     * 系统参数值
     */
    private String paramValue;

    /**
     * 排序值,值越小越靠前
     */
    private Integer sortBy;

    /**
     * 系统参数启用状态0启用9未启用
     */
    private Integer paramStatus;

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
