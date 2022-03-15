package com.onelive.common.mybatis.mapper.slave.mem;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.mem.AnchorAssets;
import com.onelive.common.model.dto.report.AnchorReportDto;
import com.onelive.common.model.req.mem.UserAnchorReq;
import com.onelive.common.model.req.mem.WithdrawAnchorVo;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorListReq;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.model.vo.live.LiveAnchorVO;
import com.onelive.common.model.vo.live.LiveLogForApiVO;
import com.onelive.common.model.vo.mem.AnchorForFamilyVO;
import com.onelive.common.model.vo.mem.AnchorIncomeDetailsVO;
import com.onelive.common.model.vo.mem.MemUserAnchorVO;
import com.onelive.common.mybatis.entity.MemUserAnchor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author admin
 *
 */
public interface SlaveMemUserAnchorMapper extends BaseMapper<MemUserAnchor> {

	/**
	 * 	后台列表查询
	 * 
	 * @param req
	 * @return
	 */
	List<MemUserAnchorVO> getList(MemUserAnchorListReq req);

	LiveAnchorDetailVO selectLiveAnchorDetail(Long userId);

	List<AnchorForFamilyVO> getListForFamily(Long familyId, String nickName);

	MemUserAnchor getFocusAward(Long userId);

	/**
	 * 
	 * @param anchorReportDto
	 * @return
	 */
	List<AnchorReportDto> getReportList(AnchorReportDto anchorReportDto);

	/**
	 * 查询主播的收入记录
	 * 
	 * @param userAnchorReq
	 * @return
	 */
	List<AnchorIncomeDetailsVO> anchorIncomeDetails(UserAnchorReq userAnchorReq);

	/**
	 * 	查询主播的资产，包括今日收入，当月收入
	 * 				包括 礼物，弹幕，关注，代理的收入
	 * 					-- 主播的收入除了礼物，都是实时到账
	 *					-- 查询金币流水表， + 礼物记录表 相加		
	 *		今日收入，当月收入：礼物 + 资金流水表 mem_goldchange 当天，当月  amount的流水
	 *		总资产 ：钱包余额（因为 弹幕，关注，代理直接到钱包【且礼物可能已经被提现，不能算在总资产】） + 主播表未提现的礼物金额 * 分成比例 /100
	 * @param userId
	 * @return
	 */
	AnchorAssets anchorAssets(Long userId);

	/**
	 * 	查询家族长的资产名下主播的综合，包括今日收入，当月收入
	 * @param userId
	 * @return
	 */
	AnchorAssets familyAssets(Long userId);

	/**
	 * 	根据用户id查询主播的当前直播时间
	 * 
	 * @param userId
	 * @return
	 */
	LiveLogForApiVO getTodayLiveTime(Long userId);

	/**
	 * 	根据直播间num查询主播的简单信息
	 * 
	 * @param studioNum
	 * @return
	 */
	LiveAnchorVO getAnchorInfoByStudioNum(String studioNum);

	/**
	 * 	根据直播间num查询主播的详细信息
	 * 
	 * @param studioNum
	 * @return
	 */
	LiveAnchorDetailVO getAnchorInfoDetailByStudioNum(String studioNum);

	/**
	 * 	主播指定月份的收入，支出
	 * 
	 * @param userId
	 * @param yearMonth
	 * @param changeType 
	 * @return
	 */
	BigDecimal getMonthIncomeByChangeType(Long userId, String yearMonth, Integer changeType);

	/**
	 * 	指定月份的支出（提现）
	 * @param userId
	 * @param yearMonth
	 * @return
	 */
	BigDecimal getMonthExpend(Long userId, String yearMonth);

	/**
	 * 	更新主播的关注收益金额
	 * 
	 * @param userId
	 * @param focusAward
	 * @return
	 */
	int updateAnchorFocuseTotal(Long userId, BigDecimal focusAward);

	/**
	 * 	查询名下主播的可提现列表
	 * 
	 * @param id
	 * @return
	 */
	List<WithdrawAnchorVo> canWithdrawAnchor(Long familyId);

	/**
	 * 	根据userID查询主播信息，礼物分成优先获取家族配置
	 * 
	 * @param userId
	 * @return
	 */
	MemUserAnchor getInfoByUserId(Long userId);
	
	
}
