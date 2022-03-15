package com.onelive.common.model.vo.webSocket;


import lombok.Data;

@Data
public class SendMsgVO {

    /** 消息内容（json格式） */
    private String content;

    /** 发送的目标ID（直播间ID，用户ID） */
    private String targetId;

    /** 操作类型  -1-入场公告 0-聊天 1-进入房间 2-踢人  3-禁言 4-关注 5-取关 6-送礼 7-弹幕 8-下播 9-设为管理 10-取消管理 11-礼物连击结束  12-主播收礼金额   20-彩票下注 21-彩票中奖 22-中奖公告*/
    private Integer operatorType;
    
    /** 语言标志*/
    private String lang;

}
