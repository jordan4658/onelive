package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemBankAccountVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/6 11:34
 */
@Data
@ApiModel
public class MemBankAccountUpdateReq {

    @ApiModelProperty(value = "会员银行卡id", required = true)
    private Integer bankAccid;

    @ApiModelProperty(value = "开户银行地址", required = true)
    private String bankAddress;

    @ApiModelProperty(value = "开户人姓名", required = true)
    private String bankAccountName;

    @ApiModelProperty(value = "银行账号", required = true)
    private String bankAccountNo;

    @ApiModelProperty(value = "银行名称标识符 如ICBC", required = true)
    private String bankCode;

    @ApiModelProperty(value = "手机号码", required = true)
    private String mobilePhone;

    @ApiModelProperty(value = "手机验证码", required = true)
    private String smsCode;
}
