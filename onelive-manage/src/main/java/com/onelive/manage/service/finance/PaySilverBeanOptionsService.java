package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsVO;
import com.onelive.common.mybatis.entity.PaySilverBeanOptions;

import java.util.List;

/**
 * <p>
 * 提现快捷选项配置表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
public interface PaySilverBeanOptionsService extends IService<PaySilverBeanOptions> {

    List<PaySilverBeanOptionsVO> listPage(Boolean isEnable);
}
