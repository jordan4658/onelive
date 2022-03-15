package com.onelive.common.model.req.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserAnchorReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主播的userid，主播查询时不传，家族长传入主播的uerId")
	private Long userId;
	
	@ApiModelProperty("主播昵称,主播账号，查询用")
	private String nickName;
	
    @ApiModelProperty(value = "第几页", required = false, example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大数量", required = false, example = "10")
    private Integer pageSize = 10;
    
    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
    
    @ApiModelProperty("收入类型 7-提现（成功） 14-代理佣金 20-礼物收益(主播) 21-被关注奖励(主播) 22-其他（弹幕）")
    private Integer changeType;
    
    private String lang;
    

}
