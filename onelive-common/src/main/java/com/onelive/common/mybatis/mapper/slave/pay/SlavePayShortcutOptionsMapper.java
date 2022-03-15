package com.onelive.common.mybatis.mapper.slave.pay;

import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PayShortcutOptionsByIdVO;
import com.onelive.common.model.vo.pay.PayShortcutOptionsVO;
import com.onelive.common.mybatis.entity.PayShortcutOptions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 支付快捷选项表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-04
 */
public interface SlavePayShortcutOptionsMapper extends BaseMapper<PayShortcutOptions> {

    List<PayShortcutOptionsVO> listPage(@Param("payWayId") Long payWayId,
                                        @Param("isEnable") Boolean isEnable,
                                        @Param("countryCode") String countryCode);

    List<shortcutOptionsUnitLang> getByLangId(@Param("langId") String langId);

    PayShortcutOptionsByIdVO selectShortcutOptionsById(@Param("id") Long id);
}
