package com.onelive.job.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.MemUserMessage;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMessageMapper;
import com.onelive.job.service.mem.MemUserMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-17
 */
@Service
public class MemUserMessageServiceImpl extends ServiceImpl<MemUserMessageMapper, MemUserMessage> implements MemUserMessageService {

    @Resource
    private MemUserMessageMapper memUserMessageMapper;

    @Override
    public void saveAll(List<MemUserMessage> msgList) {
        memUserMessageMapper.saveAll(msgList);
    }
}
