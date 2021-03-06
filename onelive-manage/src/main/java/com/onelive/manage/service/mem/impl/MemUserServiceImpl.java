package com.onelive.manage.service.mem.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.InviteCodeConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.dto.mem.UserGroupCountDTO;
import com.onelive.common.model.req.mem.MemUserListReq;
import com.onelive.common.model.req.mem.MemUserUpdateFrozenReq;
import com.onelive.common.model.req.report.UserReportReq;
import com.onelive.common.model.vo.mem.MemUserListVO;
import com.onelive.common.model.vo.report.UserReportVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.master.mem.MemLevelLinkMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletMapper;
import com.onelive.common.mybatis.mapper.slave.agent.SlaveAgentForbiddenProfitMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysCountryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.InviteCodeUtils;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.manage.service.agent.AgentInviteCodeService;
import com.onelive.manage.service.agent.AgentInviteRecordService;
import com.onelive.manage.service.mem.MemUserService;
import com.onelive.manage.service.mem.MemWalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
@Service
public class MemUserServiceImpl extends ServiceImpl<MemUserMapper, MemUser> implements MemUserService {

    @Resource
    private SlaveMemUserMapper slaveMemUserMapper;
    @Resource
    private SlaveSysCountryMapper slaveSysCountryMapper;
    @Resource
    private MemWalletMapper memWalletMapper;
    @Resource
    private MemLevelLinkMapper memLevelLinkMapper;
    
    @Resource
    private MemWalletService memWalletService;
    
    @Resource
    private MemUserService memUserService;

    @Resource
    private AgentInviteCodeService agentInviteCodeService;

    @Resource
    private SlaveAgentForbiddenProfitMapper slaveAgentForbiddenProfitMapper;
    
    @Resource
    private AgentInviteRecordService agentInviteRecordService;

