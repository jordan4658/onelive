package com.onelive.anchor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.AgentInviteCodeService;
import com.onelive.anchor.service.AgentInviteRecordService;
import com.onelive.anchor.service.MemUserService;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.constants.business.InviteCodeConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.master.mem.MemLevelLinkMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysCountryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.img.HeaderImgRamUtil;
import com.onelive.common.utils.others.InviteCodeUtils;
import com.onelive.common.utils.others.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
@Service
public class MemUserServiceImpl extends ServiceImpl<MemUserMapper, MemUser> implements MemUserService {

	@Resource
	private MemUserService memUserService;
	
	@Resource
	private SlaveMemUserMapper slaveMemUserMapper;

	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	@Resource
	private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;

	@Resource
	private SlaveSysCountryMapper slaveSysCountryMapper;

	@Resource
	private MemWalletMapper memWalletMapper;
	
	@Resource
	private MemLevelLinkMapper memLevelLinkMapper;
	
    @Resource
    private AgentInviteCodeService agentInviteCodeService;

    @Resource
    private AgentInviteRecordService agentInviteRecordService;

	@Override
	public Boolean isExistByAccount(String userAccount) {
		QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemUser::getUserAccount, userAccount);
		queryWrapper.lambda().eq(MemUser::getMerchantCode, LoginInfoUtil.getMerchantCode());
		queryWrapper.lambda().in(MemUser::getUserType, Arrays.asList(1, 2));
		Integer count = slaveMemUserMapper.selectCount(queryWrapper);
		if (count > 0)
			return true;
		return false;
	}

	@Override
	public MemUser queryByAccno(String accno) {
		QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemUser::getAccno, accno);
		queryWrapper.lambda().eq(MemUser::getMerchantCode, LoginInfoUtil.getMerchantCode());
		queryWrapper.lambda().in(MemUser::getUserType, Arrays.asList(1, 2)).last("limit 1 ");
		return slaveMemUserMapper.selectOne(queryWrapper);
	}

	@Override
	public MemUser saveUserAnchor(UserAnchorDTO dto) throws Exception {
		// 校验主播基本信息
		if (dto == null || StringUtils.isBlank(dto.getMobilePhone()) || StringUtils.isBlank(dto.getRegisterCountryCode()) || StringUtils.isBlank(dto.getUserAccount())
				|| StringUtils.isBlank(dto.getPassword())) {
			throw new BusinessException(StatusCode.REGISTER_ANCHOR_LOSE);
		}
		// 检验账号是否存在
		if (isExistByAccount(dto.getUserAccount())) {
			throw new BusinessException(StatusCode.REGISTER_EXISTS_ANCHOR);
		}

		MemUser memUser = new MemUser();
		Integer userType = dto.getUserType() == null ? 2 : dto.getUserType();
		memUser.setUserType(userType);
		memUser.setSalt(SecurityUtils.getRandomSalt());
		memUser.setPassword(SecurityUtils.MD5SaltEncrypt(memUser.getPassword(), memUser.getSalt()));
		if (StringUtils.isBlank(dto.getNickName())) {
			memUser.setNickName(InviteCodeUtils.nickName());
		} else {
			memUser.setNickName(dto.getNickName());
		}
		memUser.setIsFrozen(dto.getIsFrozen());
		memUser.setRemark(dto.getRemark());
		memUser.setAccno(InviteCodeUtils.accountCode());
		memUser.setUserAccount(dto.getUserAccount());
		memUser.setFocusNum(0);
		memUser.setFansNum(0);
		// 主播的等级是-1
		memUser.setUserLevel(-1);
		memUser.setAvatar(HeaderImgRamUtil.userHeadImg());
		memUser.setRegisterIp(LoginInfoUtil.getIp());
		memUser.setRegisterTime(new Date());
		memUser.setRegisterDevice("web");
		memUser.setRegisterSource("manage");
		// 通过country-id获取注册信息
		 QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry :: getCountryCode, dto.getRegisterCountryCode());
        SysCountry sysCountry = slaveSysCountryMapper.selectOne(queryWrapper);
		memUser.setRegisterArea(sysCountry.getZhName());
		memUser.setRegisterAreaCode(sysCountry.getAreaCode());
		memUser.setRegisterCountryCode(sysCountry.getCountryCode());
		memUser.setDefaultCountryCode(sysCountry.getCountryCode());
		memUser.setCreatedBy(LoginInfoUtil.getUserAccount());
		this.save(memUser);
		// 初始化用户其他信息
		this.initRegister(memUser);
		return memUser;
	}

	/**
	 * 初始化用户信息
	 *
	 * @param memUser
	 */
	private void initRegister(MemUser memUser) {
		// 初始化钱包
		MemWallet wallet = new MemWallet();
		wallet.setAccount(memUser.getUserAccount());
		wallet.setCreateUser(wallet.getAccount());
		wallet.setWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
		wallet.setUserId(memUser.getId());
		memWalletMapper.insert(wallet);

		// 初始化用户等级
		MemLevelLink levelLink = new MemLevelLink();
		// 初始化数据，1对应数据库id为1，等级是-1 主播
		levelLink.setLevelId(-1L);
		levelLink.setUserId(memUser.getId());
		memLevelLinkMapper.insert(levelLink);

		// 邀请码
        dealWithInviteCode(memUser);
	}
	
	/**
     * 处理邀请码相关逻辑
     *
     * @param memUser
     */
    private void dealWithInviteCode(MemUser memUser) {
        //注册成功后, 检查是否有邀请码,创建邀请关系记录,
        boolean isInvite = false; //标记是否被邀请注册的
        AgentInviteRecord agentInviteRecord = new AgentInviteRecord();
        agentInviteRecord.setUserId(memUser.getId());
        String inviteCode = LoginInfoUtil.getInviteCode();
        AgentInviteCode inviteCodeObj = null;
        MemUser inviteUser = null;
        int level = 1; //默认代理等级
        Long rootUserId = memUser.getId();//默认自己是根用户
        if (StringUtils.isNotBlank(inviteCode)) {
            //查询邀请码
            inviteCodeObj = agentInviteCodeService.getOneByInviteCode(inviteCode);

            //邀请码状态是否正常
            if (inviteCodeObj == null || !inviteCodeObj.getStatus()) {
                throw new BusinessException(StatusCode.INVALID_INVITE_CODE);//无效的邀请码
            }

            //查询邀请码所属用户
            inviteUser = memUserService.getById(inviteCodeObj.getUserId());

            //用户状态是否正常
            if (inviteUser == null || inviteUser.getIsFrozen()) {
                throw new BusinessException(StatusCode.INVALID_INVITE_CODE);//无效的邀请码
            }
            level = inviteCodeObj.getAgentLevel() + 1;
            rootUserId = inviteCodeObj.getRootUserId();
            agentInviteRecord.setInviteCode(inviteCode);
            agentInviteRecord.setInviteUserId(inviteCodeObj.getUserId());
            isInvite = true;
        } else {
            //如果没有邀请码, 记录为系统邀请
            agentInviteRecord.setInviteCode(InviteCodeConstants.INVITE_CODE_PLATFORM);
            agentInviteRecord.setInviteUserId(0L);
        }
        agentInviteRecordService.save(agentInviteRecord);

        //生成新用户的邀请码
        int count = 0;
        while (true) {
            try {
                String code = InviteCodeUtils.inviteCode();
                AgentInviteCode agentInviteCode = new AgentInviteCode();
                agentInviteCode.setUserId(memUser.getId());
                agentInviteCode.setUserAccount(memUser.getUserAccount());
                agentInviteCode.setInviteCode(code);
                agentInviteCode.setIsSys(true);
                agentInviteCode.setIsAutoCreate(true);

                //如果不是被邀请注册的,设置默认信息
                if (isInvite) {
                    agentInviteCode.setInviteUserId(inviteUser.getId());
                    agentInviteCode.setInviteUserAccount(inviteUser.getUserAccount());
                } else {
                    agentInviteCode.setInviteUserId(0L);
                    agentInviteCode.setInviteUserAccount(InviteCodeConstants.INVITE_CODE_PLATFORM);
                }
                agentInviteCode.setAgentLevel(level);
                agentInviteCode.setRootUserId(rootUserId);

                agentInviteCodeService.save(agentInviteCode);
                break;//正常添加邀请码后结束循环
            } catch (Exception e) {
                e.printStackTrace();
                count++;
                //生成邀请码失败, 重试
                //如果超过5次, 失败
                if (count >= 5) {
                    throw new BusinessException(StatusCode.CREATE_INVITE_CODE_FAILURE);
                }
            }
        }
    }
	
	/**
	 * 	根据userType查询
	 */
	@Override
	public MemUser queryByAccount(String userAccount, String merchantCode, Integer userType) {
		QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemUser::getUserAccount, userAccount);
		queryWrapper.lambda().eq(MemUser::getMerchantCode, merchantCode);
		queryWrapper.lambda().eq(MemUser::getUserType, userType).last("limit 1 ");
		return slaveMemUserMapper.selectOne(queryWrapper);
	}

	@Override
	public MemUser queryById(Long userId) {
		return slaveMemUserMapper.selectById(userId);
	}

}
