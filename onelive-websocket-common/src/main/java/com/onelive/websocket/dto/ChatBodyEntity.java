package com.onelive.websocket.dto;

import lombok.Data;

/**
 * 消息体
 */
@Data
public class ChatBodyEntity {

    //消息id
    private String id;
    
    /** 聊天类型 1【单聊】  2 【群聊】 3【系统消息】  4 @功能 */
    private Integer chatType;
    
    /** 目标id // 【单聊】 => 用户id 【群聊/聊天室】 => 群id，聊天室id */
    private String targetId;
    
    /** 操作类型  -1-入场公告 0-聊天 1-进入房间 2-踢人  3-禁言 4-关注 5-取关 6-送礼 7-弹幕 8-下播 9-设为管理 10-取消管理 11-礼物连击结束  12-主播收礼金额   20-彩票下注 21-彩票中奖 22-中奖公告*/
    private Integer operatorType;
    
    //发送时间
    private String sendTime;
    
    // 消息内容
    private String content;
    
    /**  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 待定*/
//    // 发送者类型 【游客】 => visitor 【登录用户】 => user 【管理员】 => admin
//    private String senderType;
//    
//	private Long kickingEndTime;// 被踢出结束时间
//
	private Long bannedEndTime;// 禁言结束时间
//
//	// @目标用户信息
//	private MemUser targetUser;
//	
	
	 /**  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 待定*/
}
