package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动列表查询实体类
 */
@Data
@ApiModel
public class SysActivityListVO {

    @ApiModelProperty("活动列表")
    List<SysActivityInfoVO> list;

}
