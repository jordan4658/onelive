package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 查询交易类型配置表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayTransactionTypeCfg implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "transaction_type_id", type = IdType.AUTO)
    private Long transactionTypeId;

    /**
     * 类型code（对应业务参数表中的p_param_code=TRANSACTION_TYPE）
     */
    private String transactionTypeCode;

    /**
     * 类型名称
     */
    private String transactionTypeName;

    /**
     * 语言标识
     */
    private String langValue;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
