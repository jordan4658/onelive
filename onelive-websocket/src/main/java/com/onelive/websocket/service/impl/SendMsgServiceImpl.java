package com.onelive.websocket.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onelive.websocket.dto.ChatEntity;
import com.onelive.websocket.handler.WebSocketHandler;
import com.onelive.websocket.service.SendMsgService;
import com.onelive.websocket.vo.SendMsgVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendMsgServiceImpl implements SendMsgService {

	@Resource
	private WebSocketHandler handler;

	@Override
	public boolean sendRoomMsg(SendMsgVO msg) {
		try {
			return handler.new_roomNotice(
					ChatEntity.getChatEntity(msg.getContent(), msg.getTargetId(), msg.getOperatorType()));
		} catch (Exception e) {
			log.error("发送消息错：" + e.getMessage());
		}

		return false;
	}

	@Override
	public boolean sendWordMsg(SendMsgVO msg) {
		try {
			return handler.new_worldNotice(
					ChatEntity.getChatEntity(msg.getContent(), msg.getTargetId(), msg.getOperatorType()));
		} catch (Exception e) {
			log.error("发送消息错：" + e.getMessage());
		}

		return false;
	}

	@Override
	public boolean sendSingleMsg(SendMsgVO msg) {
		try {
			return handler.new_singleNotice(
					ChatEntity.getChatEntity(msg.getContent(), msg.getTargetId(), msg.getOperatorType()));
		} catch (Exception e) {
			log.error("发送消息错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean removeUser(Long userId) {
		try {
			return handler.new_removeUser(userId);
		} catch (Exception e) {
			log.error("发送消息错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean removeRoom(String studioNum) {
		try {
			return handler.new_removeRoom(studioNum);
		} catch (Exception e) {
			log.error("发送消息错：" + e.getMessage());
		}
		return false;
	}

	@Override
	public Set<Long> getRoomUsers(String studioNum) {
		return handler.getRoomUsers(studioNum);
	}

	@Override
	public boolean roomNoticeLang(SendMsgVO msg) {
		return handler.roomNoticeLang(
				ChatEntity.getChatEntity(msg.getContent(), msg.getTargetId(), msg.getOperatorType()), msg.getLang());
	}

	@Override
	public boolean worldNoticeLang(SendMsgVO msg) {
		return handler.worldNoticeLang(
				ChatEntity.getChatEntity(msg.getContent(), msg.getTargetId(), msg.getOperatorType()), msg.getLang());
	}

}
