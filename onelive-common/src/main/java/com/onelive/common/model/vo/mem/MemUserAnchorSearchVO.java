package com.onelive.common.model.vo.mem;

import java.io.Serializable;
import java.util.List;

import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("主播搜索返回对象")
public class MemUserAnchorSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("主播名片")
	List<MemUserAnchorSearchListVO> anchorList;
	
	@ApiModelProperty("正在直播的直播间")
	List<LiveStudioListForIndexVO> studioList;
	
	
}
