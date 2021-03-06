//package com.onelive.anchor.modules.account;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.google.common.base.Splitter;
//import com.onelive.anchor.modules.account.business.FamilyBusiness;
//import com.onelive.anchor.service.AgentInviteRecordService;
//import com.onelive.anchor.service.LiveStudioLogService;
//import com.onelive.anchor.service.MemFamilyService;
//import com.onelive.anchor.service.MemGoldchangeService;
//import com.onelive.anchor.service.MemLoginRecordService;
//import com.onelive.anchor.service.MemUserAnchorService;
//import com.onelive.anchor.service.MemUserService;
//import com.onelive.anchor.service.SysCountryService;
//import com.onelive.anchor.service.SysParameterService;
//import com.onelive.common.base.LocaleMessageSourceService;
//import com.onelive.common.constants.sys.SysParameterConstants;
//import com.onelive.common.enums.StatusCode;
//import com.onelive.common.exception.BusinessException;
//import com.onelive.common.model.common.ResultInfo;
//import com.onelive.common.model.dto.mem.AnchorAssets;
//import com.onelive.common.model.dto.platformConfig.CustomerSericeLangDto;
//import com.onelive.common.model.dto.platformConfig.NickNameFiterDto;
//import com.onelive.common.model.req.login.FrozenAnchorReq;
//import com.onelive.common.model.req.mem.AnchorAvatarReq;
//import com.onelive.common.model.req.mem.AnchorIncomeMonthReq;
//import com.onelive.common.model.req.mem.AnchorNameReq;
//import com.onelive.common.model.req.mem.FamilSearchUserAnchorReq;
//import com.onelive.common.model.req.mem.FamilyCreateAnchorReq;
//import com.onelive.common.model.req.mem.MemUserAnchorReq;
//import com.onelive.common.model.req.mem.MemUserIdReq;
//import com.onelive.common.model.req.mem.UserAnchorReq;
//import com.onelive.common.model.vo.agent.AgentInviteForIndexVo;
//import com.onelive.common.model.vo.live.LiveHistoryVo;
//import com.onelive.common.model.vo.live.LiveLogForApiVO;
//import com.onelive.common.model.vo.login.AppLoginTokenVo;
//import com.onelive.common.model.vo.mem.AnchorForFamilyVO;
//import com.onelive.common.model.vo.mem.AnchorIncomeDetailsVO;
//import com.onelive.common.model.vo.mem.AnchorIncomeMonthVO;
//import com.onelive.common.model.vo.mem.MemAnchorInfoVO;
//import com.onelive.common.mybatis.entity.MemFamily;
//import com.onelive.common.mybatis.entity.MemUser;
//import com.onelive.common.mybatis.entity.MemUserAnchor;
//import com.onelive.common.mybatis.entity.SysParameter;
//import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
//import com.onelive.common.utils.Login.LoginInfoUtil;
//import com.onelive.common.utils.others.BeanCopyUtil;
//import com.onelive.common.utils.others.CollectionUtil;
//import com.onelive.common.utils.others.SecurityUtils;
//import com.onelive.common.utils.others.StringUtils;
//import com.onelive.common.utils.upload.AWSS3Util;
//
//import cn.hutool.core.util.StrUtil;
//
///**
// * ????????????
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AccountTest {
//
//	@Resource
//	private MemUserService memUserService;
//
//	@Resource
//	private MemFamilyService memFamilyService;
//
//	@Resource
//	private SysCountryService sysCountryService;
//
//	@Resource
//	private LocaleMessageSourceService localeMessageSourceService;
//
//	@Resource
//	private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;
//
//	@Resource
//	private MemLoginRecordService memLoginRecordService;
//
//	@Resource
//	private MemUserAnchorService memUserAnchorService;
//	
//	@Resource
//	private SysParameterService sysParameterService;
//	
//    @Resource
//    private LiveStudioLogService liveStudioLogService;
//    
//    @Resource
//    private MemGoldchangeService memGoldchangeService;
//    
//    @Resource
//    private AgentInviteRecordService agentInviteRecordService;
//    
//    @Resource
//    private FamilyBusiness familyBusiness;
//
//    
//
//	/**
//	 * ??????/???????????????
//	 * 
//	 * @param req
//	 * @return
//	 */
//    @Test
//	public void login() {
//		String merchantCode = "0";
//		String password = "123456";
//		String userAccount = "maomaojia";
//		Boolean isFamily = false;
//		// ????????????????????????
//		// ????????????
//		 MemUser memUser = memUserService.queryByAccount(userAccount, merchantCode, 2);
//		if (memUser == null) {
//			// ??????????????????????????????
//			memUser = memUserService.queryByAccount(userAccount, merchantCode, 3);
//			if (memUser == null) {
//				System.out.println(111);
//			}
//			isFamily = true;
//		}
//
//		// ????????????
//		password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
//		if (!password.equals(memUser.getPassword())) {
//		}
//		System.out.println(111);
//		
//	}
//	
//    @Test
//    public void anchorList() {
//    	FamilSearchUserAnchorReq userAnchorReq = new FamilSearchUserAnchorReq();
//		userAnchorReq.setMerchantCode("0");
//		MemFamily memFamily = memFamilyService.queryByUserId(103L);
//		PageHelper.startPage(2, 10);
//		List<AnchorForFamilyVO> list = slaveMemUserAnchorMapper.getListForFamily(memFamily.getId(),
//				userAnchorReq.getNickName());
//		for (AnchorForFamilyVO anchorForFamilyVO : list) {
//			anchorForFamilyVO.setAvatar(AWSS3Util.getAbsoluteUrl(anchorForFamilyVO.getAvatar()));
//		}
//
//	}
//
//	
//	/**
//	 * 	??????/???????????????????????????
//	 * 
//	 * @param req
//	 * @return
//	 */
//    @Test
//	public void resetPassword() {
//		String newPassword = "123456";
//		String password = "654321";
//		// ??????????????????
//		MemUser memUser = memUserService.queryById(117L);
//		// ????????????
//		password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
//		if (!password.equals(memUser.getPassword())) {
//			System.out.println("???????????????");
//			
//		}
//		
//		// ????????????
//		memUser.setPassword(SecurityUtils.MD5SaltEncrypt(newPassword, memUser.getSalt()));
//		memUserService.updateById(memUser);
//	}
//
//	/**
//	 * ??????????????????
//	 * 
//	 * @param memUserAnchorReq
//	 */
//	public void createAnchor(FamilyCreateAnchorReq familyCreateAnchorReq) throws Exception {
//		if (StrUtil.isBlank(familyCreateAnchorReq.getPassword())) {
//			throw new BusinessException("????????????????????????");
//		}
//		if (StrUtil.isBlank(familyCreateAnchorReq.getUserAccount())) {
//			throw new BusinessException("?????????????????????");
//		}
//		
//		// ????????????????????????
//		MemUser family = memUserService.queryById(LoginInfoUtil.getUserId());
//		
//		// ????????????
//		MemUserAnchorReq memUserAnchorReq = new MemUserAnchorReq();
//		memUserAnchorReq.setPassword(familyCreateAnchorReq.getPassword());
//		memUserAnchorReq.setUserAccount(familyCreateAnchorReq.getUserAccount());
////		memUserAnchorReq.setCountryId(family.getCountryId());
//		memUserAnchorService.save(memUserAnchorReq);
//	}
//
//	/**
//	 * 		????????????????????????
//	 * @param req
//	 * @return
//	 */
//	public ResultInfo<AppLoginTokenVo> frozenAnchor(FrozenAnchorReq req) {
//		MemUser memUser = memUserService.queryById(req.getUserId());
//		if (memUser == null) {
//			return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
//		}
//		memUser.setIsFrozen(req.getIsFrozen());
//		memUserService.updateById(memUser);
//		return ResultInfo.ok();
//	}
//
//	/**
//	 * ????????????????????????????????????
//	 * 
//	 * @param userAnchorReq
//	 * @return
//	 */
//	public PageInfo<AnchorIncomeDetailsVO> anchorIncomeDetails(UserAnchorReq userAnchorReq) {
////		if (userAnchorReq.getUserId() == null) {
////			userAnchorReq.setUserId(LoginInfoUtil.getUserId());
////		} else {
////			// ???????????????id
////			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
////			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
////			queryWrapper.lambda().eq(MemUserAnchor::getUserId, userAnchorReq.getUserId());
////			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
////			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
////			// ????????????????????????????????????
////			if (selectCount != 1) {
////				throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
////			}
////		}
////		
////		PageHelper.startPage(userAnchorReq.getPageNum(), userAnchorReq.getPageSize());
////		userAnchorReq.setLang(LoginInfoUtil.getLang());
////		List<AnchorIncomeDetailsVO> list = slaveMemUserAnchorMapper.anchorIncomeDetails(userAnchorReq);
////		for (AnchorIncomeDetailsVO anchorIncomeDetailsVO : list) {
////			if(anchorIncomeDetailsVO.getChangeType() == 7) {
////				anchorIncomeDetailsVO.setChangeName("??????");
////			}
////			if(anchorIncomeDetailsVO.getChangeType() == 14) {
////				anchorIncomeDetailsVO.setChangeName("??????");
////			}
////			if(anchorIncomeDetailsVO.getChangeType() == 21) {
////				anchorIncomeDetailsVO.setChangeName("??????");
////			}
////			if(anchorIncomeDetailsVO.getChangeType() == 22) {
////				anchorIncomeDetailsVO.setChangeName("??????");
////			}
////		}
//		
//		List<AnchorIncomeDetailsVO> fake = new ArrayList<>();
//		for(int i = 0; i < userAnchorReq.getPageSize() ; i++) {
//			AnchorIncomeDetailsVO anchorIncomeDetailsVO = new AnchorIncomeDetailsVO();
//			anchorIncomeDetailsVO.setChangeMoney(new BigDecimal("188"));
//			anchorIncomeDetailsVO.setChangeName("??????????????????");
//			anchorIncomeDetailsVO.setChangeType(7);
//			anchorIncomeDetailsVO.setCreateTime("2022-01-15 12:10:23");
//			fake.add(anchorIncomeDetailsVO);
//		}
//		
//		
//		return new PageInfo<>(fake);
//	}
//
//	/**
//	 * 
//	 *	 ????????????????????????????????????????????????????????????????????????
//	 * @return
//	 */
//	public ResultInfo<AnchorAssets> assets() {
//		AnchorAssets anchorAssets = new AnchorAssets();
//		Long userId = LoginInfoUtil.getUserId();
//		// ????????????????????????
//		MemUser anchor = memUserService.queryById(LoginInfoUtil.getUserId());
//		// ?????????????????????????????????????????????????????????
//		if (anchor.getUserType() == 2) {
//			anchorAssets = slaveMemUserAnchorMapper.anchorAssets(userId);
//		}
//		// ?????????????????????????????????
//		if (anchor.getUserType() == 3) {
//			anchorAssets = slaveMemUserAnchorMapper.familyAssets(userId);
//		}
//		
//		// ?????????
//		anchorAssets.setFocusMoney(new BigDecimal("251"));
//		anchorAssets.setGiftMoney(new BigDecimal("531"));
//		anchorAssets.setMonthIncome(new BigDecimal("541"));
//		anchorAssets.setOtherMoney(new BigDecimal("515"));
//		anchorAssets.setRebatesMoney(new BigDecimal("451"));
//		anchorAssets.setTodayIncome(new BigDecimal("551"));
//		anchorAssets.setTotalAssets(new BigDecimal("571"));
//		
//		return ResultInfo.ok(anchorAssets);
//	}
//
//	/**
//	 * 	?????????????????????????????????????????????
//	 * @return
//	 */
//	public ResultInfo<LiveLogForApiVO> liveTime(MemUserIdReq memUserIdReq) {
//		Long userId = LoginInfoUtil.getUserId();
//		if (memUserIdReq != null && memUserIdReq.getUserId() != null) {
//			userId = memUserIdReq.getUserId();
//			// ???????????????id
//			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
//
//			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
//			queryWrapper.lambda().eq(MemUserAnchor::getUserId, userId);
//			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
//			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
//			// ????????????????????????????????????
//			if (selectCount != 1) {
//				return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
//			}
//		}
//		return ResultInfo.ok(slaveMemUserAnchorMapper.getTodayLiveTime(userId));
//	}
//
//	/**
//	 * 
//	 * 	?????????????????? ???????????????
//	 * @param anchorNameReq
//	 * @return
//	 */
//	public Boolean modifNickName(AnchorNameReq anchorNameReq) {
//		if(StringUtils.isEmpty(anchorNameReq.getNickName())) {
//			throw new BusinessException("?????????????????????");
//		}
//		// ????????????????????????
//		MemUser anchor = memUserService.queryById(LoginInfoUtil.getUserId());
//		if(!anchor.getNickNameStatus()) {
//			throw new BusinessException("???????????????????????????????????????????????????");
//		}
//		// ???????????????????????????
//		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.NICK_NAME_FILTER);
//		if (byCode != null) {
//			NickNameFiterDto nickNameFiterDto = JSON.parseObject(byCode.getParamValue(), NickNameFiterDto.class);
//			List<String> splitToList = Splitter.on(",").trimResults().splitToList(nickNameFiterDto.getContext());
//			for (String nickNameFiter : splitToList) {
//				if (anchorNameReq.getNickName().contains(nickNameFiter)) {
//					throw new BusinessException(StatusCode.NICK_NAME_KILL_ILLEGAL);
//				}
//			}
//		}
//		anchor.setNickName(anchorNameReq.getNickName());
//		anchor.setNickNameStatus(false);
//		
//		return memUserService.updateById(anchor);
//	}
//	
//	 /**
//     * 		????????????
//     *
//     * @param req
//     */
//    public Boolean updateAvatar(AnchorAvatarReq req) {
//        //?????????????????????
//        String relativeUrl = AWSS3Util.getRelativeUrl(req.getAvatar());
//        MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
//        user.setAvatar(relativeUrl);
//        return memUserService.updateById(user);
//    }
//
//	/**
//	 * 	????????????,TODO??????
//	 * 
//	 * @return
//	 */
//	public AgentInviteForIndexVo inviteDetail() {
//		MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
//		BigDecimal totalByType = memGoldchangeService.getUserGoldchangeTotalByType(user.getUserAccount(), 14);
////		agentInviteRecordService.
//		AgentInviteForIndexVo agentInviteForIndexVo = new AgentInviteForIndexVo();
//		agentInviteForIndexVo.setInviteAward(totalByType);
//		agentInviteForIndexVo.setInviteCode("X2PU7W");
//		agentInviteForIndexVo.setInviteCount(26);
//		return agentInviteForIndexVo;
//	}
//
//	/**
//	 * 	???????????????????????????????????????????????????
//	 * @return
//	 */
//	public List<LiveHistoryVo> liveHistory() {
//		return liveStudioLogService.liveHistory(117L);
//	}
//
//	/**
//	 * 	??????????????????????????????
//	 * 	 
//	 * @param anchorIncomeMonthReq
//	 * @return
//	 */
//	public AnchorIncomeMonthVO anchorIncomeMonth(AnchorIncomeMonthReq anchorIncomeMonthReq) {
//		if (StringUtils.isEmpty(anchorIncomeMonthReq.getYearMonth())) {
//			throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
//		}
//		if (anchorIncomeMonthReq.getUserId() == null) {
//			anchorIncomeMonthReq.setUserId(LoginInfoUtil.getUserId());
//		} else {
//			// ???????????????id
//			MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
//			QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
//			queryWrapper.lambda().eq(MemUserAnchor::getUserId, anchorIncomeMonthReq.getUserId());
//			queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
//			Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
//			// ????????????????????????????????????
//			if (selectCount != 1) {
//				throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
//			}
//		}
//		// ?????????????????????????????????
////		BigDecimal income = slaveMemUserAnchorMapper.getMonthIncomeByChangeType(anchorIncomeMonthReq.getUserId(), 
////				anchorIncomeMonthReq.getYearMonth(), anchorIncomeMonthReq.getChangeType());
////		// ?????????????????????????????????
////		BigDecimal expend = slaveMemUserAnchorMapper.getMonthExpend(anchorIncomeMonthReq.getUserId(), 
////				anchorIncomeMonthReq.getYearMonth());
//		AnchorIncomeMonthVO anchorIncomeMonthVO = new AnchorIncomeMonthVO();
//		anchorIncomeMonthVO.setExpend(new BigDecimal("10"));
//		anchorIncomeMonthVO.setIncome(new BigDecimal("2530"));
//		return anchorIncomeMonthVO;
//	}
//
//	/**
//	 * 	??????/?????????????????????
//	 * @return
//	 */
//	public MemAnchorInfoVO getUserInfo() {
//		MemAnchorInfoVO memAnchorInfoVO = new MemAnchorInfoVO();
//		MemUser user = memUserService.queryById(LoginInfoUtil.getUserId());
//		BeanCopyUtil.copyProperties(user,memAnchorInfoVO);
//		return memAnchorInfoVO;
//	}
//
//	/**
//	 * 	?????????????????????
//	 * 
//	 * @return
//	 */
//	public CustomerSericeLangDto getOnlineService() {
//		// ??????????????????????????????
//		CustomerSericeLangDto customerSericeLangDto = null;
//		SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.CUSTOMER_SERVICE);
//		String paramValue = byCode.getParamValue();
//		if (StringUtils.isNotBlank(paramValue)) {
//			List<CustomerSericeLangDto> customerSericeLangDtoList = JSON.parseArray(paramValue,
//					CustomerSericeLangDto.class);
//			// ?????????????????????
//			List<CustomerSericeLangDto> collect = customerSericeLangDtoList.stream()
//					.filter(t -> LoginInfoUtil.getLang().equals(t.getLang())).collect(Collectors.toList());
//			if (CollectionUtil.isNotEmpty(collect)) {
//				customerSericeLangDto = collect.get(0);
//			}
//		}
//
//		return customerSericeLangDto;
//	}
//
//}
