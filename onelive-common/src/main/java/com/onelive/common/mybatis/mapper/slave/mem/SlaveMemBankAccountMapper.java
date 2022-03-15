package com.onelive.common.mybatis.mapper.slave.mem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.vo.mem.MemBankAccountVO;
import com.onelive.common.mybatis.entity.MemBankAccount;

import java.util.List;

/**
 * <p>
 * 会员银行卡列表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */

public interface SlaveMemBankAccountMapper extends BaseMapper<MemBankAccount> {


    List<MemBankAccountVO> list(String account);


    Integer getBankCountByAccount(String account);

    
	MemBankAccount getFirstBank(String userAccount);
}
