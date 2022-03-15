package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.PayShortcutOptionsService;
import com.onelive.common.mybatis.entity.PayShortcutOptions;
import com.onelive.common.mybatis.mapper.master.pay.PayShortcutOptionsMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayShortcutOptionsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 支付快捷选项表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-04
 */
@Service
public class PayShortcutOptionsServiceImpl extends ServiceImpl<PayShortcutOptionsMapper, PayShortcutOptions> implements PayShortcutOptionsService {

    @Resource
    private SlavePayShortcutOptionsMapper slavePayShortcutOptionsMapper;

}
