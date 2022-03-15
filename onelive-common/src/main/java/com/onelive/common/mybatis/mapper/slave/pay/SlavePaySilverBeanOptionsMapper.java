package com.onelive.common.mybatis.mapper.slave.pay;

import com.onelive.common.model.req.pay.shortcutOptionsUnitLang;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsVO;
import com.onelive.common.mybatis.entity.PaySilverBeanOptions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 兑换快捷选项配置表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
public interface SlavePaySilverBeanOptionsMapper extends BaseMapper<PaySilverBeanOptions> {

    List<PaySilverBeanOptionsVO> listPage(@Param("isEnable") Boolean isEnable);

}
