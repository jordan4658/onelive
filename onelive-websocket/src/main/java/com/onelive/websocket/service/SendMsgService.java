package com.onelive.websocket.service;


import java.util.Set;

import com.onelive.websocket.vo.SendMsgVO;

public interface SendMsgService {

    boolean sendRoomMsg(SendMsgVO msg);

    boolean sendWordMsg(SendMsgVO msg);

    boolean sendSingleMsg(SendMsgVO msg);

    boolean removeUser(Long userId);

    boolean removeRoom(String studioNum);

	Set<Long> getRoomUsers(String studioNum);

	boolean roomNoticeLang(SendMsgVO msg);

	boolean worldNoticeLang(SendMsgVO msg);
}
