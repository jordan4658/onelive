package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户第三方游戏钱包名称多语言表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemWalletLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 钱包类型 关联mem_wallet.wallet_type
     */
    private Integer walletType;

    /**
     * 语言标识
     */
    private String lang;

    /**
     * 钱包名称
     */
    private String name;

    /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
