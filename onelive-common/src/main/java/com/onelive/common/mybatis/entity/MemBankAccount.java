package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 会员银行卡列表
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemBankAccount implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 会员银行卡id
     */
      @TableId(value = "bank_accid", type = IdType.AUTO)
    private Long bankAccid;

    @ApiModelProperty("是否默认银行卡：0-否、1-是")
    private Integer isDefault;

    /**
     * 会员账号
     */
    private String account;

    /**
     * 开户银行地址
     */
    private String bankAddress;

    /**
     * 开户人姓名
     */
    private String bankAccountName;

    /**
     * 银行账号
     */
    private String bankAccountNo;

    /**
     * 银行名称标识符 如ICBC
     */
    private String bankCode;

    /**
     * 是否删除： 0-否，1-是
     */
    private Boolean isDelete;

    /**
     * 创建人账号
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
