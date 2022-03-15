package com.onelive.manage.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.mem.MemGroupCurrencyCfgVO;
import com.onelive.common.mybatis.entity.MemGroupCurrencyCfg;

import java.util.List;

/**
 * <p>
 * 用户层级 出入款配置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
public interface MemGroupCurrencyCfgService extends IService<MemGroupCurrencyCfg> {

    List<MemGroupCurrencyCfgVO> getByUserGroupIds(List<Long> groupIds);
}
