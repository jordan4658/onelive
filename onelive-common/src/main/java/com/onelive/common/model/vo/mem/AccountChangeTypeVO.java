package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname MemFlowRestrictReq
 * @Des 会员账变类型
 * @Author 凯文
 * @Date 2021/5/1414:42
 */
@Data
@ApiModel
public class AccountChangeTypeVO {

    @ApiModelProperty("账变code")
    private Integer code;

    @ApiModelProperty("账变类型名称")
    private String name;
}
