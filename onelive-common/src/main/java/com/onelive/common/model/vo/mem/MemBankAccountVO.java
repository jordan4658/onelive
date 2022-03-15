package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemBankAccountVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/6 11:34
 */
@Data
@ApiModel
public class MemBankAccountVO {

    @ApiModelProperty("会员银行卡id")
    private Integer bankAccid;

    @ApiModelProperty("是否默认银行卡：0-否、1-是")
    private Integer isDefault;

    @ApiModelProperty("开户银行地址")
    private String bankAddress;

    @ApiModelProperty("开户人姓名")
    private String bankAccountName;

    @ApiModelProperty("银行账号")
    private String bankAccountNo;

    @ApiModelProperty("银行名称标识符 如ICBC")
    private String bankCode;

    @ApiModelProperty("银行名")
    private String bankName;

    @ApiModelProperty("银行卡logo地址")
    private String bankLogo;

    @ApiModelProperty("创建时间 date")
    private Date createTime;


}
