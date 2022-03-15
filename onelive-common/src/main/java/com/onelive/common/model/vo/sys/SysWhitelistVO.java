package com.onelive.common.model.vo.sys;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * ip白名单
 * </p>
 */
@Data
@ApiModel
public class SysWhitelistVO {


	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty("ip")
    private String ip;


	@ApiModelProperty("更新时间")
    private Date updateTime;

	@ApiModelProperty("后台更新人账号")
    private String updateUser;


}
