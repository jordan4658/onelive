package com.onelive.manage.service.operate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.vo.operate.MessageListVo;
import com.onelive.common.mybatis.entity.OperateMessage;
import com.onelive.common.mybatis.mapper.master.operate.OperateMessageMapper;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.operate.OperateMessageService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-06
 */
@Service
public class OperateMessageServiceImpl extends ServiceImpl<OperateMessageMapper, OperateMessage> implements OperateMessageService {

    @Override
    public PageInfo<MessageListVo> getMessageList(PageReq req) {
        QueryWrapper<OperateMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperateMessage::getIsDelete,false);
        PageInfo<OperateMessage> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> this.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo,MessageListVo.class);
    }

    @Override
    public void deleteMsg(List<Long> idList, String operateUser) {
        UpdateWrapper<OperateMessage> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(OperateMessage::getIsDelete,true)
                .set(OperateMessage::getUpdateUser,operateUser)
                .set(OperateMessage::getUpdateTime,new Date())
                .in(OperateMessage::getId,idList);
        this.update(wrapper);
    }

}
