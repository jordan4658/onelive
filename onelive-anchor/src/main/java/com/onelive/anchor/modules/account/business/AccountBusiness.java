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
 * 家族管理
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
	 * 主播/家族长登录
	 * 
	 * @param req
	 * @return
	 */
	public ResultInfo<AnchorLoginTokenVo> login(FamilyLoginReq req) {
		String merchantCode = req.getMerchantCode();
		String password = req.getPassword();
		String userAccount = req.getUserAccount();
		// 参数校验
		if (StringUtils.isBlank(merchantCode) || StringUtils.isBlank(password) || StringUtils.isBlank(userAccount)) {
			return ResultInfo.getInstance(StatusCode.LOGIN_PARAM_ERROR);
		}
		Boolean isFamily = false;
		// 查询是否有该账户
		// 查询主播
		 MemUser memUser = memUserService.queryByAccount(userAccount, merchantCode, 2);
		if (memUser == null) {
			// 主播查询不到查询家长
			memUser = memUserService.queryByAccount(userAccount, merchantCode, 3);
			if (memUser == null) {
				return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
			}
			isFamily = true;
			log.info("家族长{} 正在登陆", memUser.getNickName());
		} else {
			log.info("主播{} 正在登陆", memUser.getNickName());
		}

		// 密码检验
		password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
		if (!password.equals(memUser.getPassword())) {
			// 登录次数过多，提示用户已锁定，1小时后解锁,5次锁定，TODO 次数配置在配置表
			Integer loginNum = AnchorBusinessRedisUtils.getLoginProtect(memUser.getAccno());
			if (loginNum >= 5) {
				return ResultInfo.getInstance(StatusCode.LOGIN_LOCK_PASSWORD);
			} else {
				AnchorBusinessRedisUtils.setLoginProtect(memUser.getAccno(), loginNum + 1);
				return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
			}
		}
		// 账号已被冻结
		if (memUser.getIsFrozen()) {
			return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
		}
		// 登录保护 TODO,有鉴权功能，先不搞
		
		
		// 更新会员表最后登录相关
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

		// app端逻辑是单点登录,清除之前登录的
		AnchorBusinessRedisUtils.deleteAllToken(memUser);
		// 生成token和其他登录信息
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
	 * 登录信息处理
	 *
	 * @param memUser
	 * @param isRegister 是否刚注册过
	 * @return
	 */
	private AppLoginTokenVo dealLogin(MemUser memUser, String countryCode, Boolean isRegister) {
		// 记录用户登录日志
		this.saveLogs(memUser);
		// 获取用户登录token
		String acctoken = this.getToken(memUser, countryCode);
		AppLoginTokenVo loginVO = new AppLoginTokenVo();
		loginVO.setAcctoken(acctoken);
		loginVO.setRegisterCountryCode(countryCode);
		return loginVO;
	}

	/**
	 * 记录用户的登录日志
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
	 * 获取用户token
	 *
	 * @param memUser
	 * @return
	 */
	private String getToken(MemUser memUser, String countryCode) {
		// 1、redis存储登录信息
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
		// 设置用户token
		AnchorBusinessRedisUtils.setAccessToken(memUser, acctoken, jsonStr);
		// 设置用户状态
		AnchorBusinessRedisUtils.setUserStatus(memUser);

		// 返回用户token
		return acctoken;
	}

	/**
	 * 	家长/主播修改自己的密码
	 * 
	 * @param req
	 * @return
	 */
	public ResultInfo<String> resetPassword(ResetAnchorPasswordReq req) {
		String password = req.getPassword();
		String newPassword = req.getNewPassword();
		// 密码不能为空
		if (StringUtils.isBlank(password)) {
			return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
		}
		if (StringUtils.isBlank(newPassword)) {
			return ResultInfo.getInstance(StatusCode.EGISTER_BLANK_NEW_PASSWORD);
		}
		// 当前登录用户
		MemUser memUser = memUserService.queryById(LoginInfoUtil.getUserId());
		// 密码检验
		password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
		if (!password.equals(memUser.getPassword())) {
			return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
		}

		// 更新密码
		memUser.setPassword(SecurityUtils.MD5SaltEncrypt(newPassword, memUser.getSalt()));
		memUserService.updateById(memUser);
		return ResultInfo.ok();
	}



	/**
	 * 收入详情，每笔收入的记录
	 * 
	 * @param userAnchorReq
	 * @return
	 */
	public PageInfo<AnchorIncomeDetailsVO> anchorIncomeDetails(UserAnchorReq userAnchorReq) {
//		if (userAnchorReq.getUserId() == null) {
//			userAnchorReq.setUserId(LoginInfoUtil.getUserId());
//		} else {
//			// 当前的家族id
//			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
//			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
//			queryWrapper.lambda().eq(MemUserAnchor::getUserId, userAnchorReq.getUserId());
//			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
//			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
//			// 判断家族名下是否有该主播
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
//				anchorIncomeDetailsVO.setChangeName("提现");
//			}
//			if(anchorIncomeDetailsVO.getChangeType() == 14) {
//				anchorIncomeDetailsVO.setChangeName("佣金");
//			}
//			if(anchorIncomeDetailsVO.getChangeType() == 21) {
//				anchorIncomeDetailsVO.setChangeName("订阅");
//			}
//			if(anchorIncomeDetailsVO.getChangeType() == 22) {
//				anchorIncomeDetailsVO.setChangeName("打赏");
//			}
//		}
		
		List<AnchorIncomeDetailsVO> fake = new ArrayList<>();
		for(int i = 0; i < userAnchorReq.getPageSize() ; i++) {
			AnchorIncomeDetailsVO anchorIncomeDetailsVO = new AnchorIncomeDetailsVO();
			anchorIncomeDetailsVO.setChangeMoney(new BigDecimal("188"));
			anchorIncomeDetailsVO.setChangeName("测试收入类型");
			anchorIncomeDetailsVO.setChangeType(7);
			anchorIncomeDetailsVO.setCreateTime("2022-01-15 12:10:23");
			fake.add(anchorIncomeDetailsVO);
		}
		
		
		return new PageInfo<>(fake);
	}

	/**
	 * 
	 *	 家长或主播查询自己的资产，包括今日收入，当月收入
	 * @return
	 */
	public ResultInfo<AnchorAssets> assets() {
		AnchorAssets anchorAssets = new AnchorAssets();
		Long userId = LoginInfoUtil.getUserId();
		// 获取该主播的信息
		MemUser anchor = memUserService.queryById(LoginInfoUtil.getUserId());
		// 查询主播的资产，包括今日收入，当月收入
		if (anchor.getUserType() == 2) {
			anchorAssets = slaveMemUserAnchorMapper.anchorAssets(userId);
		}
		// 查询家族长下的所有主播
		if (anchor.getUserType() == 3) {
			anchorAssets = slaveMemUserAnchorMapper.familyAssets(userId);
		}
		// 客服服务链接
		CustomerSericeLangDto onlineService = getOnlineService();
		anchorAssets.setCustomerSerice(onlineService.getContext());
		return ResultInfo.ok(anchorAssets);
	}

	/**
	 * 	家长或主播查询当日主播直播时长
	 * @return
	 */
	public ResultInfo<LiveLogForApiVO> liveTime(MemUserIdReq memUserIdReq) {
		Long userId = LoginInfoUtil.getUserId();
		if (memUserIdReq != null && memUserIdReq.getUserId() != null) {
			userId = memUserIdReq.getUserId();
			// 当前的家族id
			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());

			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(MemUserAnchor::getUserId, userId);
			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
			// 判断家族名下是否有该主播
			if (selectCount != 1) {
				return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
			}
		}
		return ResultInfo.ok(slaveMemUserAnchorMapper.getTodayLiveTime(userId));
	}

	/**
	 * 
	 * 	主播修改昵称 只能改一次
	 * @param anchorNameReq
	 * @return
	 */
	public Boolean modifNickName(AnchorNameReq anchorNameReq) {
		if(StringUtils.isEmpty(anchorNameReq.getNickName())) {
			throw new BusinessException("昵称不能为空！");
		}
		// 获取该主播的信息
		MemUser anchor = memUserService.queryById(LoginInfoUtil.getUserId());
		if(!anchor.getNickNameStatus()) {
			throw new BusinessException("昵称已经修改过一次，不可再次修改！");
		}
		// 用户昵称敏感字排查
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
     * 		更新头像
     *
     * @param req
     */
    public Boolean updateAvatar(AnchorAvatarReq req) {
        //转换成相对路径
        String relativeUrl = AWSS3Util.getRelativeUrl(req.getAvatar());
        MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
        user.setAvatar(relativeUrl);
        return memUserService.updateById(user);
    }

	/**
	 * 	代理详情,TODO二期
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
	 * 	主播查看自己的直播记录，最近三十天
	 * @return
	 */
	public List<LiveHistoryVo> liveHistory() {
		return liveStudioLogService.liveHistory(117L);
	}

	/**
	 * 	指定月份的收入，支出
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
			// 当前的家族id
			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(MemUserAnchor::getUserId, anchorIncomeMonthReq.getUserId());
			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
			// 判断家族名下是否有该主播
			if (selectCount != 1) {
				throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
			}
		}
		// 根据类型获取月份的收入
//		BigDecimal income = slaveMemUserAnchorMapper.getMonthIncomeByChangeType(anchorIncomeMonthReq.getUserId(), 
//				anchorIncomeMonthReq.getYearMonth(), anchorIncomeMonthReq.getChangeType());
//		// 指定月份的支出（提现）
//		BigDecimal expend = slaveMemUserAnchorMapper.getMonthExpend(anchorIncomeMonthReq.getUserId(), 
//				anchorIncomeMonthReq.getYearMonth());
		AnchorIncomeMonthVO anchorIncomeMonthVO = new AnchorIncomeMonthVO();
		anchorIncomeMonthVO.setExpend(new BigDecimal("10"));
		anchorIncomeMonthVO.setIncome(new BigDecimal("2530"));
		return anchorIncomeMonthVO;
	}

	/**
	 * 	主播/家族长基本信息
	 * @return
	 */
	public MemAnchorInfoVO getUserInfo() {
		MemAnchorInfoVO memAnchorInfoVO = new MemAnchorInfoVO();
		MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
		BeanCopyUtil.copyProperties(user,memAnchorInfoVO);
		return memAnchorInfoVO;
	}

	/**
	 * 	当前语言的客服
	 * 
	 * @return
	 */
	public CustomerSericeLangDto getOnlineService() {
		// 客服存在通用常量表里
		CustomerSericeLangDto customerSericeLangDto = null;
		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.CUSTOMER_SERVICE);
		String paramValue = byCode.getParamValue();
		if (StringUtils.isNotBlank(paramValue)) {
			List<CustomerSericeLangDto> customerSericeLangDtoList = JSON.parseArray(paramValue,
					CustomerSericeLangDto.class);
			// 当前语言的客服
			List<CustomerSericeLangDto> collect = customerSericeLangDtoList.stream()
					.filter(t -> LoginInfoUtil.getLang().equals(t.getLang())).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(collect)) {
				customerSericeLangDto = collect.get(0);
			}
		}

		return customerSericeLangDto;
	}

}
