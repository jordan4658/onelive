package com.onelive.common.mybatis.mapper.master.mem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.MemBankAccount;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员银行卡列表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */

public interface MemBankAccountMapper extends BaseMapper<MemBankAccount> {

    void deleteByBankAccidAndAccount(@Param("bankAccid") Long bankAccid,@Param("account") String account,@Param("isDetele") Boolean isDelete);
}
