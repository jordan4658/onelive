package com.onelive.websocket.controller;


import com.onelive.websocket.service.SendMsgService;
import com.onelive.websocket.vo.SendMsgVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;


@RequestMapping("/webSocket")
@RestController
public class MessageController {

    @Resource
    private SendMsgService sendMsgService;

    /**
     * 单人消息发送
     *
     * @param msg
     * @return
     */
    @PostMapping("/sendSingleMsg")
    public boolean sendSingleMsg(@RequestBody SendMsgVO msg) {
        return sendMsgService.sendSingleMsg(msg);
    }


    /**
     * 群聊消息发送
     *
     * @param msg
     * @return
     */
    @PostMapping("/sendRoomMsg")
    public boolean sendRoomMsg(@RequestBody SendMsgVO msg) {
        return sendMsgService.sendRoomMsg(msg);
    }


    /**
     * 世界消息发送
     *
     * @param msg
     * @return
     */
    @PostMapping("/sendWordMsg")
    public boolean sendWordMsg(@RequestBody SendMsgVO msg) {
        return sendMsgService.sendWordMsg(msg);
    }

    /**
     * 移除用户信息
     *
     * @param userId
     * @return
     */
    @PostMapping("/removeUser")
    public boolean removeUser(@RequestParam("userId") Long userId) {
        return sendMsgService.removeUser(userId);
    }

    /**
     * 移除房间信息
     *
     * @param studioNum
     * @return
     */
    @PostMapping("/removeRoom")
    public boolean removeRoom(@RequestParam("studioNum") String studioNum) {
        return sendMsgService.removeRoom(studioNum);
    }
    
    /**
     * 	获取房间内的用户id
     *
     * @param studioNum
     * @return
     */
    @PostMapping("/getRoomUsers")
    public Set<Long> getRoomUsers(@RequestParam("studioNum") String studioNum) {
    	return sendMsgService.getRoomUsers(studioNum);
    }
    
    /**
     * 	指定语言房间广播:房间内所有指定语言的用户收到消息
     *
     * @return
     */
    @PostMapping("/roomNoticeLang")
    public boolean roomNoticeLang(@RequestBody SendMsgVO msg) {
    	return sendMsgService.roomNoticeLang(msg);
    }

    
    /**
     * 	指定语言全站广播:全站所有指定语言的用户收到消息
     *
     * @return
     */
    @PostMapping("/worldNoticeLang")
    public boolean worldNoticeLang(@RequestBody SendMsgVO msg) {
    	return sendMsgService.worldNoticeLang(msg);
    }
    

}
