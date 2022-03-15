package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemAccountBankDeleteReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 15:35
 */
@Data
@ApiModel
public class MemAccountBankDeleteReq {

    @ApiModelProperty(value = "用户银行卡id[必填]",required = true)
    private Long bankAccid;
}
