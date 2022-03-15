package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 货币国际汇率
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayExchangeCurrency implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 汇率ID
     */
      @TableId(value = "exchange_currency_id", type = IdType.AUTO)
    private Long exchangeCurrencyId;

    /**
     * 汇率国家代码
     */
    private String currencyCode;

    /**
     * 转换前的货币代码
     */
    private String currencyF;

    /**
     * 转换前的货币名称
     */
    private String currencyFName;

    /**
     * 转换成的货币代码
     */
    private String currencyT;

    /**
     * 转换成的货币名称
     */
    private String currencyTName;

    /**
     * 转换金额
     */
    private String currencyFD;

    /**
     * 当前汇率（其他货币兑换美金的汇率）
     */
    private String exchange;

    /**
     * 提现汇率：比当前汇率高（默认为当前汇率）
     */
    private String txExchange;

    /**
     * 充值汇率：比当前汇率低（默认为当前汇率）
     */
    private String czExchange;

    /**
     * 要兑换的单位货币
     */
    private String currencyUnit;

    /**
     * 是否删除
     */
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
     * 查询时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
