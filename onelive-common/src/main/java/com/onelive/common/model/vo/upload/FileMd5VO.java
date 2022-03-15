package com.onelive.common.model.vo.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName FileMd5VO
 * @Desc md5VO
 * @Date 2021/3/23 10:26
 */
@ApiModel
@Data
public class FileMd5VO {
    @ApiModelProperty("文件路径")
    private String url;
    @ApiModelProperty("通过Md5是否存在历史上传记录，false否true是")
    private Boolean flag = false;
}
