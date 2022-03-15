package com.onelive.manage.service.operate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.vo.operate.MessageListVo;
import com.onelive.common.mybatis.entity.OperateMessage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-06
 */
public interface OperateMessageService extends IService<OperateMessage> {

    PageInfo<MessageListVo> getMessageList(PageReq req);

    void deleteMsg(List<Long> idList, String operateUser);
}
