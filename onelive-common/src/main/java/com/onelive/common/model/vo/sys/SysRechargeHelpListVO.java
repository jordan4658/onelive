package com.onelive.common.model.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * VO
 * </p>
 */
@Data
@ApiModel
public class SysRechargeHelpListVO {

	@ApiModelProperty("主键")
    private Long id;
	
	@ApiModelProperty("标题")
    private String title;

	@ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
