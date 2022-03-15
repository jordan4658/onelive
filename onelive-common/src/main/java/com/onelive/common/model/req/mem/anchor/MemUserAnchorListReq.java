package com.onelive.common.model.req.mem.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("主播信息列表请求参数")
public class MemUserAnchorListReq implements Serializable {

	private static final long serialVersionUID = 1L;


	@ApiModelProperty("所属地区code")
	private String countryCode;

	@ApiModelProperty("用户登录账号/主播账号")
	private String userAccount;

	@ApiModelProperty("商户code值，默认值为0")
	private String merchantCode;

	@ApiModelProperty("主播昵称")
	private String nickName;

	@ApiModelProperty("查询类型1:总资产 2:下级会员 3:登录次数")
	private Integer queryType;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("家族id")
	private Long familyId;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;


}
