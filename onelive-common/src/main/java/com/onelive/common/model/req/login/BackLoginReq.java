package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 登录请求类
 * @date 2021/3/30
 */
@Data
@ApiModel
public class BackLoginReq {

    @ApiModelProperty("登录账号")
    private String accLogin;

    @ApiModelProperty("登录密码,md5加密传输")
    private String password;

}
