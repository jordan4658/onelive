package com.onelive.common.mybatis.mapper.slave.mem;

import com.onelive.common.model.vo.mem.FamilyFinancialRecord;
import com.onelive.common.mybatis.entity.MemFamilyWithdrawalLog;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-24
 */
public interface SlaveMemFamilyWithdrawalLogMapper extends BaseMapper<MemFamilyWithdrawalLog> {

	/**
	 * 家族长的财务记录， 收入支出
	 * 
	 * @param userAccount
	 * @param familyId
	 * @return
	 */
	List<FamilyFinancialRecord> financialRecord(String userAccount, Long familyId, Integer type);

}
