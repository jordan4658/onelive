package com.onelive.common.client;

import com.onelive.common.model.vo.webSocket.SendMsgVO;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class WebSocketFeignCallBack implements WebSocketFeignClient {
    @Override
    public boolean sendSingleMsg(SendMsgVO vo) {
        return false;
    }

    @Override
    public boolean sendRoomMsg(SendMsgVO vo) {
        return false;
    }

    @Override
    public boolean sendWordMsg(SendMsgVO msg) {
        return false;
    }

    @Override
    public boolean removeUser(Long userId) {
        return false;
    }

    @Override
    public boolean removeRoom(String studioNum) {
        return false;
    }

	@Override
	public Set<Long> getRoomUsers(String studioNum) {
		return null;
	}

	@Override
	public boolean roomNoticeLang(SendMsgVO msg) {
		return false;
	}

	@Override
	public boolean worldNoticeLang(SendMsgVO msg) {
		return false;
	}
	
}
