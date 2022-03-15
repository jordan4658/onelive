package com.onelive.job.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.OperateMessage;
import com.onelive.common.mybatis.mapper.master.operate.OperateMessageMapper;
import com.onelive.job.service.mem.OperateMessageService;
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
