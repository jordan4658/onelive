package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
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

    PayShortcutOptions getByPayWayId(Long payWayId);

}
