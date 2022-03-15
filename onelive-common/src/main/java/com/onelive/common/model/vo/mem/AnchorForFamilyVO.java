package com.onelive.common.model.vo.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AnchorForFamilyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("主播的userid")
	private Long userId;
	
	@ApiModelProperty("用户登录账号/主播账号")
	private String userAccount;

	@ApiModelProperty("主播昵称")
	private String nickName;
	
	@ApiModelProperty("主播头像")
	private String avatar;
	
	@ApiModelProperty("被关注后可以奖励钱,默认关闭")
    private Boolean isFocusAward;

	@ApiModelProperty("是否在线 0否1是")
	private Boolean isOnline;
	
	@ApiModelProperty("是否冻结")
    private Boolean isFrozen;

//	@ApiModelProperty("关联用户表自增id")
//	private Long userId;

	@ApiModelProperty("直播时长(每次直播结束时统计)")
	private Integer liveTime;

//	@ApiModelProperty("直播次数/登录次数")
//	private Integer liveCount;
}
