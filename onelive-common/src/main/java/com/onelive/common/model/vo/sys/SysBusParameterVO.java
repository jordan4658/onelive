package com.onelive.common.model.vo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version V1.0
 * @ClassName: SysBusParameterVO
 * @date 创建时间：2021/4/6 15:42
 */
@Data
@ApiModel
public class SysBusParameterVO {

    @ApiModelProperty("业务参数代码")
    private String paramCode;

    @ApiModelProperty("业务参数值")
    private String paramValue;

    @ApiModelProperty("业务参数父代码")
    private String pParamCode;







}