    @Override
    public Boolean isExistByAccount(String userAccount) {
        QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUser::getUserAccount, userAccount);
        queryWrapper.lambda().eq(MemUser::getMerchantCode, LoginInfoUtil.getMerchantCode());
//        queryWrapper.lambda().eq(MemUser::getUserType, 0);
//        queryWrapper.lambda().in(MemUser::getUserType, Arrays.asList(0, 1));
        Integer count = slaveMemUserMapper.selectCount(queryWrapper);
        if (count > 0) return true;
        return false;
    }

    @Override
    public MemUser saveUserAnchor(UserAnchorDTO dto) throws Exception {
        //????????????????????????
        if (dto == null
                || StringUtils.isBlank(dto.getUserAccount())
                || StringUtils.isBlank(dto.getMobilePhone())
                || StringUtils.isBlank(dto.getRegisterAreaCode())
                || StringUtils.isBlank(dto.getPassword())) {
            throw new BusinessException(StatusCode.REGISTER_ANCHOR_LOSE);
        }
        //????????????????????????
        if (isExistByAccount(dto.getUserAccount())) {
            throw new BusinessException(StatusCode.REGISTER_EXISTS_ANCHOR);
        }

        MemUser memUser = new MemUser();
        memUser.setUserType(dto.getUserType());
        memUser.setSalt(SecurityUtils.getRandomSalt());
        memUser.setPassword(SecurityUtils.MD5SaltEncrypt(memUser.getPassword(), memUser.getSalt()));
        if (StringUtils.isBlank(dto.getNickName())) {
            memUser.setNickName(InviteCodeUtils.nickName());
        } else {
            memUser.setNickName(dto.getNickName());
        }
        memUser.setMobilePhone(dto.getMobilePhone());
        memUser.setIsFrozen(dto.getIsFrozen());
        memUser.setRemark(dto.getRemark());
        memUser.setAccno(InviteCodeUtils.accountCode());
        memUser.setUserAccount(dto.getUserAccount());
        memUser.setFocusNum(0);
        memUser.setFansNum(0);
        //??????????????????-1
        memUser.setUserLevel(-1);
        memUser.setAvatar(dto.getAvatar());
        memUser.setRegisterIp(LoginInfoUtil.getIp());
        memUser.setRegisterTime(new Date());
        memUser.setRegisterDevice("web");
        memUser.setRegisterSource("manage");
        //??????country-id??????????????????
        
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry :: getCountryCode, dto.getRegisterCountryCode());
        SysCountry sysCountry = slaveSysCountryMapper.selectOne(queryWrapper);
        memUser.setRegisterArea(sysCountry.getZhName());
        memUser.setRegisterAreaCode(sysCountry.getAreaCode());
        memUser.setRegisterCountryCode(sysCountry.getCountryCode());
        memUser.setCreatedBy(LoginInfoUtil.getUserAccount());
        this.save(memUser);
        //???????????????????????????
        this.initRegister(memUser);
        return memUser;
    }
    
    /**
     * ???????????????????????????
     *
     * @param memUser
     */
    private void dealWithInviteCode(MemUser memUser) {
        //???????????????, ????????????????????????,????????????????????????,
        boolean isInvite = false; //??????????????????????????????
        AgentInviteRecord agentInviteRecord = new AgentInviteRecord();
        agentInviteRecord.setUserId(memUser.getId());
        String inviteCode = LoginInfoUtil.getInviteCode();
        AgentInviteCode inviteCodeObj = null;
        MemUser inviteUser = null;
        int level = 1; //??????????????????
        Long rootUserId = memUser.getId();//????????????????????????
        if (StringUtils.isNotBlank(inviteCode)) {
            //???????????????
            inviteCodeObj = agentInviteCodeService.getOneByInviteCode(inviteCode);

            //???????????????????????????
            if (inviteCodeObj == null || !inviteCodeObj.getStatus()) {
                throw new BusinessException(StatusCode.INVALID_INVITE_CODE);//??????????????????
            }

            //???????????????????????????
            inviteUser = memUserService.getById(inviteCodeObj.getUserId());

            //????????????????????????
            if (inviteUser == null || inviteUser.getIsFrozen()) {
                throw new BusinessException(StatusCode.INVALID_INVITE_CODE);//??????????????????
            }
            level = inviteCodeObj.getAgentLevel() + 1;
            rootUserId = inviteCodeObj.getRootUserId();
            agentInviteRecord.setInviteCode(inviteCode);
            agentInviteRecord.setInviteUserId(inviteCodeObj.getUserId());
            isInvite = true;
        } else {
            //?????????????????????, ?????????????????????
            agentInviteRecord.setInviteCode(InviteCodeConstants.INVITE_CODE_PLATFORM);
            agentInviteRecord.setInviteUserId(0L);
        }
        agentInviteRecordService.save(agentInviteRecord);

        //???????????????????????????
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

                //??????????????????????????????,??????????????????
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
                break;//????????????????????????????????????
            } catch (Exception e) {
                e.printStackTrace();
                count++;
                //?????????????????????, ??????
                //????????????5???, ??????
                if (count >= 5) {
                    throw new BusinessException(StatusCode.CREATE_INVITE_CODE_FAILURE);
                }
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param req
     * @return
     */
    @Override
    public PageInfo<MemUserListVO> getList(MemUserListReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<MemUserListVO> list = slaveMemUserMapper.getList(req);
        PageInfo<MemUserListVO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void updateFrozenUsers(MemUserUpdateFrozenReq req) {
        slaveMemUserMapper.updateFrozenUsers(req);
    }


    /**
     * ?????????????????????
     *
     * @param memUser
     */
    private void initRegister(MemUser memUser) {
        //???????????????
        MemWallet wallet = new MemWallet();
        wallet.setAccount(memUser.getUserAccount());
        wallet.setCreateUser(wallet.getAccount());
        wallet.setWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
        wallet.setUserId(memUser.getId());
        memWalletMapper.insert(wallet);

        //?????????????????????
        MemLevelLink levelLink = new MemLevelLink();
        //??????????????????1???????????????id???1????????????-1 ??????
        levelLink.setLevelId(-1L);
        levelLink.setUserId(memUser.getId());
        memLevelLinkMapper.insert(levelLink);
        // ?????????
        dealWithInviteCode(memUser);
    }


    @Override
    public MemUser queryByAccount(String account) {
        QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUser::getUserAccount, account);
        return slaveMemUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateCommissionUsers(ArrayList<Long> userIds, Boolean isCommission) {
        slaveMemUserMapper.updateCommissionUsers(userIds, isCommission);
    }

    /* ?????? Javadoc???
     * @see com.onelive.manage.service.mem.MemUserService#getRiskList(java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public PageInfo<MemUser> getRiskList(String startDate, String endDate, Long countryId, String equip,
                                         String userAccount, String ip, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<MemUser> wrapper = Wrappers.<MemUser>lambdaQuery()
                .like(StrUtil.isNotBlank(equip), MemUser::getRegisterDevice, equip)
                .like(StrUtil.isNotBlank(userAccount), MemUser::getUserAccount, userAccount)
                .ge(StrUtil.isNotBlank(startDate), MemUser::getLastLoginTime, DateUtil.parse(startDate))
                .le(StrUtil.isNotBlank(endDate), MemUser::getLastLoginTime, DateUtil.parse(endDate))
                .eq(StrUtil.isNotBlank(ip), MemUser::getLastLoginIp, ip)
                .eq(countryId != null, MemUser::getCountryId, countryId)
                .eq(MemUser::getMerchantCode, LoginInfoUtil.getMerchantCode());

        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> slaveMemUserMapper.selectList(wrapper));
    }

    @Override
    public List<UserGroupCountDTO> getByUserGroupId(List<Long> groupIds,Long currencyId) {
        List<UserGroupCountDTO> userGroupCount = slaveMemUserMapper.getByUserGroupId(groupIds,currencyId);
        return userGroupCount;
    }

    @Override
    public PageInfo<UserReportVO> queryUserReport(UserReportReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<UserReportVO> list = slaveMemUserMapper.queryUserReport(req);
        return new PageInfo<>(list);
    }

    @Override
	public MemUser queryById(Long userId) {
		return slaveMemUserMapper.selectById(userId);
	}

}
