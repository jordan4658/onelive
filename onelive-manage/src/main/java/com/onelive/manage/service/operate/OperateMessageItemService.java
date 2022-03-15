package com.onelive.manage.service.operate;

import com.onelive.common.mybatis.entity.OperateMessageItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-06
 */
public interface OperateMessageItemService extends IService<OperateMessageItem> {

    void deleteItemList(List<Long> msgIdList, String operateUser);

    List<OperateMessageItem> getListByMsgId(Long msgId);
}
