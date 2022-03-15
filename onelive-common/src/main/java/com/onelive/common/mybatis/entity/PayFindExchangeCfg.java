package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 汇率查询key配置
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayFindExchangeCfg implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 汇率查询key ID
     */
      @TableId(value = "exchange_key_id", type = IdType.AUTO)
    private Long exchangeKeyId;

    /**
     * 查询汇率的code（sys_parameter表中获取）
     */
    private String exchangeUrlCode;

    /**
     * 查询汇率url
     */
    private String exchangeUrl;

    /**
     * 请求Key
     */
    private String exchangeKey;

    /**
     * 每天请求频率限制（默认-1）：-1为无限制，其他数字为次数
     */
    private Integer frequency;

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

    /**
     * 是否删除
     */
    private Boolean isDelete;


}
