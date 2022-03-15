package com.onelive.manage.service.sys;

import com.onelive.common.mybatis.entity.SysRechargeHelp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 充值说明表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface SysRechargeHelpService extends IService<SysRechargeHelp> {


	public void updateStatus(Long id, String account);
}
