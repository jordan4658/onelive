package com.onelive.common.client;

import com.onelive.common.constants.other.ServerConstants;
import com.onelive.common.model.vo.webSocket.SendMsgVO;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServerConstants.ONELIVE_WEBSCOKET, fallback = WebSocketFeignCallBack.class)
public interface WebSocketFeignClient {

    String WEBSOCKET_PREFIX = "/webSocket";

    /**
     * 群聊消息发送
     *
     * @return
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/sendSingleMsg")
    boolean sendSingleMsg(@RequestBody SendMsgVO vo);

    /**
     * 群聊消息发送
     *
     * @return
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/sendRoomMsg")
    boolean sendRoomMsg(@RequestBody SendMsgVO vo);


    /**
     * 世界消息发送
     *
     * @return
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/sendWordMsg")
    boolean sendWordMsg(@RequestBody SendMsgVO msg);

    /**
     * 移除用户信息
     *
     * @param userId
     * @return
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/removeUser")
    boolean removeUser(@RequestParam("userId")  Long userId);

    /**
     * 移除房间信息
     *
     * @param studioNum
     * @return
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/removeRoom")
    boolean removeRoom(@RequestParam("studioNum") String studioNum);
    
    /**
     * 	获取房间内的用户id
     *
     * @param studioNum
     * @return
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/getRoomUsers")
    Set<Long> getRoomUsers(@RequestParam("studioNum") String studioNum);
    
    /**
     * 	 指定语言房间广播:房间内所有指定语言的用户收到消息
     * @param msg
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/roomNoticeLang")
    boolean roomNoticeLang(@RequestBody SendMsgVO msg);

    /**
     * 	 指定语言房间广播:房间内所有指定语言的用户收到消息
     * @param msg
     */
    @PostMapping(value = WEBSOCKET_PREFIX + "/worldNoticeLang")
    boolean worldNoticeLang(@RequestBody SendMsgVO msg);

}
