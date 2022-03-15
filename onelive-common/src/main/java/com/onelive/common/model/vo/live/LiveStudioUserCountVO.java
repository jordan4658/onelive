package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播间表排序VO
 */
@Data
@ApiModel
public class LiveStudioUserCountVO {

	@ApiModelProperty("房间号")
	private String studioNum;
	
	@ApiModelProperty("排序分数，如：在线人数/火力值")
	private Integer score;
	
	@ApiModelProperty("直播间的gameid")
	private Long gameId;

	public LiveStudioUserCountVO(String studioNum, Integer score) {
		super();
		this.studioNum = studioNum;
		this.score = score;
	}
	
	public LiveStudioUserCountVO(String studioNum, Integer score, Long gameId) {
		super();
		this.studioNum = studioNum;
		this.score = score;
		this.gameId = gameId;
	}
	
	
}