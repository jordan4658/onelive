package com.onelive.anchor.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.MemGoldchangeService;
import com.onelive.common.mybatis.entity.MemGoldchange;
import com.onelive.common.mybatis.mapper.master.mem.MemGoldchangeMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemGoldchangeMapper;


/**
 * <p>
 * 		会员金额变动明细 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
@Service
public class MemGoldchangeServiceImpl extends ServiceImpl<MemGoldchangeMapper, MemGoldchange> implements MemGoldchangeService {

    @Resource
    private SlaveMemGoldchangeMapper slaveMemGoldchangeMapper;

	@Override
	public BigDecimal getUserGoldchangeTotalByType(String userAccount, int type) {
		return slaveMemGoldchangeMapper.getUserGoldchangeTotalByType(userAccount, type);
	}


}
