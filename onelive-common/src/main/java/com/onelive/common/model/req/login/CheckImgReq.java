package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName : CheckImgReq
 * @Description : 验证图片验证码请求类
 */
@Data
@ApiModel(value = "验证图片验证码请求类")
public class CheckImgReq {
    @ApiModelProperty(value = "图片验证码")
    private String imgCode;

    @ApiModelProperty(value = "图片标识")
    private String captchaKey;
}
