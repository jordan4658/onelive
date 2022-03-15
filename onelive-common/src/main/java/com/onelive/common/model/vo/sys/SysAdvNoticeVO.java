package com.onelive.common.model.vo.sys;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 广告公告ListVO
 * </p>
 *
 */
@Data
@ApiModel(value = "公告传输类")
public class SysAdvNoticeVO {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("多个语言对象 公告>多条语言对象  一对多 ")
	private List<SysAdvNoticeLangVo> sysAdvNoticeLangVos;

	@ApiModelProperty("广告设置id")
	private Integer areaId;

	@ApiModelProperty("公告名称 [必填]")
	private String noticeName;

	@ApiModelProperty("展示开始时间[必填]")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startDate;

	@ApiModelProperty("展示结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endDate;

	@ApiModelProperty("循环公告:间隔播放时间")
	private Integer intervalTime;

	@ApiModelProperty("循环公告:用户进入直播后多久开始播放")
	private Integer waitTime;
	
	@ApiModelProperty("循环公告的循环次数,默认1")
	private Integer loopCount;

	@ApiModelProperty("1：直播间公告  2：开场公告（一个语言只有一条） "
			+ "3：循环公告  4：首页公告 5：推荐栏目路马灯公告  6：游戏列表路马灯公告 7:兑换银豆公告")
	private Integer type;

	@ApiModelProperty("状态 0显示1隐藏 [必填]")
	private Integer isHide;

	@ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
	private String useCountry;

}
