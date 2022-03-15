package com.onelive.common.model.dto.live;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关注列表传输类
 */
@Data
public class LiveFocusListDTO {
    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;

    private Long userId;

    private String merchantCode;
}
