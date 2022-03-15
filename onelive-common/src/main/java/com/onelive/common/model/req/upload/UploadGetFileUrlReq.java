package com.onelive.common.model.req.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * app版本管理Req
 * </p>
 */
@Data
@ApiModel
public class UploadGetFileUrlReq {

	@ApiModelProperty("md5字符串")
    private String md5Flag;
}
