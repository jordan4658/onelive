package com.onelive.anchor.modules.account.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.onelive.anchor.service.AgentInviteRecordService;
import com.onelive.anchor.service.LiveStudioLogService;
import com.onelive.anchor.service.MemFamilyService;
import com.onelive.anchor.service.MemGoldchangeService;
import com.onelive.anchor.service.MemLoginRecordService;
import com.onelive.anchor.service.MemUserAnchorService;
import com.onelive.anchor.service.MemUserService;
import com.onelive.anchor.service.SysCountryService;
import com.onelive.anchor.service.SysParameterService;
import com.onelive.anchor.util.AnchorBusinessRedisUtils;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.mem.AnchorAssets;
import com.onelive.common.model.dto.platformConfig.CustomerSericeLangDto;
import com.onelive.common.model.dto.platformConfig.NickNameFiterDto;
import com.onelive.common.model.req.family.FamilyLoginReq;
import com.onelive.common.model.req.login.ResetAnchorPasswordReq;
import com.onelive.common.model.req.mem.AnchorAvatarReq;
import com.onelive.common.model.req.mem.AnchorIncomeMonthReq;
import com.onelive.common.model.req.mem.AnchorNameReq;
import com.onelive.common.model.req.mem.MemUserIdReq;
import com.onelive.common.model.req.mem.UserAnchorReq;
import com.onelive.common.model.vo.agent.AgentInviteForIndexVo;
import com.onelive.common.model.vo.live.LiveHistoryVo;
import com.onelive.common.model.vo.live.LiveLogForApiVO;
import com.onelive.common.model.vo.login.AnchorLoginTokenVo;
import com.onelive.common.model.vo.login.AppLoginTokenVo;
import com.onelive.common.model.vo.mem.AnchorIncomeDetailsVO;
import com.onelive.common.model.vo.mem.AnchorIncomeMonthVO;
import com.onelive.common.model.vo.mem.MemAnchorInfoVO;
import com.onelive.common.mybatis.entity.MemFamily;
import com.onelive.common.mybatis.entity.MemLoginRecord;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.upload.AWSS3Util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ????????????
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountBusiness {

	@Resource
	private MemUserService memUserService;

	@Resource
	private MemFamilyService memFamilyService;

	@Resource
	private SysCountryService sysCountryService;

	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	@Resource
	private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;

	@Resource
	private MemLoginRecordService memLoginRecordService;

	@Resource
	private MemUserAnchorService memUserAnchorService;
	
	@Resource
	private SysParameterService sysParameterService;
	
    @Resource
    private LiveStudioLogService liveStudioLogService;
    
    @Resource
    private MemGoldchangeService memGoldchangeService;
    
    @Resource
    private AgentInviteRecordService agentInviteRecordService;

	private final static String tokenPre = "onelive";

	/**
	 * ??????/???????????????
	 * 
	 * @param req
	 * @return
	 */
	public ResultInfo<AnchorLoginTokenVo> login(FamilyLoginReq req) {
		String merchantCode = req.getMerchantCode();
		String password = req.getPassword();
		String userAccount = req.getUserAccount();
		// ????????????
		if (StringUtils.isBlank(merchantCode) || StringUtils.isBlank(password) || StringUtils.isBlank(userAccount)) {
			return ResultInfo.getInstance(StatusCode.LOGIN_PARAM_ERROR);
		}
		Boolean isFamily = false;
		// ????????????????????????
		// ????????????
		 MemUser memUser = memUserService.queryByAccount(userAccount, merchantCode, 2);
		if (memUser == null) {
			// ??????????????????????????????
			memUser = memUserService.queryByAccount(userAccount, merchantCode, 3);
			if (memUser == null) {
				return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
			}
			isFamily = true;
			log.info("?????????{} ????????????", memUser.getNickName());
		} else {
			log.info("??????{} ????????????", memUser.getNickName());
		}

		// ????????????
		password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
		if (!password.equals(memUser.getPassword())) {
			// ?????????????????????????????????????????????1???????????????,5????????????TODO ????????????????????????
			Integer loginNum = AnchorBusinessRedisUtils.getLoginProtect(memUser.getAccno());
			if (loginNum >= 5) {
				return ResultInfo.getInstance(StatusCode.LOGIN_LOCK_PASSWORD);
			} else {
				AnchorBusinessRedisUtils.setLoginProtect(memUser.getAccno(), loginNum + 1);
				return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
			}
		}
		// ??????????????????
		if (memUser.getIsFrozen()) {
			return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
		}
		// ???????????? TODO,???????????????????????????
		
		
		// ?????????????????????????????????
		memUser.setLastLoginIp(LoginInfoUtil.getIp());
		// memUser.setLastLoginArea(IPAddressUtil.getClientArea(memBaseInfo.getLastLoginIp()));
		memUser.setLastLoginTime(DateUtil.date());
		memUser.setLastLoginSource(LoginInfoUtil.getSource());
		String deviceType = LoginInfoUtil.getDeviceType();
		if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
			deviceType = deviceType.substring(0, 100);
		}
		memUser.setLastLoginDevice(deviceType);
		memUserService.updateById(memUser);

		// app????????????????????????,?????????????????????
		AnchorBusinessRedisUtils.deleteAllToken(memUser);
		// ??????token?????????????????????
		SysCountry country = sysCountryService.getById(memUser.getCountryId());

		String countryCode = null;
		if (country != null) {
			countryCode = country.getCountryCode();
		}
		AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, false);
		AnchorLoginTokenVo anchorLoginTokenVo = new AnchorLoginTokenVo();
		anchorLoginTokenVo.setIsFamily(isFamily);
		anchorLoginTokenVo.setAcctoken(loginVO.getAcctoken());
		anchorLoginTokenVo.setRegisterCountryCode(loginVO.getRegisterCountryCode());
		
		return ResultInfo.ok(anchorLoginTokenVo);
	}

	/**
	 * ??????????????????
	 *
	 * @param memUser
	 * @param isRegister ??????????????????
	 * @return
	 */
	private AppLoginTokenVo dealLogin(MemUser memUser, String countryCode, Boolean isRegister) {
		// ????????????????????????
		this.saveLogs(memUser);
		// ??????????????????token
		String acctoken = this.getToken(memUser, countryCode);
		AppLoginTokenVo loginVO = new AppLoginTokenVo();
		loginVO.setAcctoken(acctoken);
		loginVO.setRegisterCountryCode(countryCode);
		return loginVO;
	}

	/**
	 * ???????????????????????????
	 *
	 * @param memUser
	 */
	private void saveLogs(MemUser memUser) {
		MemLoginRecord record = new MemLoginRecord();
		record.setAccount(memUser.getAccno());
		record.setIp(memUser.getLastLoginIp());
		record.setLoginSource(memUser.getLastLoginSource());
		record.setArea(memUser.getLastLoginArea());
		record.setLoginDevice(LoginInfoUtil.getDeviceType());
		memLoginRecordService.save(record);
	}

	/**
	 * ????????????token
	 *
	 * @param memUser
	 * @return
	 */
	private String getToken(MemUser memUser, String countryCode) {
		// 1???redis??????????????????
		AppLoginUser user = new AppLoginUser();
		user.setId(memUser.getId());
		user.setAccno(memUser.getAccno());
		user.setMobilePhone(memUser.getMobilePhone());
		user.setAreaCode(memUser.getRegisterAreaCode());
		user.setUserAccount(memUser.getUserAccount());
		user.setMerchantCode(memUser.getMerchantCode());
		user.setRegisterCountryCode(memUser.getRegisterCountryCode());
		user.setCountryCode(countryCode);

		user.setNickName(memUser.getNickName());
		user.setPersonalSignature(memUser.getPersonalSignature());
		user.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
		user.setLevel(memUser.getUserLevel());

		String jsonStr = JSON.toJSONString(user);
		String acctoken = DigestUtil.md5Hex(tokenPre + IdUtil.fastSimpleUUID());
		// ????????????token
		AnchorBusinessRedisUtils.setAccessToken(memUser, acctoken, jsonStr);
		// ??????????????????
		AnchorBusinessRedisUtils.setUserStatus(memUser);

		// ????????????token
		return acctoken;
	}

	/**
	 * 	??????/???????????????????????????
	 * 
	 * @param req
	 * @return
	 */
	public ResultInfo<String> resetPassword(ResetAnchorPasswordReq req) {
		String password = req.getPassword();
		String newPassword = req.getNewPassword();
		// ??????????????????
		if (StringUtils.isBlank(password)) {
			return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
		}
		if (StringUtils.isBlank(newPassword)) {
			return ResultInfo.getInstance(StatusCode.EGISTER_BLANK_NEW_PASSWORD);
		}
		// ??????????????????
		MemUser memUser = memUserService.queryById(LoginInfoUtil.getUserId());
		// ????????????
		password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
		if (!password.equals(memUser.getPassword())) {
			return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
		}

		// ????????????
		memUser.setPassword(SecurityUtils.MD5SaltEncrypt(newPassword, memUser.getSalt()));
		memUserService.updateById(memUser);
		return ResultInfo.ok();
	}



	/**
	 * ????????????????????????????????????
	 * 
	 * @param userAnchorReq
	 * @return
	 */
	public PageInfo<AnchorIncomeDetailsVO> anchorIncomeDetails(UserAnchorReq userAnchorReq) {
//		if (userAnchorReq.getUserId() == null) {
//			userAnchorReq.setUserId(LoginInfoUtil.getUserId());
//		} else {
//			// ???????????????id
//			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
//			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
//			queryWrapper.lambda().eq(MemUserAnchor::getUserId, userAnchorReq.getUserId());
//			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
//			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
//			// ????????????????????????????????????
//			if (selectCount != 1) {
//				throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
//			}
//		}
//		
//		PageHelper.startPage(userAnchorReq.getPageNum(), userAnchorReq.getPageSize());
//		userAnchorReq.setLang(LoginInfoUtil.getLang());
//		List<AnchorIncomeDetailsVO> list = slaveMemUserAnchorMapper.anchorIncomeDetails(userAnchorReq);
//		for (AnchorIncomeDetailsVO anchorIncomeDetailsVO : list) {
//			if(anchorIncomeDetailsVO.getChangeType() == 7) {
//				anchorIncomeDetailsVO.setChangeName("??????");
//			}
//			if(anchorIncomeDetailsVO.getChangeType() == 14) {
//				anchorIncomeDetailsVO.setChangeName("??????");
//			}
//			if(anchorIncomeDetailsVO.getChangeType() == 21) {
//				anchorIncomeDetailsVO.setChangeName("??????");
//			}
//			if(anchorIncomeDetailsVO.getChangeType() == 22) {
//				anchorIncomeDetailsVO.setChangeName("??????");
//			}
//		}
		
		List<AnchorIncomeDetailsVO> fake = new ArrayList<>();
		for(int i = 0; i < userAnchorReq.getPageSize() ; i++) {
			AnchorIncomeDetailsVO anchorIncomeDetailsVO = new AnchorIncomeDetailsVO();
			anchorIncomeDetailsVO.setChangeMoney(new BigDecimal("188"));
			anchorIncomeDetailsVO.setChangeName("??????????????????");
			anchorIncomeDetailsVO.setChangeType(7);
			anchorIncomeDetailsVO.setCreateTime("2022-01-15 12:10:23");
			fake.add(anchorIncomeDetailsVO);
		}
		
		
		return new PageInfo<>(fake);
	}

	/**
	 * 
	 *	 ????????????????????????????????????????????????????????????????????????
	 * @return
	 */
	public ResultInfo<AnchorAssets> assets() {
		AnchorAssets anchorAssets = new AnchorAssets();
		Long userId = LoginInfoUtil.getUserId();
		// ????????????????????????
		MemUser anchor = memUserService.queryById(LoginInfoUtil.getUserId());
		// ?????????????????????????????????????????????????????????
		if (anchor.getUserType() == 2) {
			anchorAssets = slaveMemUserAnchorMapper.anchorAssets(userId);
		}
		// ?????????????????????????????????
		if (anchor.getUserType() == 3) {
			anchorAssets = slaveMemUserAnchorMapper.familyAssets(userId);
		}
		// ??????????????????
		CustomerSericeLangDto onlineService = getOnlineService();
		anchorAssets.setCustomerSerice(onlineService.getContext());
		return ResultInfo.ok(anchorAssets);
	}

	/**
	 * 	?????????????????????????????????????????????
	 * @return
	 */
	public ResultInfo<LiveLogForApiVO> liveTime(MemUserIdReq memUserIdReq) {
		Long userId = LoginInfoUtil.getUserId();
		if (memUserIdReq != null && memUserIdReq.getUserId() != null) {
			userId = memUserIdReq.getUserId();
			// ???????????????id
			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());

			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(MemUserAnchor::getUserId, userId);
			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
			// ????????????????????????????????????
			if (selectCount != 1) {
				return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
			}
		}
		return ResultInfo.ok(slaveMemUserAnchorMapper.getTodayLiveTime(userId));
	}

	/**
	 * 
	 * 	?????????????????? ???????????????
	 * @param anchorNameReq
	 * @return
	 */
	public Boolean modifNickName(AnchorNameReq anchorNameReq) {
		if(StringUtils.isEmpty(anchorNameReq.getNickName())) {
			throw new BusinessException("?????????????????????");
		}
		// ????????????????????????
		MemUser anchor = memUserService.queryById(LoginInfoUtil.getUserId());
		if(!anchor.getNickNameStatus()) {
			throw new BusinessException("???????????????????????????????????????????????????");
		}
		// ???????????????????????????
		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.NICK_NAME_FILTER);
		if (byCode != null) {
			NickNameFiterDto nickNameFiterDto = JSON.parseObject(byCode.getParamValue(), NickNameFiterDto.class);
			List<String> splitToList = Splitter.on(",").trimResults().splitToList(nickNameFiterDto.getContext());
			for (String nickNameFiter : splitToList) {
				if (anchorNameReq.getNickName().contains(nickNameFiter)) {
					throw new BusinessException(StatusCode.NICK_NAME_KILL_ILLEGAL);
				}
			}
		}
		anchor.setNickName(anchorNameReq.getNickName());
		anchor.setNickNameStatus(false);
		
		return memUserService.updateById(anchor);
	}
	
	 /**
     * 		????????????
     *
     * @param req
     */
    public Boolean updateAvatar(AnchorAvatarReq req) {
        //?????????????????????
        String relativeUrl = AWSS3Util.getRelativeUrl(req.getAvatar());
        MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
        user.setAvatar(relativeUrl);
        return memUserService.updateById(user);
    }

	/**
	 * 	????????????,TODO??????
	 * 
	 * @return
	 */
	public AgentInviteForIndexVo inviteDetail() {
		MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
		BigDecimal totalByType = memGoldchangeService.getUserGoldchangeTotalByType(user.getUserAccount(), 14);
//		agentInviteRecordService.
		AgentInviteForIndexVo agentInviteForIndexVo = new AgentInviteForIndexVo();
		agentInviteForIndexVo.setInviteAward(totalByType);
		agentInviteForIndexVo.setInviteCode("X2PU7W");
		agentInviteForIndexVo.setInviteCount(26);
		return agentInviteForIndexVo;
	}

	/**
	 * 	???????????????????????????????????????????????????
	 * @return
	 */
	public List<LiveHistoryVo> liveHistory() {
		return liveStudioLogService.liveHistory(117L);
	}

	/**
	 * 	??????????????????????????????
	 * 	 
	 * @param anchorIncomeMonthReq
	 * @return
	 */
	public AnchorIncomeMonthVO anchorIncomeMonth(AnchorIncomeMonthReq anchorIncomeMonthReq) {
		if (StringUtils.isEmpty(anchorIncomeMonthReq.getYearMonth())) {
			throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
		}
		if (anchorIncomeMonthReq.getUserId() == null) {
			anchorIncomeMonthReq.setUserId(LoginInfoUtil.getUserId());
		} else {
			// ???????????????id
			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(MemUserAnchor::getUserId, anchorIncomeMonthReq.getUserId());
			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
			// ????????????????????????????????????
			if (selectCount != 1) {
				throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
			}
		}
		// ?????????????????????????????????
//		BigDecimal income = slaveMemUserAnchorMapper.getMonthIncomeByChangeType(anchorIncomeMonthReq.getUserId(), 
//				anchorIncomeMonthReq.getYearMonth(), anchorIncomeMonthReq.getChangeType());
//		// ?????????????????????????????????
//		BigDecimal expend = slaveMemUserAnchorMapper.getMonthExpend(anchorIncomeMonthReq.getUserId(), 
//				anchorIncomeMonthReq.getYearMonth());
		AnchorIncomeMonthVO anchorIncomeMonthVO = new AnchorIncomeMonthVO();
		anchorIncomeMonthVO.setExpend(new BigDecimal("10"));
		anchorIncomeMonthVO.setIncome(new BigDecimal("2530"));
		return anchorIncomeMonthVO;
	}

	/**
	 * 	??????/?????????????????????
	 * @return
	 */
	public MemAnchorInfoVO getUserInfo() {
		MemAnchorInfoVO memAnchorInfoVO = new MemAnchorInfoVO();
		MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
		BeanCopyUtil.copyProperties(user,memAnchorInfoVO);
		return memAnchorInfoVO;
	}

	/**
	 * 	?????????????????????
	 * 
	 * @return
	 */
	public CustomerSericeLangDto getOnlineService() {
		// ??????????????????????????????
		CustomerSericeLangDto customerSericeLangDto = null;
		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.CUSTOMER_SERVICE);
		String paramValue = byCode.getParamValue();
		if (StringUtils.isNotBlank(paramValue)) {
			List<CustomerSericeLangDto> customerSericeLangDtoList = JSON.parseArray(paramValue,
					CustomerSericeLangDto.class);
			// ?????????????????????
			List<CustomerSericeLangDto> collect = customerSericeLangDtoList.stream()
					.filter(t -> LoginInfoUtil.getLang().equals(t.getLang())).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(collect)) {
				customerSericeLangDto = collect.get(0);
			}
		}

		return customerSericeLangDto;
	}

}
