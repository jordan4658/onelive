package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.pay.PayFindExchangeCfgVO;
import com.onelive.common.mybatis.entity.PayFindExchangeCfg;

/**
 * <p>
 * 汇率查询key配置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
public interface PayFindExchangeCfgService extends IService<PayFindExchangeCfg> {

    PageInfo<PayFindExchangeCfgVO> pageList(Integer pageSize, Integer pageNum, String exchangeKey, String exchangeDataSourceCode);
}
