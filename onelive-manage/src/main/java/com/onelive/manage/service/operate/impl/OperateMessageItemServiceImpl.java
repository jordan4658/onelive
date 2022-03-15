package com.onelive.manage.service.operate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.OperateMessageItem;
import com.onelive.common.mybatis.mapper.master.operate.OperateMessageItemMapper;
import com.onelive.manage.service.operate.OperateMessageItemService;
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
public class OperateMessageItemServiceImpl extends ServiceImpl<OperateMessageItemMapper, OperateMessageItem> implements OperateMessageItemService {


    @Override
    public void deleteItemList(List<Long> msgIdList, String operateUser) {
        UpdateWrapper<OperateMessageItem> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(OperateMessageItem::getIsDelete, true)
                .set(OperateMessageItem::getUpdateUser, operateUser)
                .set(OperateMessageItem::getUpdateTime,new Date())
                .in(OperateMessageItem::getMsgId, msgIdList);
        this.update(null, wrapper);
    }

    @Override
    public List<OperateMessageItem> getListByMsgId(Long msgId) {
        QueryWrapper<OperateMessageItem> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OperateMessageItem::getMsgId,msgId).eq(OperateMessageItem::getIsDelete,false);
        return this.list(wrapper);
    }
}
