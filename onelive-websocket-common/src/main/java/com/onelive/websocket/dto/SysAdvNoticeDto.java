package com.onelive.websocket.dto;

import lombok.Data;

/**
 * <p>
 *		公告传输类
 * </p>
 *
 */
@Data
public class SysAdvNoticeDto {

//	@ApiModelProperty("公告名称")
	private String noticeName;

//	@ApiModelProperty("公告内容")
	private String noticeContent;

//	@ApiModelProperty("0：一般公告 1：循环公告 2：用户进场公告（一个语言只有一条） 3：新版本公告 4：直播列表推荐栏目公告")
	private Integer type;

}
