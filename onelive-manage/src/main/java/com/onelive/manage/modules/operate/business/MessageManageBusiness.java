package com.onelive.manage.modules.operate.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdListReq;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.operate.MessageAddReq;
import com.onelive.common.model.req.operate.MessageItemReq;
import com.onelive.common.model.vo.operate.MessageItemVo;
import com.onelive.common.model.vo.operate.MessageListVo;
import com.onelive.common.model.vo.operate.MessageVo;
import com.onelive.common.mybatis.entity.OperateMessage;
import com.onelive.common.mybatis.entity.OperateMessageItem;
import com.onelive.common.mybatis.util.MysqlMethod;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.operate.OperateMessageItemService;
import com.onelive.manage.service.operate.OperateMessageService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息管理业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageManageBusiness {

    @Resource
    private OperateMessageService operateMessageService;
    @Resource
    private OperateMessageItemService operateMessageItemService;

    /**
     * 查询消息列表
     *
     * @param req
     * @return
     */
    public ResultInfo<PageInfo<MessageListVo>> getMessageList(PageReq req) {
        PageInfo<MessageListVo> list = operateMessageService.getMessageList(req);
        return ResultInfo.ok(list);
    }

    /**
     * 新增消息
     *
     * @param req
     * @param loginUser
     * @return
     */
    @Transactional
    public ResultInfo<Boolean> saveMessage(MessageAddReq req, LoginUser loginUser) {

        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        //根据类型，取出数据
        Integer receiveType = req.getReceiveType();

        StringBuilder sb = new StringBuilder();
        switch (receiveType){
            case 1://用户层级
                List<Integer> userGroupList = req.getUserGroupList();
                if(userGroupList==null || userGroupList.size()==0){
                    throw new BusinessException("请选择用户层级");
                }
                for(Integer groupType:userGroupList){
                    sb.append(groupType).append(",");
                }
                sb.setLength(sb.length()-1);
                break;
            case 2://用户等级
                List<Integer> userLevelList = req.getUserLevelList();
                if(userLevelList==null || userLevelList.size()==0){
                    throw new BusinessException("请选择用户等级");
                }
                for(Integer levelWeight:userLevelList){
                    sb.append(levelWeight).append(",");
                }
                sb.setLength(sb.length()-1);
                break;
            case 3://按指定地区
                List<String> areaList = req.getAreaList();
                if(areaList==null || areaList.size()==0){
                    throw new BusinessException("请选择地区");
                }
                for(String areaCode:areaList){
                    sb.append(areaCode).append(",");
                }
                sb.setLength(sb.length()-1);
                break;
            case 4://用户账号
                String receiverAccount = req.getReceiverAccount();
                if(StrUtil.isBlank(receiverAccount)){
                    throw new BusinessException("请输入用户账号");
                }
                String[] accountList = receiverAccount.split(System.lineSeparator());
                for(String account:accountList){
                    if(StrUtil.isNotBlank(account)) {
                        sb.append(account).append(",");
                    }
                }
                sb.setLength(sb.length()-1);
                break;
        }


        //取出Item列表
        List<MessageItemReq> langList = req.getLangList();

        if (langList == null || langList.size() == 0) {
            throw new BusinessException("消息列表不能为空");
        }

        String title = "";

        for (MessageItemReq item : langList) {
            if (LangConstants.LANG_CN.equals(item.getLang())) {
                title = item.getTitle();
                break;
            }
        }

        if (StrUtil.isBlank(title)) {
            throw new BusinessException(StatusCode.LANG_ZH_NULL_ERROR);
        }

        String operateUser = loginUser.getAccLogin();

        OperateMessage msg = new OperateMessage();

        BeanUtil.copyProperties(req, msg);
        msg.setTitle(title);
        msg.setReceiver(sb.toString());
        //判断是新增还是更新
        List<OperateMessageItem> itemAddList = new ArrayList<>();
        List<OperateMessageItem> itemUpdateList = new ArrayList<>();
        Long msgId = msg.getId();
        msg.setUpdateTime(new Date());
        msg.setUpdateUser(operateUser);
        if (msgId == null) {
            msg.setCreateUser(operateUser);
            msg.setUpdateUser(operateUser);
            msg.setCreateTime(new Date());
            operateMessageService.save(msg);
            msgId = msg.getId();
            for (MessageItemReq item : langList) {
                OperateMessageItem msgItem = new OperateMessageItem();
                BeanUtil.copyProperties(item, msgItem);
                msgItem.setMsgId(msgId);
                msgItem.setCreateUser(operateUser);
                itemAddList.add(msgItem);
            }
        } else {
            //先查询， 再更新
            OperateMessage dbMsg = operateMessageService.getById(msgId);
            if(dbMsg==null){
                throw new BusinessException("该消息不存在");
            }
            operateMessageService.updateById(msg);
            for (MessageItemReq item : langList) {
                OperateMessageItem msgItem = new OperateMessageItem();
                BeanUtil.copyProperties(item, msgItem);
                msgItem.setMsgId(msgId);
                if(item.getId()==null){
                    //需要新增的内容
                    msgItem.setCreateUser(operateUser);
                    itemAddList.add(msgItem);
                }else{
                    //需要更新的内容
                    msgItem.setUpdateUser(operateUser);
                    itemUpdateList.add(msgItem);
                }
            }
        }

        if(itemAddList.size()>0){
            MysqlMethod.batchInsert(OperateMessageItem.class,itemAddList);
        }
        if(itemUpdateList.size()>0){
            MysqlMethod.batchUpdate(OperateMessageItem.class,itemUpdateList);
        }

        return ResultInfo.ok(Boolean.TRUE);
    }


    /**
     * 删除消息
     *
     * @return
     */
    @Transactional
    public ResultInfo<Boolean> deleteMsg(LongIdListReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if(req==null || req.getIds()==null || req.getIds().size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String operateUser = loginUser.getAccLogin();
        List<Long> ids = req.getIds();
        operateMessageService.deleteMsg(ids, operateUser);
        operateMessageItemService.deleteItemList(ids, operateUser);
        return ResultInfo.ok(Boolean.TRUE);
    }

    /**
     * 查询消息内容
     *
     * @return
     */
    public ResultInfo<MessageVo> getMessage(LongIdReq req) {
        if(req==null || req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        Long id = req.getId();
        OperateMessage msg = operateMessageService.getById(id);
        if (msg == null) {
            throw new BusinessException(StatusCode.SYS_DATA_NOT_FOUND);
        }
        if(msg.getIsDelete()){
            throw new BusinessException(StatusCode.SYS_DATA_IS_DEL);
        }
        MessageVo vo = new MessageVo();
        BeanCopyUtil.copyProperties(msg, vo);
        List<OperateMessageItem> list = operateMessageItemService.getListByMsgId(id);
        List<MessageItemVo> voList = BeanCopyUtil.copyCollection(list, MessageItemVo.class);
        vo.setLangList(voList);
        return ResultInfo.ok(vo);
    }
}
