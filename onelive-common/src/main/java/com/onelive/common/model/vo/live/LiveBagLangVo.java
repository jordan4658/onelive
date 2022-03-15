package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("背包物品保存多语言返回参数")
public class LiveBagLangVo {
    @ApiModelProperty("项目ID")
    private Long id;

    @ApiModelProperty("语言")
    private String lang;

    @ApiModelProperty("背包物品名称")
    private String bagName;
}
