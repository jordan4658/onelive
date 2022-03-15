package com.onelive.api.modules.mem.business;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.ob.resp.BalanceResp;
import com.onelive.api.modules.game.business.GameBusiness;
import com.onelive.api.service.game.GameRecordService;
import com.onelive.api.service.game.GameThirdService;
import com.onelive.api.service.game.GameUserCenterService;
import com.onelive.api.service.live.LiveBagService;
import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.mem.*;
import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.api.service.sys.SysAdvActivityService;
import com.onelive.api.service.sys.SysCountryAddrService;
import com.onelive.api.service.sys.SysCountryService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.dto.platformConfig.AccountConfigDto;
import com.onelive.common.model.dto.platformConfig.NickNameFiterDto;
import com.onelive.common.model.req.game.GameRecordDetailReq;
import com.onelive.common.model.req.game.GameTransferReq;
import com.onelive.common.model.req.game.GameWalletReq;
import com.onelive.common.model.req.mem.MemUserGameRecordListReq;
import com.onelive.common.model.req.mem.MemUserInfoEditReq;
import com.onelive.common.model.req.mem.UserWalletChangeReq;
import com.onelive.common.model.req.mem.usercenter.ActivityListDTO;
import com.onelive.common.model.req.mem.usercenter.ActivityListReq;
import com.onelive.common.model.vo.game.GameRecordDetailVO;
import com.onelive.common.model.vo.game.GameRecordListVO;
import com.onelive.common.model.vo.game.GameRecordVO;
import com.onelive.common.model.vo.live.AppLiveBagVO;
import com.onelive.common.model.vo.live.AppUserLiveBagVO;
import com.onelive.common.model.vo.mem.*;
import com.onelive.common.model.vo.sys.AppCountryAddrVO;
import com.onelive.common.model.vo.sys.SysActivityInfoVO;
import com.onelive.common.model.vo.sys.SysActivityListVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.PageInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemUserCenterBusiness {

    @Resource
    private MemUserService memUserService;
    @Resource
    private GameThirdService gameThirdService;
    @Resource
    private MemUserOccupationService memUserOccupationService;
    @Resource
    private MemLevelVipService memLevelVipService;
    @Resource
    private MemUserEmpiricalValueService memUserEmpiricalValueService;
    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private SysAdvActivityService sysAdvActivityService;
    @Resource
    private LiveBagService liveBagService;
    @Resource
    private MemUserBagService memUserBagService;
    @Resource
    private SysCountryAddrService sysCountryAddrService;
    @Resource
    private LiveGiftLogService liveGiftLogService;
    @Resource
    private MemFocusUserService memFocusUserService;
    @Resource
    private SysParameterService sysParameterService;
    @Resource
    private GameUserCenterService gameUserCenterService;
    @Resource
    private GameRecordService gameRecordService;
    @Resource
    private MemUserMsgRepushRecordService repushRecordService;
    @Resource
    private MemWalletService memWalletService;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;
    @Resource
    private GameBusiness gameBusiness;
    @Resource
    private MemWalletLangService memWalletLangService;
    @Resource
    private LiveStudioListService liveStudioListService;


    /**
     * 查询用户基本资料
     *
     * @return
     */
    public MemUserInfoVO getUserInfo() {

        Long userId = LoginInfoUtil.getUserId();

        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);

        // TODO 火力值缓存，送礼后更新
        BigDecimal giftTatol = new BigDecimal("0");
        // 主播查询火力值
        if (user.getUserType() == 2) {
            giftTatol = liveGiftLogService.selectUserSumByhostId(null, userId);
        }
        // 用户查询送出礼物火力值
        else {
            giftTatol = liveGiftLogService.selectUserSumByhostId(userId, null);
        }

        MemUserInfoVO vo = new MemUserInfoVO();
        vo.setFirepower(giftTatol.multiply(new BigDecimal("10")).setScale(0, BigDecimal.ROUND_DOWN).toString());
        BeanCopyUtil.copyProperties(user, vo);
        vo.setIsPayPassword(false);
        if (StringUtils.isNotBlank(user.getPayPassword())) {
            vo.setIsPayPassword(true);
        }
        //用户关注数量
        QueryWrapper<MemFocusUser> queryWrapperFocus = new QueryWrapper<>();
        queryWrapperFocus.lambda().eq(MemFocusUser::getUserId, userId);
        vo.setFocusNum(memFocusUserService.count(queryWrapperFocus));
        // 粉丝数量：被关注数量
        QueryWrapper<MemFocusUser> queryWrapperFans = new QueryWrapper<>();
        queryWrapperFans.lambda().eq(MemFocusUser::getFocusId, userId);
        vo.setFansNum(memFocusUserService.count(queryWrapperFans));

        if (vo.getUserType() == 2) {
            //查询主播开播状态
            QueryWrapper<LiveStudioList> studioListQueryWrapper = new QueryWrapper<>();
            studioListQueryWrapper.lambda().eq(LiveStudioList::getUserId, vo.getId());
            studioListQueryWrapper.lambda().last(" limit 1");
            LiveStudioList studioList = liveStudioListService.getOne(studioListQueryWrapper);
            if (studioList != null && studioList.getStudioStatus() == 1) {
                vo.setIsOpenLive(true);
            }
        }
        return vo;
    }


    /**
     * 更新用户昵称
     *
     * @param nickName
     */
    public void updateNickName(String nickName) {
        Long userId = LoginInfoUtil.getUserId();

        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);

        // 判断用户当前是否可修改昵称
        if (user.getNickNameStatus()) {
            // 用户昵称敏感字排查
            SysParameter byCode = sysParameterService.getByCode(SysParameterConstants.NICK_NAME_FILTER);
            if (byCode != null) {
                NickNameFiterDto nickNameFiterDto = JSON.parseObject(byCode.getParamValue(), NickNameFiterDto.class);
                List<String> splitToList = Splitter.on(",").trimResults().splitToList(nickNameFiterDto.getContext());
                for (String nickNameFiter : splitToList) {
                    if (nickName.contains(nickNameFiter)) {
                        throw new BusinessException(StatusCode.NICK_NAME_KILL_ILLEGAL);
                    }
                }
            }
            user.setNickName(nickName);
            user.setNickNameStatus(false);
            memUserService.updateById(user);
        } else {
            throw new BusinessException(StatusCode.MODIFY_NICK_NAME_NOT_FREE);
        }
    }

    /**
     * 更新用户个性签名
     *
     * @param signature
     */
    public void updateSignature(String signature) {
        Long userId = LoginInfoUtil.getUserId();

        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);
        user.setPersonalSignature(signature);
        memUserService.updateById(user);
    }

    /**
     * 更新用户信息
     *
     * @param req
     */
    public MemUserInfoVO updateUserInfo(MemUserInfoEditReq req) {
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);
        // 用户等级不够不可以修改头像
        SysParameter accountConfig = sysParameterService.getByCode(SysParameterConstants.ACCOUNT_CONFIG);

        if (accountConfig != null && StringUtils.isNotEmpty(accountConfig.getParamValue())) {
            AccountConfigDto accountConfigDto = JSON.parseObject(accountConfig.getParamValue(), AccountConfigDto.class);
            Integer avatarLevel = accountConfigDto.getAvatarLevel();
            Integer userLevel = user.getUserLevel();
            // 如果当前会员等级小于可以配置头像的等级
            if (userLevel < avatarLevel) {
                throw new BusinessException(StatusCode.AVATAR_LEVELNOT_ENOUGH);
            }
        }

        BeanCopyUtil.copyProperties(req, user);
        memUserService.updateById(user);
        MemUserInfoVO vo = new MemUserInfoVO();
        BeanCopyUtil.copyProperties(user, vo);
        return vo;
    }


    /**
     * 查询职业列表
     *
     * @return
     */
    public List<MemUserOccupationListVO> getUserOccupationList() {
        List<MemUserOccupation> list = memUserOccupationService.list();
        List<MemUserOccupationListVO> voList = BeanCopyUtil.copyCollection(list, MemUserOccupationListVO.class);
        return voList;
    }

    /**
     * 查询用户游戏记录
     *
     * @return
     */
    public GameRecordListVO getGameRecordList(MemUserGameRecordListReq req) {
        //查询日期 1今天 2昨天
        if (req == null || req.getQueryDate() == null || (req.getQueryDate() != 1 && req.getQueryDate() != 2)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //查询所有的第三方游戏
        QueryWrapper<GameThird> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThird::getIsDelete, false);
        List<GameThird> gameList = gameThirdService.list(queryWrapper);
        Map<String, List<GameThird>> gameMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(gameList)) {
            gameMap = gameList.stream().collect(Collectors.groupingBy(GameThird::getGameType));
        }

        GameRecordListVO<GameRecordVO> listVO = new GameRecordListVO<>();
        List<GameRecordVO> list = gameRecordService.sumUserBetRecordData(req);
        BigDecimal totalBetAmount = new BigDecimal("0");
        BigDecimal totalWinAmount = new BigDecimal("0");
        if (CollectionUtil.isNotEmpty(list)) {
            for (GameRecordVO vo : list) {
                totalBetAmount = totalBetAmount.add(vo.getBetAmount());
                BigDecimal winAmount = vo.getWinAmount();
                if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                    totalWinAmount = totalWinAmount.add(winAmount);
                }
                List<GameThird> games = gameMap.get(vo.getGameType());
                if (CollectionUtil.isNotEmpty(games)) {
                    for (GameThird game : games) {
                        if (game.getGameId().toString().equals(vo.getGameId())) {
                            vo.setIconUrl(game.getIcon());
                            break;
                        }
                    }
                }
            }
        }
        listVO.setTotalBetAmount(totalBetAmount);
        listVO.setTotalWinAmount(totalWinAmount);
        listVO.setList(list);
        return listVO;
    }


    /**
     * 查询游戏记录详情
     *
     * @param req
     * @return
     */
    public GameRecordListVO<GameRecordDetailVO> getGameRecordDetail(GameRecordDetailReq req) {
        //查询日期 1今天 2昨天
        if (req == null || req.getQueryDate() == null || (req.getQueryDate() != 1 && req.getQueryDate() != 2)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameRecordListVO<GameRecordDetailVO> listVO = new GameRecordListVO<>();
        List<GameRecordDetailVO> list = gameRecordService.sumUserBetRecordDetailData(req);
        BigDecimal totalBetAmount = new BigDecimal("0");
        BigDecimal totalWinAmount = new BigDecimal("0");
        if (CollectionUtil.isNotEmpty(list)) {
            for (GameRecordDetailVO vo : list) {
                totalBetAmount = totalBetAmount.add(vo.getBetAmount());
                BigDecimal winAmount = vo.getWinAmount();
                if (winAmount.compareTo(BigDecimal.ZERO) > 0) {
                    totalWinAmount = totalWinAmount.add(winAmount);
                }
            }
        }
        listVO.setTotalBetAmount(totalBetAmount);
        listVO.setTotalWinAmount(totalWinAmount);
        listVO.setList(list);
        return listVO;
    }

    /**
     * 查询用户等级信息
     *
     * @return
     */
    public MemUserLevelInfoVO getUserLevelInfo() {
        MemUserLevelInfoVO vo = new MemUserLevelInfoVO();
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        //查询用户当前等级信息
        MemUser user = memUserService.getById(userId);
        //查询用户的经验值
        MemUserEmpiricalValue userEmpiricalValue = memUserEmpiricalValueService.getByUserId(userId);
        BigDecimal empiricalValue = new BigDecimal("0");
        if (userEmpiricalValue != null) {
            empiricalValue = userEmpiricalValue.getEmpiricalValue();
        }

        //根据用户当前经验, 更新为最新等级状态
        Integer level = user.getUserLevel();
        //查询当前经验值可升级的最大等级
        MemLevelVip levelInfo = memLevelVipService.getMaxUpgradeableLevelByEmpirical(empiricalValue);
        if (levelInfo != null) {
            //条件满足
            if (level < levelInfo.getLevelWeight()) {
                user.setUserLevel(levelInfo.getLevelWeight());
                //更新数据库
                memUserService.updateById(user);
                repushRecordService.addRecordByUser(user);
            }
        }

        //查询用户的最新等级
        MemLevelVip userLevel = memLevelVipService.getVipLevelInfo(user.getUserLevel());
        if (userLevel != null) {
            BeanCopyUtil.copyProperties(userLevel, vo);
        }

        log.info("user.getUserLevel()=" + user.getUserLevel());
        log.info("userLevel=" + userLevel);
        BigDecimal currentLevelExp = userLevel.getPromotionRecharge();
        BigDecimal currentExp = empiricalValue.subtract(currentLevelExp);
        log.info("currentLevelExp=" + currentLevelExp);
        log.info("currentExp=" + currentExp);
        vo.setCurrentExp(currentExp);
        //计算下一级所需经验值
        MemLevelVip nextLevel = memLevelVipService.getVipLevelInfo(user.getUserLevel() + 1);
        if (nextLevel != null) {
            BigDecimal nextLevelExp = nextLevel.getPromotionRecharge();
            log.info("nextLevelExp=" + nextLevelExp);
            if (nextLevelExp != null && nextLevelExp.compareTo(BigDecimal.ZERO) > 0 && nextLevelExp.compareTo(empiricalValue) > 0) {
                BigDecimal needExp = nextLevelExp.subtract(currentLevelExp);
                vo.setNeedExp(needExp);
                BigDecimal progress = currentExp.divide(needExp, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
                vo.setProgress(progress.intValue());
            }
        }
        return vo;
    }

    /**
     * 获取游戏列表
     * @return
     */
/*
    public List<GameInfoVO> getGameList() {
        Wrapper<GameOutsideGames> queryWrapper=new QueryWrapper<>();
        List<GameOutsideGames> list = gameOutsideGamesService.getBaseMapper().selectList(queryWrapper);
        List<GameInfoVO> voList = BeanCopyUtil.copyCollection(list, GameInfoVO.class);
        return voList;
    }
*/

    /**
     * 获取广告列表
     *
     * @param req
     * @return
     */
    public SysActivityListVO getActivityList(ActivityListReq req) {
        SysActivityListVO vo = new SysActivityListVO();
        ActivityListDTO dto = new ActivityListDTO();
        dto.setActivityType(req.getActivityType());
        List<SysAdvActivity> list = sysAdvActivityService.listWithCountryAndLang(dto);
        List<SysActivityInfoVO> voList = BeanCopyUtil.copyCollection(list, SysActivityInfoVO.class);
        vo.setList(voList);
        return vo;
    }


    /**
     * 获取系统背包列表
     *
     * @return
     */
    public PageInfo<AppLiveBagVO> getBagList() {
        //List<LiveBag> list = liveBagService.getListWithLang();
        QueryWrapper<LiveBag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveBag::getIsFrozen, 0);
        List<LiveBag> list = liveBagService.list(queryWrapper);
        return PageInfoUtil.pageInfo2DTO(new PageInfo<>(list), AppLiveBagVO.class);
    }


    /**
     * 查询用户背包列表
     *
     * @return
     */
    public PageInfo<AppUserLiveBagVO> getUserBagList() {
        return memUserBagService.getUserBagList();
    }


    /**
     * 查询国家列表
     *
     * @return
     */
    public PageInfo<AppCountryAddrVO> getCountryList() {
        QueryWrapper<SysCountryAddr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountryAddr::getPid, 0L);
        return PageInfoUtil.pageInfo2DTO(new PageInfo<>(sysCountryAddrService.list(queryWrapper)), AppCountryAddrVO.class);
    }

    /**
     * 查询省市列表
     *
     * @return
     */
    public PageInfo<AppCountryAddrVO> getCityList(String lang) {
        QueryWrapper<SysCountryAddr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountryAddr::getLang, lang).ne(SysCountryAddr::getPid, 0L);
        return PageInfoUtil.pageInfo2DTO(new PageInfo<>(sysCountryAddrService.list(queryWrapper)), AppCountryAddrVO.class);
    }

    /**
     * 获取第三方游戏列表
     *
     * @return
     */
    public List<AppGameUserCenterListVO> getGameList() {
        String countryCode = LoginInfoUtil.getCountryCode();
        if (StrUtil.isBlank(countryCode)) {
            SysCountry defaultCountry = sysCountryService.getCountryByCountryCode(LoginInfoUtil.getLang());
            if (defaultCountry == null) {
                defaultCountry = sysCountryService.getCountryByCountryCode(LangConstants.LANG_VN);
            }
            if (defaultCountry != null) {
                countryCode = defaultCountry.getCountryCode();
            }
        }
        QueryWrapper<GameUserCenter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameUserCenter::getIsDelete, false).eq(GameUserCenter::getCountryCode, countryCode);
        List<GameUserCenter> list = gameUserCenterService.list(queryWrapper);
        return BeanCopyUtil.copyCollection(list, AppGameUserCenterListVO.class);
    }

    public List<MemWallet> getUserWalletList(String lang) {
        List<MemWallet> listWallet = memWalletService.getWalletListByMemId(LoginInfoUtil.getUserAccount());
        List<MemWalletLang> walletLangList = memWalletLangService.getWalletLangList();
        for (MemWallet wallet : listWallet) {
//            if (WalletTypeEnum.WOODEN_PLATFORM.getCode() != wallet.getWalletType()) {
//                GameWalletReq req = new GameWalletReq();
//                req.setGameWallet(wallet.getWalletType());
//                BalanceResp balanceResp = gameBusiness.queryWalletBalance(req);
//                wallet.setAmount(balanceResp.getBalance());
//            }
            //组装钱包名称
            for (MemWalletLang nameLang : walletLangList) {
                if (lang.equals(nameLang.getLang()) && nameLang.getWalletType().equals(wallet.getWalletType())) {
                    wallet.setWalletName(nameLang.getName());
                }
            }
        }
        return listWallet;
    }

    @Transactional
    public void userWalletChange(UserWalletChangeReq req) {
        if (req.getWalletType() == null) {
            throw new BusinessException("钱包类型为空！");
        }
        if (req.getOptionType() == null) {
            throw new BusinessException("操作类型为空");
        }
        if (req.getAmount() == null || req.getAmount().compareTo(new BigDecimal(0)) < 1) {
            throw new BusinessException("金额错误！");
        }
        //查询游戏钱包信息
        MemWallet wallet = memWalletService.getWalletByMemId(LoginInfoUtil.getUserId(), req.getWalletType());
        //查询游戏钱包余额
        GameWalletReq BalanceReq = new GameWalletReq();
        BalanceReq.setGameWallet(wallet.getWalletType());
        BalanceResp balanceResp = gameBusiness.queryWalletBalance(BalanceReq);
        //查询平台钱包信息
        MemWallet platformWallet = memWalletService.getWalletByMemId(LoginInfoUtil.getUserId(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (req.getOptionType() == 0) {
            //转入
            if (wallet == null || platformWallet == null) {
                throw new BusinessException("钱包未初始化！");
            }
            if (platformWallet.getAmount().compareTo(req.getAmount()) < 0) {
                throw new BusinessException("转入金额超出钱包余额！");
            }
            //进行账变
            MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
            memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
            memAccountChangeVO.setIsSilverBean(4);
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE38.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE38.getMsg());
            memAccountChangeVO.setPrice(req.getAmount());
            memAccountChangeVO.setFlowType(1);
            memAccountChangeVO.setTransferInWalletId(wallet.getWalletId());
            memAccountChangeVO.setTransferOutWalletId(platformWallet.getWalletId());
            BalanceChangeDTO balanceChangeDTO = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
            if (!balanceChangeDTO.getFlag()) {
                throw new BusinessException("账变失败！");
            }
            GameTransferReq gameTransferReq=new GameTransferReq();
            gameTransferReq.setAmount(req.getAmount().toString());
            gameTransferReq.setWalletType(wallet.getWalletType());
            gameBusiness.gameDeposit(gameTransferReq);
        } else {
            //转出
            if (wallet == null) {
                throw new BusinessException("钱包未初始化！");
            }
            if (balanceResp.getBalance().compareTo(req.getAmount()) < 0) {
                throw new BusinessException("转出金额超出钱包余额！");
            }
            //进行账变
            MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
            memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
            memAccountChangeVO.setIsSilverBean(4);
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE38.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE38.getMsg());
            memAccountChangeVO.setPrice(req.getAmount());
            memAccountChangeVO.setFlowType(2);
            memAccountChangeVO.setTransferInWalletId(platformWallet.getWalletId());
            memAccountChangeVO.setTransferOutWalletId(wallet.getWalletId());
            BalanceChangeDTO balanceChangeDTO = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
            if (!balanceChangeDTO.getFlag()) {
                throw new BusinessException("账变失败！");
            }
            GameTransferReq gameTransferReq=new GameTransferReq();
            gameTransferReq.setAmount(req.getAmount().toString());
            gameTransferReq.setWalletType(wallet.getWalletType());
            gameBusiness.gameWithdraw(gameTransferReq);
        }
    }
}
