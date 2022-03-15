package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播列表查询")
public class LiveListReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("此房间号后面的直播间，可以为空")
	private String studioNum;

	@ApiModelProperty("每页的条数")
	private Integer pageSize = 10;

}
