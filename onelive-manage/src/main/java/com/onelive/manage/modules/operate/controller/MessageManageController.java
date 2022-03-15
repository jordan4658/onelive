package com.onelive.manage.modules.operate.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdListReq;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.operate.MessageAddReq;
import com.onelive.common.model.vo.operate.MessageListVo;
import com.onelive.common.model.vo.operate.MessageVo;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.operate.business.MessageManageBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 后台消息管理
 */
@RestController
@RequestMapping(value = "/operate")
@Api(tags = "运营管理-消息管理")
@Slf4j
public class MessageManageController extends BaseAdminController {
    @Resource
    private MessageManageBusiness business;


    @PostMapping("/v1/getMessageList")
    @ApiOperation("查询消息列表")
    public ResultInfo<PageInfo<MessageListVo>> getMessageList(@RequestBody PageReq req) {
        return business.getMessageList(req);
    }

    @PostMapping("/v1/getMessage")
    @ApiOperation("查询消息内容")
    public ResultInfo<MessageVo> getMessage(@RequestBody LongIdReq req) {
        return business.getMessage(req);
    }

    @Log("新增消息")
    @PostMapping("/v1/saveMessage")
    @ApiOperation("新增消息")
    public ResultInfo<Boolean> saveMessage(@RequestBody MessageAddReq req) {
        LoginUser admin = getLoginAdmin();
        return business.saveMessage(req,admin);
    }

    @Log("删除消息")
    @PostMapping("/v1/deleteMsg")
    @ApiOperation("(批量)删除消息")
    public ResultInfo<Boolean> deleteMsg(@RequestBody LongIdListReq req) {
        LoginUser admin = getLoginAdmin();
        return business.deleteMsg(req, admin);
    }


}
