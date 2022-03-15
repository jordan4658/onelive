package com.onelive.common.model.vo.mem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FocusUserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户登录账号")
	private String userAccount;

	@ApiModelProperty("用户昵称")
	private String nickName;

	@ApiModelProperty("主播账号")
	private String userAccountAnchor;

	@ApiModelProperty("主播昵称")
	private String nickNameAnchor;

	@ApiModelProperty("所属地区id")
	private Long countryId;

	@ApiModelProperty("用户关注主播后给主播奖励的钱")
	private BigDecimal award;
	
	@ApiModelProperty("是否关注")
	private Boolean isFocus;

	@ApiModelProperty("创建时间/关注时间")
	private Date createTime;

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

}
