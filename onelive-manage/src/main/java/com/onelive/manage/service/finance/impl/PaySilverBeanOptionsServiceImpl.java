package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsVO;
import com.onelive.common.mybatis.entity.PaySilverBeanOptions;
import com.onelive.common.mybatis.mapper.master.pay.PaySilverBeanOptionsMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePaySilverBeanOptionsMapper;
import com.onelive.manage.service.finance.PaySilverBeanOptionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 兑换快捷选项配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
@Service
public class PaySilverBeanOptionsServiceImpl extends ServiceImpl<PaySilverBeanOptionsMapper, PaySilverBeanOptions> implements PaySilverBeanOptionsService {


    @Resource
    private SlavePaySilverBeanOptionsMapper slavePaySilverBeanOptionsMapper;

    @Override
    public List<PaySilverBeanOptionsVO> listPage(Boolean isEnable) {
        return slavePaySilverBeanOptionsMapper.listPage(isEnable);
    }

}
