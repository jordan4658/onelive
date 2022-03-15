package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.pay.PayFindExchangeCfgVO;
import com.onelive.common.mybatis.entity.PayFindExchangeCfg;
import com.onelive.common.mybatis.mapper.master.pay.PayFindExchangeCfgMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayFindExchangeCfgMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.finance.PayFindExchangeCfgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 汇率查询key配置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Service
public class PayFindExchangeCfgServiceImpl extends ServiceImpl<PayFindExchangeCfgMapper, PayFindExchangeCfg> implements PayFindExchangeCfgService {

    @Resource
    private SlavePayFindExchangeCfgMapper slavePayFindExchangeCfgMapper;

    @Override
    public PageInfo<PayFindExchangeCfgVO> pageList(Integer pageSize, Integer pageNum, String exchangeKey, String exchangeDataSourceCode) {
        QueryWrapper<PayFindExchangeCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayFindExchangeCfg::getIsDelete, false);
        if (StringUtils.isNotEmpty(exchangeKey)) {
            queryWrapper.lambda().like(PayFindExchangeCfg::getExchangeKey, exchangeKey);
        }
        if (StringUtils.isNotEmpty(exchangeDataSourceCode)) {
            queryWrapper.lambda().eq(PayFindExchangeCfg::getExchangeUrlCode, exchangeDataSourceCode);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PayFindExchangeCfg> list = slavePayFindExchangeCfgMapper.selectList(queryWrapper);
        return new PageInfo<>(BeanCopyUtil.copyCollection(list, PayFindExchangeCfgVO.class));
    }
}
