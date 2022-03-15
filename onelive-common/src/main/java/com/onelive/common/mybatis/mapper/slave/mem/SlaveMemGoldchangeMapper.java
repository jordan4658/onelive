package com.onelive.common.mybatis.mapper.slave.mem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.pay.TotalUserIncomeAndExpensesDTO;
import com.onelive.common.model.req.report.UserReportDetailReq;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.report.MemGoldChangeBackVO;
import com.onelive.common.model.vo.report.UserReportDetailVO;
import com.onelive.common.mybatis.entity.MemGoldchange;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员金额变动明细 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
public interface SlaveMemGoldchangeMapper extends BaseMapper<MemGoldchange> {

    List<UserReportDetailVO> queryUserDetail(UserReportDetailReq req);

    BigDecimal queryUserDetailSum(UserReportDetailReq req);

    List<MemGoldchangeVO> rechargeRecord(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("account") String account);

    List<MemGoldChangeBackVO> listPage(@Param("startTime") Date startTime,
                                       @Param("endTime") Date endTime,
                                       @Param("nickName") String nickName,
                                       @Param("account") String account,
                                       @Param("changWalletType") Integer changWalletType,
                                       @Param("changeType") Integer changeType);

    List<TotalUserIncomeAndExpensesDTO> totalUserIncomeAndExpenses(
            @Param("listDate")List<String> listDate,
            @Param("transactionTypes")List<Integer> transactionTypes,
            @Param("userAccount") String userAccount);

	/**
	 * 	查询用户的账变总金额，根据类型
	 * 
	 * @param userAccount
	 * @param type
	 * @return
	 */
	BigDecimal getUserGoldchangeTotalByType(String userAccount, int type);

    List<MemGoldchange> getUserChangeLog(@Param("transactionTypes")List<Integer> transactionTypes,
										 @Param("userAccount")String userAccount,
										 @Param("pageSize")Integer pageSize,
										 @Param("queryDate")Date queryDate,
										 @Param("startDate")Date startDate);
}
