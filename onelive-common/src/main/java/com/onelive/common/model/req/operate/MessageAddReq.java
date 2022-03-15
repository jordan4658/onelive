package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 新增消息请求参数
 */
@Data
@ApiModel
public class MessageAddReq {

    @ApiModelProperty("消息ID, 修改时传")
    private Long id;

    @ApiModelProperty(value = "消息类型 1.系统消息 2.邮件[必填]",required = true)
    private Integer msgType;

    @ApiModelProperty(value = "接收类型 1按用户层级 2按用户等级 3按指定地区 4按指定账号[必填]",required = true)
    private Integer receiveType;

    @ApiModelProperty("用户层级列表")
    private List<Integer> userGroupList;

    @ApiModelProperty("用户等级列表")
    private List<Integer> userLevelList;

    @ApiModelProperty("指定地区code列表")
    private List<String> areaList;

    /**
     * 接收者账号(多个用换行分隔)
     */
    @ApiModelProperty("接收者账号(多个用换行分隔)")
    private String receiverAccount;
    /**
     * 图片地址
     */
    @ApiModelProperty("图片")
    private String img;

    /**
     * 跳转链接
     */
    @ApiModelProperty("跳转链接")
    private String skipUrl;


    @ApiModelProperty(value = "多语言消息列表[必填]",required = true)
    private List<MessageItemReq> langList;

}
