package com.onelive.websocket.dto;

import com.onelive.websocket.util.DateUtils;
import com.onelive.websocket.util.UUIDUtils;
import lombok.Data;

/**
 * 		公共传输格式类
 */
@Data
public class ChatEntity {

	/**
	 * <p>  返回码 服务端自己定义 用于错误码，其实一般情况用不到此码  </p>
	 */
	private int code = 200;

	/**  消息体  */
	private ChatBodyEntity body;

	// 发送时间 返回时间戳
	private long sendTime;

	// 客户端返回回执时间，此用于排查异常情况，客户端不会返回，所有时间已服务器时间为准，该时间的生成触发条件为客户端返回回执，已接收成功消息
	private long receiptTime;

	/**
	 * 消息id 服务端生成，当客户端发送一条消息，服务端须将此消息id补齐， 可用于特殊场景，如做留存消息，阶段存储消息，
	 * 该协议层消息id用于做稳定性排查与客户端网络排查时的消息唯一标志
	 */
	private String msgId;

	/** 是否接收成功消息  isReceive = true时，打印日志： userid收到了消息 msgId =  ******， */
	private boolean isReceive;

	/** 公共协议错误内容 */
	private String errorMsg;

	/**
	 *
	 * @param content
	 * @param targetId
	 * @param operatorType  -1-入场公告 0-聊天 1-进入房间 2-踢人  3-禁言 4-关注 5-取关 6-送礼 7-弹幕 8-下播 9-设为管理 10-取消管理
	 * @param chatType 指定操作类型  1【单聊】  2 【群聊】 3【系统消息】  4 @功能
	 * @return
	 */
	public static ChatEntity getChatEntity(String content, String targetId,Integer operatorType, Integer chatType) {
		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setSendTime(System.currentTimeMillis());
		chatEntity.setReceiptTime(System.currentTimeMillis());
		chatEntity.setMsgId(UUIDUtils.getUUID());
		chatEntity.setCode(200);
		
		// ChatBodyEntity body
		ChatBodyEntity chatBodyEntity = new ChatBodyEntity();
		chatBodyEntity.setContent(content);
		chatBodyEntity.setTargetId(targetId);
		chatBodyEntity.setChatType(chatType);
		chatBodyEntity.setSendTime(DateUtils.getTime());
		chatBodyEntity.setOperatorType(operatorType);
		chatEntity.setBody(chatBodyEntity);
		return chatEntity;
	}
	
	/**
	 * 		默认操作类型 = 3
	 * @param content
	 * @param targetId
	 * @param operatorType 操作类型  -1-入场公告 0-聊天 1-进入房间 2-踢人  3-禁言 4-关注 5-取关 6-送礼 7-弹幕 8-下播 9-设为管理 10-取消管理 11-礼物连击结束
	 * @return
	 */
	public static ChatEntity getChatEntity(String content, String targetId,Integer operatorType) {
		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setSendTime(System.currentTimeMillis());
		chatEntity.setReceiptTime(System.currentTimeMillis());
		chatEntity.setMsgId(UUIDUtils.getUUID());
		chatEntity.setCode(200);
		
		// ChatBodyEntity body
		ChatBodyEntity chatBodyEntity = new ChatBodyEntity();
		chatBodyEntity.setContent(content);
		chatBodyEntity.setTargetId(targetId);
		chatBodyEntity.setChatType(3);
		chatBodyEntity.setSendTime(DateUtils.getTime());
		chatBodyEntity.setOperatorType(operatorType);
		chatEntity.setBody(chatBodyEntity);
		return chatEntity;
	}

	public static ChatEntity getErrorChatEntity(String content,Integer errorCode) {
		ChatEntity chatEntity = new ChatEntity();
		chatEntity.setSendTime(System.currentTimeMillis());
		chatEntity.setReceiptTime(System.currentTimeMillis());
		chatEntity.setMsgId(UUIDUtils.getUUID());
		chatEntity.setCode(errorCode);
		chatEntity.setErrorMsg(content);
		return chatEntity;
	}


}
