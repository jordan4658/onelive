package com.onelive.common.model.vo.mem;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 会员银行卡列表
 */
@Data
@ApiModel
public class MemBankAccountBackVO {

    @ApiModelProperty("会员银行卡id")
    private Long bankAccid;

    @ApiModelProperty("开户行地址")
    private String bankAddress;

    @ApiModelProperty("开户姓名")
    private String bankAccountName;

    @ApiModelProperty("银行卡号")
    private String bankAccountNo;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
