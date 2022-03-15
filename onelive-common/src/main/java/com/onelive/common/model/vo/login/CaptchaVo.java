package com.onelive.common.model.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@Data
@ApiModel(value = "验证码返回类")
public class CaptchaVo {
    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "唯一标识")
    private String captchaKey;
    /**
     * 图片验证码URL
     */
    @ApiModelProperty(value = "图片base64字符串")
    private String img64Code;

    /**
     * 图片验证码
     */
    @ApiModelProperty(value = "图片验证码",hidden = true)
    private String imgCode;
}
