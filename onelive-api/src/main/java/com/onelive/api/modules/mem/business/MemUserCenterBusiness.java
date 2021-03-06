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
     * ????????????????????????
     *
     * @return
     */
    public MemUserInfoVO getUserInfo() {

        Long userId = LoginInfoUtil.getUserId();

        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);

        // TODO ?????????????????????????????????
        BigDecimal giftTatol = new BigDecimal("0");
        // ?????????????????????
        if (user.getUserType() == 2) {
            giftTatol = liveGiftLogService.selectUserSumByhostId(null, userId);
        }
        // ?????????????????????????????????
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
        //??????????????????
        QueryWrapper<MemFocusUser> queryWrapperFocus = new QueryWrapper<>();
        queryWrapperFocus.lambda().eq(MemFocusUser::getUserId, userId);
        vo.setFocusNum(memFocusUserService.count(queryWrapperFocus));
        // ??????????????????????????????
        QueryWrapper<MemFocusUser> queryWrapperFans = new QueryWrapper<>();
        queryWrapperFans.lambda().eq(MemFocusUser::getFocusId, userId);
        vo.setFansNum(memFocusUserService.count(queryWrapperFans));

        if (vo.getUserType() == 2) {
            //????????????????????????
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
     * ??????????????????
     *
     * @param nickName
     */
    public void updateNickName(String nickName) {
        Long userId = LoginInfoUtil.getUserId();

        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);

        // ???????????????????????????????????????
        if (user.getNickNameStatus()) {
            // ???????????????????????????
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
     * ????????????????????????
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
     * ??????????????????
     *
     * @param req
     */
    public MemUserInfoVO updateUserInfo(MemUserInfoEditReq req) {
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        MemUser user = memUserService.getById(userId);
        // ???????????????????????????????????????
        SysParameter accountConfig = sysParameterService.getByCode(SysParameterConstants.ACCOUNT_CONFIG);

        if (accountConfig != null && StringUtils.isNotEmpty(accountConfig.getParamValue())) {
            AccountConfigDto accountConfigDto = JSON.parseObject(accountConfig.getParamValue(), AccountConfigDto.class);
            Integer avatarLevel = accountConfigDto.getAvatarLevel();
            Integer userLevel = user.getUserLevel();
            // ?????????????????????????????????????????????????????????
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
     * ??????????????????
     *
     * @return
     */
    public List<MemUserOccupationListVO> getUserOccupationList() {
        List<MemUserOccupation> list = memUserOccupationService.list();
        List<MemUserOccupationListVO> voList = BeanCopyUtil.copyCollection(list, MemUserOccupationListVO.class);
        return voList;
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    public GameRecordListVO getGameRecordList(MemUserGameRecordListReq req) {
        //???????????? 1?????? 2??????
        if (req == null || req.getQueryDate() == null || (req.getQueryDate() != 1 && req.getQueryDate() != 2)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //??????????????????????????????
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
     * ????????????????????????
     *
     * @param req
     * @return
     */
    public GameRecordListVO<GameRecordDetailVO> getGameRecordDetail(GameRecordDetailReq req) {
        //???????????? 1?????? 2??????
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
     * ????????????????????????
     *
     * @return
     */
    public MemUserLevelInfoVO getUserLevelInfo() {
        MemUserLevelInfoVO vo = new MemUserLevelInfoVO();
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        //??????????????????????????????
        MemUser user = memUserService.getById(userId);
        //????????????????????????
        MemUserEmpiricalValue userEmpiricalValue = memUserEmpiricalValueService.getByUserId(userId);
        BigDecimal empiricalValue = new BigDecimal("0");
        if (userEmpiricalValue != null) {
            empiricalValue = userEmpiricalValue.getEmpiricalValue();
        }

        //????????????????????????, ???????????????????????????
        Integer level = user.getUserLevel();
        //?????????????????????????????????????????????
        MemLevelVip levelInfo = memLevelVipService.getMaxUpgradeableLevelByEmpirical(empiricalValue);
        if (levelInfo != null) {
            //????????????
            if (level < levelInfo.getLevelWeight()) {
                user.setUserLevel(levelInfo.getLevelWeight());
                //???????????????
                memUserService.updateById(user);
                repushRecordService.addRecordByUser(user);
            }
        }

        //???????????????????????????
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
        //??????????????????????????????
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
     * ??????????????????
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
     * ??????????????????
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
     * ????????????????????????
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
     * ????????????????????????
     *
     * @return
     */
    public PageInfo<AppUserLiveBagVO> getUserBagList() {
        return memUserBagService.getUserBagList();
    }


    /**
     * ??????????????????
     *
     * @return
     */
    public PageInfo<AppCountryAddrVO> getCountryList() {
        QueryWrapper<SysCountryAddr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountryAddr::getPid, 0L);
        return PageInfoUtil.pageInfo2DTO(new PageInfo<>(sysCountryAddrService.list(queryWrapper)), AppCountryAddrVO.class);
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public PageInfo<AppCountryAddrVO> getCityList(String lang) {
        QueryWrapper<SysCountryAddr> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountryAddr::getLang, lang).ne(SysCountryAddr::getPid, 0L);
        return PageInfoUtil.pageInfo2DTO(new PageInfo<>(sysCountryAddrService.list(queryWrapper)), AppCountryAddrVO.class);
    }

    /**
     * ???????????????????????????
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
            //??????????????????
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
            throw new BusinessException("?????????????????????");
        }
        if (req.getOptionType() == null) {
            throw new BusinessException("??????????????????");
        }
        if (req.getAmount() == null || req.getAmount().compareTo(new BigDecimal(0)) < 1) {
            throw new BusinessException("???????????????");
        }
        //????????????????????????
        MemWallet wallet = memWalletService.getWalletByMemId(LoginInfoUtil.getUserId(), req.getWalletType());
        //????????????????????????
        GameWalletReq BalanceReq = new GameWalletReq();
        BalanceReq.setGameWallet(wallet.getWalletType());
        BalanceResp balanceResp = gameBusiness.queryWalletBalance(BalanceReq);
        //????????????????????????
        MemWallet platformWallet = memWalletService.getWalletByMemId(LoginInfoUtil.getUserId(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (req.getOptionType() == 0) {
            //??????
            if (wallet == null || platformWallet == null) {
                throw new BusinessException("?????????????????????");
            }
            if (platformWallet.getAmount().compareTo(req.getAmount()) < 0) {
                throw new BusinessException("?????????????????????????????????");
            }
            //????????????
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
                throw new BusinessException("???????????????");
            }
            GameTransferReq gameTransferReq=new GameTransferReq();
            gameTransferReq.setAmount(req.getAmount().toString());
            gameTransferReq.setWalletType(wallet.getWalletType());
            gameBusiness.gameDeposit(gameTransferReq);
        } else {
            //??????
            if (wallet == null) {
                throw new BusinessException("?????????????????????");
            }
            if (balanceResp.getBalance().compareTo(req.getAmount()) < 0) {
                throw new BusinessException("?????????????????????????????????");
            }
            //????????????
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
                throw new BusinessException("???????????????");
            }
            GameTransferReq gameTransferReq=new GameTransferReq();
            gameTransferReq.setAmount(req.getAmount().toString());
            gameTransferReq.setWalletType(wallet.getWalletType());
            gameBusiness.gameWithdraw(gameTransferReq);
        }
    }
}
