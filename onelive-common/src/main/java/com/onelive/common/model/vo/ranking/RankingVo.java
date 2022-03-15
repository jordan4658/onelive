package com.onelive.common.model.vo.ranking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询贡献值排行榜列表实体类
 */
@Data
@ApiModel(value = "用户排行榜传输类")
public class RankingVo {

	@ApiModelProperty("排名")
	private Integer position;

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("用户名称")
	private String nickName;

	@ApiModelProperty("跟前一名的差距")
	private Integer distance;

	@ApiModelProperty("用户性别")
	private Integer sex;

	@ApiModelProperty("用户等级")
	private Integer userLevel;
	
	@ApiModelProperty("主播粉丝数")
	private Integer fansCount;

	@ApiModelProperty("用户头像")
	private String avatar;

	@ApiModelProperty("用户打赏的金币 * 10 火力值（或主播收到的打赏金币数量* 10 火力值）")
	private Integer firepower;
	
	@ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

//	@ApiModelProperty("用户被踢时间")
//	private Long kickingTime;
}
