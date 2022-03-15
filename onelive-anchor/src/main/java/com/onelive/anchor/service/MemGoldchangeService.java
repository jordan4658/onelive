package com.onelive.anchor.service;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemGoldchange;

/**
 * <p>
 * 会员金额变动明细 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
public interface MemGoldchangeService extends IService<MemGoldchange> {

	/**
	 * 	查询用户的账变总金额，根据类型
	 * 
	 * @param userAccount
	 * @param type
	 * @return
	 */
	BigDecimal getUserGoldchangeTotalByType(String userAccount, int type);

}
