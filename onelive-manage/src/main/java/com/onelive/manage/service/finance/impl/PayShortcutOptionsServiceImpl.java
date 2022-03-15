package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PayShortcutOptionsByIdVO;
import com.onelive.common.model.vo.pay.PayShortcutOptionsVO;
import com.onelive.common.mybatis.entity.PayShortcutOptions;
import com.onelive.common.mybatis.mapper.master.pay.PayShortcutOptionsMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayShortcutOptionsMapper;
import com.onelive.manage.service.finance.PayShortcutOptionsService;
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
    public List<PayShortcutOptionsVO> listPage(Long payWayId, Boolean isEnable,String countryCode) {

        return slavePayShortcutOptionsMapper.listPage(payWayId,isEnable,countryCode);
    }

    @Override
    public List<shortcutOptionsUnitLang> getByLangId(String langId) {
        return slavePayShortcutOptionsMapper.getByLangId(langId);
    }

    @Override
    public PayShortcutOptionsByIdVO selectShortcutOptionsById(Long id) {
        return slavePayShortcutOptionsMapper.selectShortcutOptionsById(id);
    }
}
