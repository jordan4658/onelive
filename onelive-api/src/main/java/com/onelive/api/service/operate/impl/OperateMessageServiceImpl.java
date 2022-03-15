package com.onelive.api.service.operate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.operate.OperateMessageService;
import com.onelive.common.mybatis.entity.OperateMessage;
import com.onelive.common.mybatis.mapper.master.operate.OperateMessageMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-17
 */
@Service
public class OperateMessageServiceImpl extends ServiceImpl<OperateMessageMapper, OperateMessage> implements OperateMessageService {

}
