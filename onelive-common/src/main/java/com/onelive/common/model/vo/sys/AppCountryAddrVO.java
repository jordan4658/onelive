package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("app端国家列表实体类")
public class AppCountryAddrVO {
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 地址名称
     */
    @ApiModelProperty("地址名称")
    private String name;

    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String lang;

    /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private Long pid;
}
