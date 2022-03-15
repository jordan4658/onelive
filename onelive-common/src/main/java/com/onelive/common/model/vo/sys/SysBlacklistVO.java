package com.onelive.common.model.vo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户黑名单
 * </p>
 */
@Data
@ApiModel
public class SysBlacklistVO {


	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty("ip")
    private String ip;

	@ApiModelProperty("状态 0-黑名单 1-非黑名单")
    private String ipStatus;
	
	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("更新时间")
    private Date updateTime;

	@ApiModelProperty("后台更新人账号")
    private String updateUser;


}
