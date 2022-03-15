package com.onelive.common.model.vo.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FileVO
 * @Desc 文件上传VO
 * @Date 2021/3/23 10:26
 */
@ApiModel
@Data
public class FileVO {
    @ApiModelProperty("文件名称")
    private String filename;
    @ApiModelProperty("文件路径")
    private String filekeyurl;
    @ApiModelProperty("是否成功 1成功 0失败")
    private Integer flag;
}
