package com.onelive.job.service.agent.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.model.dto.agent.AgentInviteRecordDto;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.mybatis.mapper.slave.agent.SlaveAgentInviteRecordMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.job.service.agent.AgentRebatesService;

import groovy.util.logging.Slf4j;

@Slf4j
@Service
public class AgentRebatesServicempl implements AgentRebatesService {

	@Resource
	private AccountBalanceChangeService accountBalanceChangeService;

	@Resource
	private SlaveAgentInviteRecordMapper slaveAgentInviteRecordMapper;

	@Override
	public void agentRebatesDay() {
		// 上一天,下级有投注的上级代理数量
		int agentCoountTotal = slaveAgentInviteRecordMapper.getYesterdayAgentUserBetCount();
		// d一次oneTimeCount条，分updateTimes次插入
		int updateTimes = (agentCoountTotal / 700) + 1;
		for (int i = 1; i < updateTimes + 1; i++) {
			PageHelper.startPage(i, 700);

			List<AgentInviteRecordDto> agentUserYesterdaBetAmount = slaveAgentInviteRecordMapper
					.getAgentUserYesterdaBetAmount();
			for (AgentInviteRecordDto agentInviteRecordDto : agentUserYesterdaBetAmount) {
				// TODO 游戏平台各返点比例不同
				BigDecimal rebatesAmount = agentInviteRecordDto.getBetAmount().multiply(new BigDecimal("0.003"))
						.setScale(4, BigDecimal.ROUND_DOWN);
				// 给每个代理加钱
				MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
				memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
				memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_3.getCode());
				memAccountChangeVO.setPrice(rebatesAmount);
				memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE30.getPayTypeCode());
				memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE30.getMsg());
				accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
				// 非主播用户记录稽核
				if (!agentInviteRecordDto.getIsAuthor()) {

				}

			}
		}

	}

}
