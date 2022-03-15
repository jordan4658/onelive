package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PayShortcutOptionsByIdVO;
import com.onelive.common.model.vo.pay.PayShortcutOptionsVO;
import com.onelive.common.mybatis.entity.PayShortcutOptions;

import java.util.List;

/**
 * <p>
 * 支付快捷选项表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-04
 */
public interface PayShortcutOptionsService extends IService<PayShortcutOptions> {

    List<PayShortcutOptionsVO> listPage(Long payWayId, Boolean isEnable,String countryCode);

    List<shortcutOptionsUnitLang> getByLangId(String langId);

    PayShortcutOptionsByIdVO selectShortcutOptionsById(Long id);
}
