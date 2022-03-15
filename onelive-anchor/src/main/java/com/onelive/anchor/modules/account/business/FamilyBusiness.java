package com.onelive.anchor.modules.account.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.anchor.service.*;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.client.PayClientFeignClient;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.login.FrozenAnchorReq;
import com.onelive.common.model.req.login.ResetAnchorPasswordReq;
import com.onelive.common.model.req.mem.*;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.req.pay.WithdrawReq;
import com.onelive.common.model.req.pay.WithdrawReqFeign;
import com.onelive.common.model.vo.common.SelectBankStringVO;
import com.onelive.common.model.vo.login.AppLoginTokenVo;
import com.onelive.common.model.vo.mem.*;
import com.onelive.common.model.vo.pay.PayWithdrawResultVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemBankAccountMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemFamilyWithdrawalLogMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemWalletMapper;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.service.sms.SmsSendCommonUtils;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.pay.SnowflakeIdWorker;
import com.onelive.common.utils.upload.AWSS3Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 家族管理
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FamilyBusiness {

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

	@Resource
	private SlaveMemWalletMapper slaveMemWalletMapper;

	@Resource
	private SlaveMemBankAccountMapper slaveMemBankAccountMapper;

	@Resource
	private SysBusParameterService sysBusParameterService;

	@Resource
	private MemBankAccountService memBankAccountService;

	@Resource
	private AccountBalanceChangeService accountBalanceChangeService;

	@Resource
	private MemAnchorWithdrawalLogService memAnchorWithdrawalLogService;

	@Resource
	private MemFamilyWithdrawalLogService memFamilyWithdrawalLogService;

	@Resource
	private SlaveMemFamilyWithdrawalLogMapper slaveMemFamilyWithdrawalLogMapper;

	@Resource
	private RedissonClient redissonClient;

	@Resource
	private PayClientFeignClient payClientFeignClient;

	/**
	 *
	 * 家长修改主播密码
	 *
	 * @param req
	 * @return
	 */
	public ResultInfo<String> resetAnchorPassword(ResetAnchorPasswordReq req) {
		String password = req.getPassword();
		String newPassword = req.getNewPassword();
		Long userId = req.getUserId();
		// 密码不能为空
		if (StringUtils.isBlank(password)) {
			return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
		}
		if (StringUtils.isBlank(newPassword)) {
			return ResultInfo.getInstance(StatusCode.EGISTER_BLANK_NEW_PASSWORD);
		}
		if (userId == null) {
			return ResultInfo.getInstance(StatusCode.ANCHOR_ID_ISNULL);
		}
		// 获取该家长的信息
		MemUser familyUser = memUserService.queryById(LoginInfoUtil.getUserId());
		// 密码检验
		password = SecurityUtils.MD5SaltEncrypt(password, familyUser.getSalt());
		if (!password.equals(familyUser.getPassword())) {
			return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
		}

		// 获取该主播的信息
		MemUser anchor = memUserService.queryById(userId);
		if (anchor == null) {
			return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
		}
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

		// 更新密码
		anchor.setPassword(SecurityUtils.MD5SaltEncrypt(newPassword, anchor.getSalt()));
		memUserService.updateById(anchor);
		return ResultInfo.ok();
	}

	/**
	 * 家长冻结主播账号
	 *
	 * @param req
	 * @return
	 */
	public ResultInfo<AppLoginTokenVo> frozenAnchor(FrozenAnchorReq req) {
		// 当前的家族id
		MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());

		QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemUserAnchor::getUserId, req.getUserId());
		queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
		Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
		// 判断家族名下是否有该主播
		if (selectCount != 1) {
			return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
		}

		MemUser memUser = memUserService.queryById(req.getUserId());
		if (memUser == null) {
			return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
		}
		memUser.setIsFrozen(req.getIsFrozen());
		memUserService.updateById(memUser);
		return ResultInfo.ok();
	}

	/**
	 * 新建主播信息
	 *
	 */
	public void createAnchor(FamilyCreateAnchorReq familyCreateAnchorReq) throws Exception {
		if (StrUtil.isBlank(familyCreateAnchorReq.getPassword())) {
			throw new BusinessException("登录密码不能为空");
		}
		if (StrUtil.isBlank(familyCreateAnchorReq.getUserAccount())) {
			throw new BusinessException("账户名不能为空");
		}

		// 获取该主播的信息
		MemUser family = memUserService.queryById(LoginInfoUtil.getUserId());
		MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());

		// 组装数据
		MemUserAnchorSaveReq memUserAnchorReq = new MemUserAnchorSaveReq();
		memUserAnchorReq.setPassword(familyCreateAnchorReq.getPassword());
		memUserAnchorReq.setUserAccount(familyCreateAnchorReq.getUserAccount());
		memUserAnchorReq.setCountryCode(family.getRegisterCountryCode());
		memUserAnchorReq.setFamilyId(memFamily.getId());
		memUserAnchorService.save(memUserAnchorReq);
	}

	/**
	 * 家长查询下面的主播
	 *
	 * @param userAnchorReq
	 * @returns
	 */
	public PageInfo<AnchorForFamilyVO> anchorList(FamilSearchUserAnchorReq userAnchorReq) {
		userAnchorReq.setMerchantCode(LoginInfoUtil.getMerchantCode());
		MemFamily memFamily = memFamilyService.queryByUserId(LoginInfoUtil.getUserId());
		PageHelper.startPage(userAnchorReq.getPageNum(), userAnchorReq.getPageSize());
		List<AnchorForFamilyVO> list = slaveMemUserAnchorMapper.getListForFamily(memFamily.getId(),
				userAnchorReq.getNickName());
		for (AnchorForFamilyVO anchorForFamilyVO : list) {
			anchorForFamilyVO.setAvatar(AWSS3Util.getAbsoluteUrl(anchorForFamilyVO.getAvatar()));
		}

		return new PageInfo<>(list);
	}

	/**
	 * 查询可以提现额，主播可提现额
	 *
	 * @return
	 */
	public FamilyCanWithdrawVO canWithdraw() {
//		Long familyUserId = LoginInfoUtil.getUserId();
//		QueryWrapper<MemWallet> queryWrapperWallet = new QueryWrapper<>();
//		queryWrapperWallet.lambda().eq(MemWallet::getUserId, familyUserId);
//		queryWrapperWallet.lambda().eq(MemWallet::getWalletType, 1);

//		// 可以提现的金币
//		BigDecimal withdrawAccount = slaveMemWalletMapper.selectOne(queryWrapperWallet).getAmount();
//
//		// 当前的家族id
//		MemFamily memFamily = memFamilyService.queryByUserId(familyUserId);
//
//		// 查询名下的主播
//		QueryWrapper<MemUserAnchor> queryWrapperAnchor = new QueryWrapper<>();
//		queryWrapperAnchor.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
//		List<MemUserAnchor> anchorList = slaveMemUserAnchorMapper.selectList(queryWrapperAnchor);
//		Set<Long> anchorUserIds = anchorList.stream().map(t -> t.getUserId()).collect(Collectors.toSet());
//
//		// 主播们的钱包
//		QueryWrapper<MemWallet> queryWrapperWalletAnchor = new QueryWrapper<>();
//		queryWrapperWalletAnchor.lambda().in(MemWallet::getUserId, anchorUserIds);
//		queryWrapperWalletAnchor.lambda().eq(MemWallet::getWalletType, 1);
//		List<MemWallet> anchorWallet = slaveMemWalletMapper.selectList(queryWrapperWalletAnchor);
//		BigDecimal withdrawAnchor = anchorWallet.stream().map(MemWallet::getAmount).reduce(BigDecimal.ZERO,
//				BigDecimal::add);
//		return new FamilyCanWithdrawVO(withdrawAccount, withdrawAnchor);
		return new FamilyCanWithdrawVO(new BigDecimal("15165"), new BigDecimal("62165"));
	}

	/**
	 * 查询名下主播的可提现列表
	 *
	 * @return
	 */
	public FamilyWithdrawAnchorVo canWithdrawAnchor() {
		Long familyUserId = LoginInfoUtil.getUserId();
		// 当前的家族id
		MemFamily memFamily = memFamilyService.queryByUserId(familyUserId);
		List<WithdrawAnchorVo> list = slaveMemUserAnchorMapper.canWithdrawAnchor(memFamily.getId());
		for (WithdrawAnchorVo withdrawAnchorVo : list) {
			withdrawAnchorVo.setAvatar(AWSS3Util.getAbsoluteUrl(withdrawAnchorVo.getAvatar()));
		}
		// 金额总和
		BigDecimal reduce = list.stream().map(WithdrawAnchorVo :: getWithdrawAccount).reduce(BigDecimal.ZERO,BigDecimal::add);
		FamilyWithdrawAnchorVo familyWithdrawAnchorVo = new FamilyWithdrawAnchorVo();
		familyWithdrawAnchorVo.setWithdrawAnchorList(list);
		familyWithdrawAnchorVo.setWithdrawAccount(reduce);
		return familyWithdrawAnchorVo;
	}

	/**
	 * 家族长的银行卡列表
	 *
	 * @return
	 */
	public FamilyBankAccountVO bankList() {
		String userAccount = LoginInfoUtil.getUserAccount();
		List<MemBankAccountVO> list = slaveMemBankAccountMapper.list(userAccount);
		if (CollectionUtils.isNotEmpty(list)) {
			Iterator<MemBankAccountVO> iterator = list.iterator();
			while (iterator.hasNext()) {
				MemBankAccountVO vo = iterator.next();
				String accountNo = vo.getBankAccountNo();
				if (StringUtils.isNotBlank(accountNo) && accountNo.length() > 4) {
					vo.setBankAccountNo(bankConvertToHidden(accountNo));
				}
				SysBusParameter param = sysBusParameterService.getByCode(vo.getBankCode());
				if (param == null) {
					continue;
				}
				String bankName = param.getParamValue();
				String remark = param.getRemark();
				vo.setBankName(bankName);
				vo.setBankLogo(remark);
			}
		}

		FamilyBankAccountVO familyBankAccountVO = new FamilyBankAccountVO();
		familyBankAccountVO.setMemBankAccountList(list);
		familyBankAccountVO.setCanBindCount(3 - list.size());
		return familyBankAccountVO;
	}

	/**
	 * 银行卡号进行隐式显示 例如 ：6214830119460961 -> **** **** **** 0961
	 *
	 * @param bankNo
	 * @return
	 */
	private String bankConvertToHidden(String bankNo) {
		StringBuffer hiddenBankNo = new StringBuffer();
		String cutOutBankNo = bankNo.substring(bankNo.length() - 4, bankNo.length());
		hiddenBankNo.append(PayConstants.CONVERT_STR).append(cutOutBankNo);
		return hiddenBankNo.toString();
	}

	/**
	 * 家族新增-银行卡信息
	 *
	 * @param memBankAccountAddReq
	 * @param user
	 */
	@Transactional
	public void addBank(MemBankAccountAddReq memBankAccountAddReq, AppLoginUser user) {

		if (StringUtils.isBlank(memBankAccountAddReq.getBankAccountNo())) {
			throw new BusinessException("银行卡号为空！");
		}
		String accountNo = memBankAccountAddReq.getBankAccountNo().replaceAll(" ", "");
		if (accountNo.length() < 16 || accountNo.length() > 20) {
			throw new BusinessException("银行卡号位数不对");
		}
		if (StringUtils.isBlank(memBankAccountAddReq.getBankAccountName())) {
			throw new BusinessException("开户行姓名为空！");
		}
		if (StringUtils.isBlank(memBankAccountAddReq.getBankCode())) {
			throw new BusinessException("所属银行为空！");
		}
		if (StringUtils.isBlank(memBankAccountAddReq.getBankAddress())) {
			throw new BusinessException("银行卡开户行地址为空！");
		}
		SysBusParameter param = sysBusParameterService.getByCode(memBankAccountAddReq.getBankCode());
		if (param == null) {
			throw new BusinessException("所属银行错误！");
		}
		String userAccount = LoginInfoUtil.getUserAccount();

		//验证短信验证码是否正确
		Boolean smsFlag = SmsSendCommonUtils.getVerificationCode(user.getAreaCode() + user.getMobilePhone(), 6, memBankAccountAddReq.getSmsCode());
		if (!smsFlag) {
			throw new BusinessException(StatusCode.SMS_CODE_ERROR);
		}
		// 检查新增的银行卡号是否已经添加过的卡号
		Integer count = memBankAccountService.getCountByAccountNo(accountNo);
		if (count != 0) {
			throw new BusinessException("新增银行卡号已存在，请勿重复新增！");
		}
		// 检查银行是否超过3张了
		Integer bankCount = memBankAccountService.getBankCountByAccount(userAccount);
		if (bankCount >= 3) {
			throw new BusinessException("您最多只能绑定3张银行卡！");
		}

		// 新增银行卡记录
		MemBankAccount memBankAccount = new MemBankAccount();
		BeanUtils.copyProperties(memBankAccountAddReq, memBankAccount);
		Date date = new Date();
		memBankAccount.setBankAccountNo(accountNo);
		memBankAccount.setAccount(userAccount);
		memBankAccount.setUpdateUser(userAccount);
		memBankAccount.setCreateTime(date);
		memBankAccount.setUpdateTime(date);
		memBankAccount.setIsDelete(false);
		memBankAccount.setCreateUser(userAccount);
		memBankAccountService.add(memBankAccount);
	}

	/**
	 * 更新银行卡信息
	 *
	 * @param memBankAccountUpdateReq
	 */
	public void updateBank(MemBankAccountUpdateReq memBankAccountUpdateReq) {
		if (memBankAccountUpdateReq.getBankAccid() == null) {
			throw new BusinessException("家族银行卡id为空！");
		}
		if (StringUtils.isBlank(memBankAccountUpdateReq.getBankAccountNo())) {
			throw new BusinessException("银行卡号为空！");
		}
		String accountNo = memBankAccountUpdateReq.getBankAccountNo().replaceAll(" ", "");
		if (accountNo.length() < 16 || accountNo.length() > 20) {
			throw new BusinessException("银行卡号位数不对");
		}
		if (StringUtils.isBlank(memBankAccountUpdateReq.getBankAccountName())) {
			throw new BusinessException("开户行姓名为空！");
		}
		if (StringUtils.isBlank(memBankAccountUpdateReq.getBankCode())) {
			throw new BusinessException("所属银行为空！");
		}
		if (StringUtils.isBlank(memBankAccountUpdateReq.getBankAddress())) {
			throw new BusinessException("银行卡开户行地址为空！");
		}
		String userAccount = LoginInfoUtil.getUserAccount();

		MemBankAccount memBankAccount = memBankAccountService.getById(memBankAccountUpdateReq.getBankAccid());
		if (memBankAccount == null) {
			throw new BusinessException("该家族的银行卡不存在！");
		}
		// 检查更新的的银行卡号是否已经添加过的卡号
		Integer count = memBankAccountService.getCountByAccountNoBankAccid(accountNo,
				memBankAccountUpdateReq.getBankAccid());
		if (count != 0) {
			throw new BusinessException("更新的银行卡号已经存在，请重新填写新的卡号！");
		}
		BeanUtils.copyProperties(memBankAccountUpdateReq, memBankAccount);
		Date date = new Date();
		memBankAccount.setBankAccountNo(accountNo);
		memBankAccount.setAccount(userAccount);
		memBankAccount.setUpdateUser(userAccount);
		memBankAccount.setUpdateTime(date);
		memBankAccountService.updateMemBankInfo(memBankAccount);

	}

	/**
	 * 删除银行卡信息
	 *
	 * @param memAccountBankDeleteReq
	 */
	public void deleteBank(MemAccountBankDeleteReq memAccountBankDeleteReq) {
		if (memAccountBankDeleteReq.getBankAccid() == null) {
			throw new BusinessException("银行卡id为空！");
		}
		String userAccount = LoginInfoUtil.getUserAccount();
		memBankAccountService.delete(memAccountBankDeleteReq.getBankAccid(), userAccount);
	}

	/**
	 * 获取银行卡下拉列表
	 *
	 * @param code
	 * @return
	 */
	public List<SelectBankStringVO> getByBankList(String code) {
		if (StringUtils.isBlank(code)) {
			throw new BusinessException("业务参数父代码为空！");
		}
		List<SelectBankStringVO> listVo = new ArrayList<>();
		List<SysBusParameter> list = sysBusParameterService.getByParentCode(code);
		for (SysBusParameter sysBusParameter : list) {
			SelectBankStringVO vo = new SelectBankStringVO();
			vo.setValue(sysBusParameter.getParamCode());
			vo.setDes(sysBusParameter.getParamValue());
			vo.setLogo(sysBusParameter.getRemark());
			listVo.add(vo);
		}
		return listVo;
	}

	/**
	 * 提现主播金额，不传userid 即全部
	 * @return
	 */
	@Transactional
	public Boolean withdrawAnchor(FamilyWithdrawAnchor familyWithdrawAnchor) {
		String account = LoginInfoUtil.getUserAccount();
		RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.FAMILY_WITHDRAWANCHOR + account);
		try {
			boolean bool = lock.writeLock().tryLock(10, 120, TimeUnit.SECONDS);
			if (bool) {
				Long familyUserId = LoginInfoUtil.getUserId();
				// 当前的家族id
				MemFamily memFamily = memFamilyService.queryByUserId(familyUserId);

				BigDecimal withdrawAnchor = new BigDecimal("0.00");
				List<MemWallet> anchorWalletList = Collections.EMPTY_LIST;
				// 如果userid = null  即：提现全部主播的全部金额
				if (familyWithdrawAnchor.getUserId() == null) {
					// 查询名下的主播
					QueryWrapper<MemUserAnchor> queryWrapperAnchor = new QueryWrapper<>();
					queryWrapperAnchor.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
					List<MemUserAnchor> anchorList = slaveMemUserAnchorMapper.selectList(queryWrapperAnchor);
					Set<Long> anchorUserIds = anchorList.stream().map(t -> t.getUserId()).collect(Collectors.toSet());

					// 主播们的钱包
					QueryWrapper<MemWallet> queryWrapperWalletAnchor = new QueryWrapper<>();
					queryWrapperWalletAnchor.lambda().in(MemWallet::getUserId, anchorUserIds);
					queryWrapperWalletAnchor.lambda().eq(MemWallet::getWalletType, 1);
					anchorWalletList = slaveMemWalletMapper.selectList(queryWrapperWalletAnchor);
					withdrawAnchor = anchorWalletList.stream().map(MemWallet::getAmount).reduce(BigDecimal.ZERO,
							BigDecimal::add);
				} else {
					// 提现单个主播
					QueryWrapper<MemUserAnchor> queryWrapper = new QueryWrapper<>();
					queryWrapper.lambda().eq(MemUserAnchor::getUserId, familyWithdrawAnchor.getUserId());
					queryWrapper.lambda().eq(MemUserAnchor::getFamilyId, memFamily.getId());
					Integer selectCount = slaveMemUserAnchorMapper.selectCount(queryWrapper);
					// 判断家族名下是否有该主播
					if (selectCount != 1) {
						throw new BusinessException(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
					}
					// 主播的钱包
					QueryWrapper<MemWallet> queryWrapperWalletAnchor = new QueryWrapper<>();
					queryWrapperWalletAnchor.lambda().in(MemWallet::getUserId, familyWithdrawAnchor.getUserId());
					queryWrapperWalletAnchor.lambda().eq(MemWallet::getWalletType, 1);
					anchorWalletList = slaveMemWalletMapper.selectList(queryWrapperWalletAnchor);
					// 当前主播的余额
					withdrawAnchor = anchorWalletList.stream().map(MemWallet::getAmount).reduce(BigDecimal.ZERO,
							BigDecimal::add);
					// 提现金额不可以大于余额 
					if (familyWithdrawAnchor.getWithdrawMoney() != null
							&& withdrawAnchor.compareTo(familyWithdrawAnchor.getWithdrawMoney()) == -1) {
						throw new BusinessException("提现金额大于当前主播的余额");
					}
					// 如果指定提现金额
					if (familyWithdrawAnchor.getWithdrawMoney() != null) {
						withdrawAnchor = familyWithdrawAnchor.getWithdrawMoney();
					}
				}

				// 可提现金额不大于0
				if (BigDecimal.ZERO.compareTo(withdrawAnchor) != 1) {
					throw new BusinessException("主播可提现金额不可以小于0");
				}
				// 分成后的钱,实际到账的钱
				BigDecimal realWithdrawAnchor = withdrawAnchor.multiply(memFamily.getGiftRatio()).divide(new BigDecimal("100"));

				QueryWrapper<MemWallet> queryWrapperWalletFamily = new QueryWrapper<>();
				queryWrapperWalletFamily.lambda().in(MemWallet::getUserId, familyUserId);
				queryWrapperWalletFamily.lambda().eq(MemWallet::getWalletType, 1);

				MemWallet walletFamily = slaveMemWalletMapper.selectOne(queryWrapperWalletFamily);

				// 给家族长加钱 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
				// 家族长提现主播余额订单号
				String withdrawalLogNo = SnowflakeIdWorker.generateShortId();
				MemAccountChangeVO familyChangeVO = new MemAccountChangeVO();
				familyChangeVO.setOrderNo(withdrawalLogNo);
				familyChangeVO.setAccount(LoginInfoUtil.getUserAccount());
				familyChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
				familyChangeVO.setPrice(realWithdrawAnchor);
				familyChangeVO
						.setChangeType(PayConstants.AccountChangTypeEnum.FAMILY_WITHDRAW_ANCHOR33.getPayTypeCode());
				familyChangeVO.setOpNote(walletFamily.getWalletId().toString());
				BalanceChangeDTO flagVoFamily = accountBalanceChangeService.publicAccountBalanceChange(familyChangeVO);
				if (!flagVoFamily.getFlag()) {
					log.error("withdrawAnchor----家族长提款主播的金额到账户失败! 家族长账号:{} 提款金额：{},实际到账金额：{} ", account, withdrawAnchor, realWithdrawAnchor);
					throw new BusinessException("提款失败，请联系管理员");
				}

				Set<Long> anchorUserIds = anchorWalletList.stream().map(t -> t.getUserId()).collect(Collectors.toSet());
				// 记录家族长提现主播余额
				MemFamilyWithdrawalLog memFamilyWithdrawalLog = new MemFamilyWithdrawalLog();
				memFamilyWithdrawalLog.setFamilyId(memFamily.getId());
				memFamilyWithdrawalLog.setAnchorUserIds(StringUtils.join(anchorUserIds, ","));
				memFamilyWithdrawalLog.setGoldChangeNo(flagVoFamily.getChangeOrderNo());
				memFamilyWithdrawalLog.setWithdrawal(realWithdrawAnchor);
				memFamilyWithdrawalLog.setWithdrawalLogNo(withdrawalLogNo);
				memFamilyWithdrawalLog.setMerchantCode(LoginInfoUtil.getMerchantCode());
				memFamilyWithdrawalLogService.save(memFamilyWithdrawalLog);
				// 给家族长加加钱 ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑

				// 扣除主播的金额 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
				for (MemWallet memWallet : anchorWalletList) {
					// 主播被提现的金额
					BigDecimal amount = familyWithdrawAnchor.getWithdrawMoney() == null ? memWallet.getAmount()
							: familyWithdrawAnchor.getWithdrawMoney();
					String withdrawalLogNoAnchor = SnowflakeIdWorker.generateShortId();
					// 扣除主播的金额
					MemAccountChangeVO anchorVO = new MemAccountChangeVO();
					anchorVO.setOrderNo(withdrawalLogNo);
					anchorVO.setAccount(memWallet.getAccount());
					anchorVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
					anchorVO.setPrice(amount.multiply(new BigDecimal("-1")));
					anchorVO.setChangeType(PayConstants.AccountChangTypeEnum.FAMILY_WITHDRAW_ANCHOR34.getPayTypeCode());
					anchorVO.setOpNote(memWallet.getWalletId().toString());
					BalanceChangeDTO flagVoAnchor = accountBalanceChangeService.publicAccountBalanceChange(anchorVO);
					if (!flagVoAnchor.getFlag()) {
						log.error("withdrawAnchor----家族长提款主播的金额到账户失败! 家族长账号:：{}, 主播账号： {}， 提款金额：{} ", account,
								memWallet.getAccount(), amount);
						throw new BusinessException("主播账号：" + memWallet.getAccount() + "转账失败，本次提款失败，请联系管理员");
					}

					// 记录主播被提现
					MemAnchorWithdrawalLog memAnchorWithdrawalLog = new MemAnchorWithdrawalLog();
					memAnchorWithdrawalLog.setFamilyId(memFamily.getId());
					memAnchorWithdrawalLog.setAnchorUserId(memWallet.getUserId());
					memAnchorWithdrawalLog.setGoldChangeNo(flagVoAnchor.getChangeOrderNo());
					memAnchorWithdrawalLog.setWithdrawal(amount);
					memAnchorWithdrawalLog.setWithdrawalLogNo(withdrawalLogNoAnchor);
					memAnchorWithdrawalLog.setMerchantCode(LoginInfoUtil.getMerchantCode());
					memAnchorWithdrawalLog.setFamilyWithdrawalLogNo(withdrawalLogNo);
					memFamilyWithdrawalLogService.save(memFamilyWithdrawalLog);
				}
				// 扣除主播的金额 ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑
				log.info("withdrawAnchor----家族长提款主播的金额到账户结束，释放锁 家族长账号:{} 提款金额 :{} 分成后实际到账金额: {}", account, withdrawAnchor, realWithdrawAnchor);
			} else {
				log.error("withdrawAnchor---- 家族长账号:{}提款主播的金额到账户没拿到锁, 提款失败 ", account);
				throw new BusinessException(StatusCode.PROCESSING);
			}
		} catch (Exception e) {
			log.error("withdrawAnchor---- 家族长账号:" + account + "提款主播的金额到账户异常, 提款失败 ", e);
			throw new BusinessException("正在处理中");
		} finally {
			// 释放锁
			lock.writeLock().unlock();
			log.info("withdrawAnchor----家族长提款主播的金额到账户结束，释放锁");
		}
		return true;
	}

	/**
	 * 家族长提现余额到银行卡
	 *
	 * @param withdrawReq
	 * @return
	 */
	public ResultInfo<PayWithdrawResultVO> withdrawCash(WithdrawReq withdrawReq) {
		WithdrawReqFeign withdrawReqFeign = new WithdrawReqFeign();
		withdrawReqFeign.setUserId(LoginInfoUtil.getUserId());
		withdrawReqFeign.setRequestIp(LoginInfoUtil.getIp());
		withdrawReqFeign.setSource(LoginInfoUtil.getSource());
		withdrawReqFeign.setPrice(withdrawReq.getPrice());
		withdrawReqFeign.setBankAccid(withdrawReq.getBankAccid());
		ResultInfo<PayWithdrawResultVO> withdraw = payClientFeignClient.withdraw(withdrawReqFeign);
		//
//		String withdrawOrderNo = withdraw.getData().getWithdrawOrderNo();

		return withdraw;
	}

	/**
	 * 家族长的财务记录
	 *
	 * @return
	 */
	public PageInfo<FamilyFinancialRecord> financialRecord(FamilyFinancialRecordReq familyFinancialRecordReq) {
		Long familyUserId = LoginInfoUtil.getUserId();
		MemUser familyUser = memUserService.queryById(familyUserId);
		// 当前的家族id
		MemFamily memFamily = memFamilyService.queryByUserId(familyUserId);
		PageHelper.startPage(familyFinancialRecordReq.getPageNum(), familyFinancialRecordReq.getPageSize());
		return new PageInfo<>(slaveMemFamilyWithdrawalLogMapper.financialRecord(familyUser.getUserAccount(),
				memFamily.getId(), familyFinancialRecordReq.getType()));
	}

	/**
	 * 家族长的提现index页面
	 *
	 * @return
	 */
	public WithdrawIndexVO withdrawIndex() {
		WithdrawIndexVO withdrawIndexVO = new WithdrawIndexVO();
//
//		Long familyUserId = LoginInfoUtil.getUserId();
//		String userAccount = LoginInfoUtil.getUserAccount();
//
//		// 默认银行卡
//		MemBankAccount memBankAccount = slaveMemBankAccountMapper.getFirstBank(LoginInfoUtil.getUserAccount());
//		if (memBankAccount != null) {
//			SysBusParameter param = sysBusParameterService.getByCode(memBankAccount.getBankCode());
//			withdrawIndexVO.setBankAccid(memBankAccount.getBankAccid());
//			withdrawIndexVO.setBankAccountNo(memBankAccount.getBankAccountNo());
//			withdrawIndexVO.setBankCode(memBankAccount.getBankCode());
//			if (param != null) {
//				withdrawIndexVO.setBankName(param.getParamValue());
//				withdrawIndexVO.setBankLogo(param.getRemark());
//			}
//		}
//		MemFamily memFamily = memFamilyService.queryByUserId(familyUserId);
//		withdrawIndexVO.setAccount(userAccount);
//		withdrawIndexVO.setGiftRatio(memFamily.getGiftRatio());
//
//		QueryWrapper<MemWallet> queryWrapperWallet = new QueryWrapper<>();
//		queryWrapperWallet.lambda().eq(MemWallet::getAccount, userAccount);
//		queryWrapperWallet.lambda().eq(MemWallet::getWalletType, 1);
//		MemWallet memWallet = slaveMemWalletMapper.selectOne(queryWrapperWallet);
//
//		BigDecimal balance = new BigDecimal("0.0");
//		BigDecimal ratioBalance = new BigDecimal("0.0");
//		if (memWallet != null) {
//			balance = memWallet.getAmount();
//			// 分成比例 / 100 = 可以分到的钱
//			ratioBalance = balance.multiply(memFamily.getGiftRatio()).divide(new BigDecimal("100"));
//		}
//		withdrawIndexVO.setBalance(balance);
//		withdrawIndexVO.setRatioBalance(ratioBalance);
//		


		withdrawIndexVO.setAccount("jiazuaa");
		withdrawIndexVO.setGiftRatio(new BigDecimal("50"));
		withdrawIndexVO.setBankAccountName("张三");
		withdrawIndexVO.setBankName("建设银行");
		withdrawIndexVO.setBankLogo("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/10bc39255c494f79872c2d20510a9576.png");
		withdrawIndexVO.setBankAccid(459L);
		withdrawIndexVO.setBankAccountNo("156191132497162");
		withdrawIndexVO.setBankCode("ICBC");
		withdrawIndexVO.setBalance(new BigDecimal("200"));
		withdrawIndexVO.setRatioBalance(new BigDecimal("100"));

		return withdrawIndexVO;
	}


}
