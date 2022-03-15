package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PayShortcutOptionsVO;
import com.onelive.common.mybatis.entity.PayShortcutOptions;
import com.onelive.common.mybatis.mapper.master.pay.PayShortcutOptionsMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayShortcutOptionsMapper;
import com.onelive.pay.service.PayShortcutOptionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public PayShortcutOptions getByPayWayId(Long payWayId) {
        QueryWrapper<PayShortcutOptions> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PayShortcutOptions::getIsDelete, false);
        queryWrapper.lambda().eq(PayShortcutOptions::getIsEnable, true);
        queryWrapper.lambda().eq(PayShortcutOptions::getPayWayId, payWayId);
        return slavePayShortcutOptionsMapper.selectOne(queryWrapper);
    }
}
