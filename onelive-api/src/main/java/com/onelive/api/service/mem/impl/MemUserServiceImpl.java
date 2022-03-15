package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.api.service.mem.MemLevelVipService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.mem.UserAnchorDTO;
import com.onelive.common.model.req.live.LiveIndexSearchReq;
import com.onelive.common.model.vo.live.LiveAnchorInfoVO;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.mem.MemUserAnchorSearchListVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.master.mem.MemLevelLinkMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysCountryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.img.HeaderImgRamUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.InviteCodeUtils;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.common.utils.upload.AWSS3Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private SlaveMemUserMapper slaveMemUserMapper;

    @Resource
    private LocaleMessageSourceService localeMessageSourceService;

    @Resource
    private MemLevelVipService memLevelVipService;

    @Resource
    private MemFocusUserService memFocusUserService;

    @Autowired
    private LiveGiftLogService liveGiftLogService;

    @Resource
    private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;

    @Resource
    private SlaveSysCountryMapper slaveSysCountryMapper;

    @Resource
    private MemWalletMapper memWalletMapper;
    @Resource
    private MemLevelLinkMapper memLevelLinkMapper;

    @Resource
    private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

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
    public MemUser queryByAccount(String account) {
        QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUser::getUserAccount, account);
        return slaveMemUserMapper.selectOne(queryWrapper);
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
        queryWrapper.lambda().eq(SysCountry::getCountryCode, dto.getRegisterCountryCode());
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
//		MemWallet wallet = new MemWallet();
//		wallet.setBalance(BigDecimal.ZERO);
//		wallet.setUserId(memUser.getId());
//		wallet.setMerchantCode(memUser.getMerchantCode());
//		memWalletMapper.insert(wallet);

        MemWallet wallet = new MemWallet();
        wallet.setAccount(memUser.getUserAccount());
        wallet.setCreateUser(wallet.getAccount());
        wallet.setWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
        wallet.setUserId(memUser.getId());
        memWalletMapper.insert(wallet);

        // 初始化用户等级
        MemLevelLink levelLink = new MemLevelLink();
        // 初始化数据，1对应数据库id为1，等级是-1 主播
        levelLink.setLevelId(1l);
        levelLink.setUserId(memUser.getId());
        memLevelLinkMapper.insert(levelLink);

        // 。。。。后续再加，例如邀请码
    }

    /**
     * 多条件搜索主播/用户信息
     * 模糊匹配:匹配accno,用户昵称,直播间标题  精确匹配：房间号， userid，房间号
     *
     * @return
     */
    @Override
    public List<MemUserAnchorSearchListVO> search(LiveIndexSearchReq req) {
        List<MemUserAnchorSearchListVO> searchAnchorList = slaveMemUserMapper.conditionSearch(req.getContext(), LoginInfoUtil.getMerchantCode());
        return searchAnchorList;
    }

    /**
     * 根据用户id查询信息，详情名片
     */
    @Override
    public LiveUserDetailVO getUserDetailInfo(Long userId) {
        LiveUserDetailVO vo = new LiveUserDetailVO();
        // 获取用户信息
        MemUser memUser = this.getById(userId);
        BeanCopyUtil.copyProperties(memUser, vo);
        vo.setUserId(memUser.getId().toString());
        String personalSignature = memUser.getPersonalSignature();
        vo.setArea(ApiBusinessRedisUtils.getMemCurrentArea(memUser.getId()));
        if (StringUtils.isBlank(personalSignature)) {
            vo.setPersonalSignature(localeMessageSourceService.getMessage("LIVE_REMARK_BLANK"));
        } else {
            vo.setPersonalSignature(personalSignature);
        }
        vo.setRegisterArea(memUser.getRegisterArea());
        vo.setIsFocus(memFocusUserService.isExistFocus(memUser.getId()));

        // 获取等级信息  //TODO 后面从Redis中取数据
        MemLevelVip vip = memLevelVipService.getVipLevelInfo(memUser.getUserLevel());
        if (vip != null) {
            vo.setLevelName(vip.getLevelName());
            vo.setLevelIcon(AWSS3Util.getAbsoluteUrl(vip.getLevelIcon()));
        }
        if (vo.getUserType() == 2) {
            //查询主播开播状态
            QueryWrapper<LiveStudioList> studioListQueryWrapper = new QueryWrapper<>();
            studioListQueryWrapper.lambda().eq(LiveStudioList::getUserId, vo.getUserId());
            studioListQueryWrapper.lambda().last(" limit 1");
            LiveStudioList studioList = slaveLiveStudioListMapper.selectOne(studioListQueryWrapper);
            if (studioList != null && studioList.getStudioStatus() == 1) {
                vo.setIsOpenLive(true);
            }
        }
        // 粉丝数量：被关注数量
        QueryWrapper<MemFocusUser> queryWrapperFans = new QueryWrapper<>();
        queryWrapperFans.lambda().eq(MemFocusUser::getFocusId, userId);
        vo.setFansNum(memFocusUserService.count(queryWrapperFans));

        // 关注数量
        QueryWrapper<MemFocusUser> queryWrapperFocus = new QueryWrapper<>();
        queryWrapperFocus.lambda().eq(MemFocusUser::getUserId, userId);
        vo.setFocusNum(memFocusUserService.count(queryWrapperFocus));
        // TODO 火力值缓存，送礼后更新
        BigDecimal giftTatol = new BigDecimal("0");
        // 主播查询火力值
        if (memUser.getUserType() == 2) {
            giftTatol = liveGiftLogService.selectUserSumByhostId(null, userId);
        }
        // 用户查询送出礼物火力值
        else {
            giftTatol = liveGiftLogService.selectUserSumByhostId(userId, null);
        }
        vo.setFirepower(giftTatol.multiply(new BigDecimal("10")).setScale(0, BigDecimal.ROUND_DOWN).toString());
        // TODO 获取背包
        return vo;
    }

    /**
     * 根据用户id查询信息，用户聊天信息返回，用户列表展示，不需要详细数据
     */
    @Override
    public LiveUserDetailVO getUserDetailForList(Long userId) {
        LiveUserDetailVO vo = new LiveUserDetailVO();
        // 获取用户信息
        MemUser memUser = this.getById(userId);
        vo.setUserId(memUser.getId().toString());
        vo.setNickName(memUser.getNickName());
        String personalSignature = memUser.getPersonalSignature();
        vo.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
        vo.setArea(ApiBusinessRedisUtils.getMemCurrentArea(memUser.getId()));
        if (StringUtils.isBlank(personalSignature)) {
            vo.setPersonalSignature(localeMessageSourceService.getMessage("LIVE_REMARK_BLANK"));
        } else {
            vo.setPersonalSignature(personalSignature);
        }
        vo.setLevel(memUser.getUserLevel());
        vo.setUserAccount(memUser.getUserAccount());
        // 获取等级信息
        MemLevelVip vip = memLevelVipService.getVipLevelInfo(memUser.getUserLevel());
        if (vip != null) {
            vo.setLevelName(vip.getLevelName());
            vo.setLevelIcon(AWSS3Util.getAbsoluteUrl(vip.getLevelIcon()));
        }
        return vo;
    }

    /**
     * 查询主播信息
     *
     * @param anchorUserId
     * @return
     */
    @Override
    public LiveAnchorInfoVO getAnchorInfoById(Long anchorUserId) {
        return slaveMemUserMapper.getAnchorInfoById(anchorUserId, LoginInfoUtil.getUserId());
    }


    /**
     * 根据userType查询
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
