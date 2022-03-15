package com.onelive.common.model.vo.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询有赛果的彩票
 */
@Data
@ApiModel
public class LotterySGListVO {

    /**
     * 彩票名称
     */
    @ApiModelProperty("彩票名称")
    private String name;

    /**
     * 对应的数据表
     */
    @ApiModelProperty("对应的数据表")
    private String game;

}
