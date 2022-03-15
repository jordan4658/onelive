package com.onelive.api.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUserMessage;

import java.util.List;

/**
 * <p>
 * 用户消息表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-17
 */
public interface MemUserMessageService extends IService<MemUserMessage> {

    void saveAll(List<MemUserMessage> msgList);
}
