package com.onelive.websocket.dto;

import lombok.Data;

/**
 *		单纯聊天的消息体，由前端组装，后台只负责中转
 */
@Data
public class ChatGroupBodyEntity {

	private String content;
	
    private String avatar;

    private Integer level;

    private String levelIcon;

    private String levelName;

    private String nickName;

    private String userId;

    private String tarAvatar;

    private Integer tarLevel;

    private String tarLvelIcon;

    private String tarLevelName;

    private String tarNickName;

    private String tarUserId;

}
