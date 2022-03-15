package com.onelive.api.modules.game.business;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ob.constant.DeviceTypeEnum;
import com.ob.constant.GameTypeConstant;
import com.ob.constant.LanguageEnum;
import com.ob.constant.VersionEnum;
import com.ob.internal.common.util.GsonUtil;
import com.ob.internal.common.util.IPUtil;
import com.ob.req.*;
import com.ob.resp.*;
import com.ob.service.GameService;
import com.onelive.api.service.game.*;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.api.service.sys.SysCountryService;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.*;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.map.GameLanguage;
import com.onelive.common.model.dto.game.GameZRModifyBetLimitDTO;
import com.onelive.common.model.req.game.*;
import com.onelive.common.model.vo.game.GameLoginVO;
import com.onelive.common.model.vo.game.app.AppGameCenterGameListVO;
import com.onelive.common.model.vo.game.app.AppGameTagListDataVO;
import com.onelive.common.model.vo.game.app.AppGameTagListVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 第三方游戏业务类
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameBusiness {

    @Resource
    private GameZRBusiness gameZRBusiness;
    @Resource
    private MemUserService memUserService;
    @Resource
    private MemWalletService memWalletService;
    @Resource
    private GameUserService gameUserService;
    @Resource
    private GameThirdService gameThirdService;
    @Resource
    private GameCategoryService gameCategoryService;
    @Resource
    private GameTransferRecordService transferRecordService;
    @Resource
    private GameTagService gameTagService;
    @Resource
    private GameTagLangService gameTagLangService;
    @Resource
    private GameCenterGameService gameCenterGameService;
    @Resource
    private SysCountryService sysCountryService;

    /**
     * 登陆第三方游戏
     */
    public GameLoginVO login(GameLoginReq req) {

        if(req==null || StrUtil.isBlank(req.getGameCode())){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameThird game = gameThirdService.getByCode(req.getGameCode());
        if (game == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //查询游戏关联的分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getCategoryId, game.getCategoryId()).last(" LIMIT 1");
        GameCategory gameCategory = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (gameCategory == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //查询数据库, 是否有该用户的游戏账户, 如果没有, 先创建账户
        Long userId = LoginInfoUtil.getUserId();
        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        String gameType = gameCategory.getGameType();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, gameCategory.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            log.info("没找到游戏账号, 创建一个账号");
            //没找到游戏账号, 就创建一个账号
            gameUser = this.createAccount(gameCategory, game);
        }

        if (gameUser == null) {
            throw new BusinessException(StatusCode.GAME_LOGIN_FAILURE);
        }

        LoginReq loginReq = new LoginReq();
        loginReq.setMerchantCode(gameCategory.getMerchantCode());
        loginReq.setHost(gameCategory.getInfoHost());
        //接口版本号 默认V1
        loginReq.setVersionEnum(VersionEnum.V1);
        loginReq.setGameTypeEnum(gameType);
        loginReq.setMd5Key(gameCategory.getMd5Key());

        //真人、棋牌、电游必传
        //真人是aesKey，棋牌和电游是IV向量
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                loginReq.setAesKey(gameCategory.getAesKey());
            } else {
                loginReq.setAesKey(gameCategory.getIv());
            }
        }

        //用户名
        loginReq.setLoginName(gameUser.getAccno());
        //昵称
        loginReq.setNickName(gameUser.getNickName());
        //密码
        loginReq.setPassword(SecurityUtils.MD5SaltEncrypt(gameUser.getPassword(), gameUser.getSalt()));
        //玩家登陆IP
        loginReq.setLoginIp(IPUtil.getLocalAddress());
        //返回商户地址
        loginReq.setBackurl(gameCategory.getBackUrl());
        //动态游戏域名 通过游戏域名访问游戏
        loginReq.setDomain(gameCategory.getDomain());
        //语言 枚举类,默认传递ZH_CN
        loginReq.setLang(getUserLang());
        //时间戳 当前时间13位时间戳
        loginReq.setTimestamp(System.currentTimeMillis());
        //设备类型
        loginReq.setDeviceType(getUserDeviceType());
        //游戏ID 棋牌电游游戏ID，体育是菜单ID
        loginReq.setGameId(game.getGameId());

        GameService gameService = GameService.getGameService();
        LoginResp resp = gameService.login(loginReq);
        if (resp.getCode() != 200) {
            throw new BusinessException(StatusCode.GAME_LOGIN_FAILURE);
        }

        //如果是真人游戏, 修改限红
        if (GameTypeConstant.ZR.equals(gameType)) {
            GameZRModifyBetLimitDTO dto = new GameZRModifyBetLimitDTO();
            BeanCopyUtil.copyProperties(gameUser, dto);
            BeanCopyUtil.copyProperties(game, dto);
            BeanCopyUtil.copyProperties(gameCategory, dto);
            gameZRBusiness.modifyBetLimit(dto);
        }
        GameLoginVO vo = new GameLoginVO();
        BeanCopyUtil.copyProperties(resp, vo);
        return vo;
    }


    /**
     * 创建游戏账户
     *
     * @param gameCategory
     * @param game
     */
    @Transactional
    public GameUser createAccount(GameCategory gameCategory, GameThird game) {
        log.info("createAccount..............");
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        String gameType = gameCategory.getGameType();
        String password = SecurityUtils.getRandomSalt();
        String accno = user.getAccno();
        String nickName = InviteCodeUtils.nickName().toLowerCase();
        String merchantCode = gameCategory.getMerchantCode();
        //保存数据到数据库
        GameUser gameUser = new GameUser();
        gameUser.setGameType(gameType);
        gameUser.setUserId(userId);
        gameUser.setAccno(accno);
        gameUser.setNickName(nickName);
        gameUser.setPassword(password);
        gameUser.setSalt(SecurityUtils.getRandomSalt());
        gameUser.setMerchantCode(merchantCode);
        CreateReq createReq = new CreateReq();
        //dy qp  zr
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                gameUser.setPlayerName((merchantCode + accno).toLowerCase());
                createReq.setAesKey(gameCategory.getAesKey());
            } else {
                gameUser.setPlayerName(accno);
                createReq.setAesKey(gameCategory.getIv());
            }
        }
        boolean result = gameUserService.save(gameUser);
        log.info("gameUser..............{}", gameUser);
        log.info("result..............{}", result);
        if (result) {
            createReq.setMerchantCode(merchantCode);
            createReq.setHost(gameCategory.getInfoHost());
            //接口版本号 默认V1
            createReq.setVersionEnum(VersionEnum.V1);

            createReq.setGameTypeEnum(gameType);
            //cp dj ty
            createReq.setMd5Key(gameCategory.getMd5Key());

            //游戏账号 最大长度15位,建议使用英文字母和数字组合
            createReq.setLoginName(accno);
            //昵称 建议不超过12位
            createReq.setNickName(nickName);
            //随机密码
            createReq.setPassword(SecurityUtils.MD5SaltEncrypt(password, gameUser.getSalt()));
            //用户IP
            createReq.setLoginIp(IPUtil.getLocalAddress());
            //会员类型 彩票、电竞支持新建测试账号，0正式，1测试，默认正式
            createReq.setMemberType(0);
            //棋牌电游游戏ID，体育是菜单ID，电游必传
            createReq.setGameId(game.getGameId());
            //语言 枚举类,默认传递ZH_CN
            createReq.setLang(getUserLang());
            //时间戳 当前时间13位时间戳
            createReq.setTimestamp(System.currentTimeMillis());
            //设备类型 1=pc，2=h5，3=ios，4=android，5=other
            createReq.setDeviceType(getUserDeviceType());
            BaseCommonResp resp = GameService.getGameService().create(createReq);
            if (resp.getCode() == 200) {
                //初始化游戏钱包
                memWalletService.initGameWallet(user,gameCategory.getGameWallet());
                return gameUser;
            } else {
                //如果创建失败, 删除数据库新建的账户
                gameUserService.removeById(gameUser.getId());
            }
        }
        return null;
    }


    /**
     * 获取用户语言
     *
     * @return
     */
    public LanguageEnum getUserLang() {
        String lang = LoginInfoUtil.getLang();
        if (StrUtil.isBlank(lang)) {
            return LanguageEnum.ZH_CN;
        }
        LanguageEnum languageEnum = GameLanguage.LanguageEnumMap.get(lang);
        if (languageEnum != null) {
            return languageEnum;
        }
        return LanguageEnum.ZH_CN;
    }


    /**
     * 获取用户设备类型
     */
    public DeviceTypeEnum getUserDeviceType() {
        String source = LoginInfoUtil.getSource();
        if (StrUtil.isBlank(source)) {
            return DeviceTypeEnum.OTHERS;
        }
        switch (source.toLowerCase()) {
            case "ios":
                return DeviceTypeEnum.IOS;
            case "android":
                return DeviceTypeEnum.ANDROID;
            case "pc":
                return DeviceTypeEnum.PC;
            default:
                return DeviceTypeEnum.OTHERS;
        }
    }


    /**
     * 游戏上分
     */
    public void gameDeposit(GameTransferReq req) {
        System.out.println("执行上分操作....");
        //参数校验
        if (req.getWalletType() == null || StrUtil.isBlank(req.getAmount())) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        //查询分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getIsDelete,false).eq(GameCategory::getIsWork,true)
                .eq(GameCategory::getGameWallet, req.getWalletType()).last(" LIMIT 1");
        GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //查询数据库, 是否有该用户的游戏账户, 如果没有, 先创建账户
        Long userId = LoginInfoUtil.getUserId();
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
        }

        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        String gameType = category.getGameType();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            //没找到游戏账号
            throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
        }

        //查询游戏钱包
        QueryWrapper<MemWallet> walletQuery = new QueryWrapper<>();
        walletQuery.lambda().eq(MemWallet::getUserId, userId)
                .eq(MemWallet::getWalletType, category.getGameWallet()).last(" LIMIT 1");
        MemWallet wallet = memWalletService.getBaseMapper().selectOne(walletQuery);
        if (wallet == null) {
            //再次初始化游戏钱包
            memWalletService.initGameWallet(user, category.getGameWallet());
        }
        wallet = memWalletService.getBaseMapper().selectOne(walletQuery);
        if (wallet == null) {
            throw new BusinessException(StatusCode.GAME_WALLET_INIT_FAILURE);
        }
        long timeStamp = System.currentTimeMillis();
        String loginName = gameUser.getAccno();
        //交易单号
        String transferNo = "";
        //dy qp 棋牌电游: transferNo =loginName:timestamp，这个要和传入的loginName和timestamp一致
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType)) {
            transferNo = loginName + ":" + timeStamp;
        } else if (GameTypeConstant.TY.equals(gameType)) {
            //体育：19位以内数字字符串
            transferNo = UUIDUtils.number(19);
        } else if (GameTypeConstant.DJ.equals(gameType)) {
            //电竞：20-32位数字字符串
            transferNo = UUIDUtils.number(20);
        } else if (GameTypeConstant.ZR.equals(gameType) || GameTypeConstant.CP.equals(gameType)) {
            //真人彩票：长度在20以内字符串
            transferNo = UUIDUtils.number(20);
        }

        if (StrUtil.isBlank(transferNo)) {
            throw new BusinessException(StatusCode.GAME_TYPE_UNSUPPORT);
        }

        //上分金额
        BigDecimal depositAmount = new BigDecimal(req.getAmount());

        //先创建一条上分记录
        GameTransferRecord record = new GameTransferRecord();
        record.setCategoryId(category.getCategoryId());
        record.setMerchantCode(category.getMerchantCode());
        record.setAmount(depositAmount);
        record.setUserId(userId);
        record.setTransferNo(transferNo);
        record.setType(GameTransferTypeEnums.GAME_DEPOSIT.getType());
        record.setTransferStatus(GameTransferStatusEnums.IN_PROGRESS.getStatus());
        boolean result = transferRecordService.save(record);
        if (result) {
            DepositReq depositReq = new DepositReq();
            depositReq.setHost(category.getInfoHost());
            if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
                if (GameTypeConstant.ZR.equals(gameType)) {
                    depositReq.setAesKey(category.getAesKey());
                } else {
                    depositReq.setAesKey(category.getIv());
                }
            }
            depositReq.setMd5Key(category.getMd5Key());
            depositReq.setGameTypeEnum(gameType);
            //接口版本号 默认V1
            depositReq.setVersionEnum(VersionEnum.V1);
            //商户账号
            depositReq.setMerchantCode(category.getMerchantCode());
            //游戏账号 如果是代理线模式商户中loginName格式应为：子代理+英文符号”-”+会员id，如：A001-test001，其中A001为子代理线，test001为会员id
            depositReq.setLoginName(loginName);
            //金额 单位元，保留两位小数，超过两位会做四舍五入
            depositReq.setAmount(depositAmount);
            //流水号
            depositReq.setTransferNo(transferNo);
            //昵称
            depositReq.setNickName(gameUser.getNickName());
            //密码
            depositReq.setPassword(SecurityUtils.MD5SaltEncrypt(gameUser.getPassword(), gameUser.getSalt()));
            //设备类型
            depositReq.setDeviceType(getUserDeviceType());
            //玩家登陆IP
            depositReq.setLoginIp(IPUtil.getLocalAddress());
            //时间戳 当前时间13位时间戳
            depositReq.setTimestamp(timeStamp);
            BaseCommonResp resp = GameService.getGameService().deposit(depositReq);
            //判断是否上分成功
            if (resp.getCode() == 200) {
                record.setTransferStatus(GameTransferStatusEnums.SUCCESS.getStatus());
            } else {
                record.setTransferStatus(GameTransferStatusEnums.FAILURE.getStatus());
            }
            //更新上分记录状态
            transferRecordService.updateById(record);
        }

    }

    /**
     * 游戏下分
     */
    public void gameWithdraw(GameTransferReq req) {
        System.out.println("执行下分操作....");
        //参数校验
        if (req.getWalletType() == null || StrUtil.isBlank(req.getAmount())) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        //查询分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getIsDelete,false).eq(GameCategory::getIsWork,true)
                .eq(GameCategory::getGameWallet, req.getWalletType()).last(" LIMIT 1");
        GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //查询数据库, 是否有该用户的游戏账户, 如果没有, 先创建账户
        Long userId = LoginInfoUtil.getUserId();
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
        }

        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        String gameType = category.getGameType();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            //没找到游戏账号
            throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
        }

        //查询游戏钱包
        QueryWrapper<MemWallet> walletQuery = new QueryWrapper<>();
        walletQuery.lambda().eq(MemWallet::getUserId, userId)
                .eq(MemWallet::getWalletType, category.getGameWallet())
                .last(" LIMIT 1");
        MemWallet wallet = memWalletService.getBaseMapper().selectOne(walletQuery);
        if (wallet == null) {
            //再次初始化游戏钱包
            memWalletService.initGameWallet(user,category.getGameWallet());
        }
        wallet = memWalletService.getBaseMapper().selectOne(walletQuery);
        if (wallet == null) {
            throw new BusinessException(StatusCode.GAME_WALLET_INIT_FAILURE);
        }
        long timeStamp = System.currentTimeMillis();
        String loginName = gameUser.getAccno();
        //交易单号
        String transferNo = "";
        //dy qp 棋牌电游: transferNo =loginName:timestamp，这个要和传入的loginName和timestamp一致
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType)) {
            transferNo = loginName + ":" + timeStamp;
        } else if (GameTypeConstant.TY.equals(gameType)) {
            //体育：19位以内数字字符串
            transferNo = UUIDUtils.number(19);
        } else if (GameTypeConstant.DJ.equals(gameType)) {
            //电竞：20-32位数字字符串
            transferNo = UUIDUtils.number(20);
        } else if (GameTypeConstant.ZR.equals(gameType) || GameTypeConstant.CP.equals(gameType)) {
            //真人彩票：长度在20以内字符串
            transferNo = UUIDUtils.number(20);
        }

        if (StrUtil.isBlank(transferNo)) {
            throw new BusinessException(StatusCode.GAME_TYPE_UNSUPPORT);
        }

        //下分金额
        BigDecimal depositAmount = new BigDecimal(req.getAmount());

        //先创建一条下分记录
        GameTransferRecord record = new GameTransferRecord();
        record.setCategoryId(category.getCategoryId());
        record.setMerchantCode(category.getMerchantCode());
        record.setAmount(depositAmount);
        record.setUserId(userId);
        record.setTransferNo(transferNo);
        record.setType(GameTransferTypeEnums.GAME_WITHDRAW.getType());
        record.setTransferStatus(GameTransferStatusEnums.IN_PROGRESS.getStatus());
        boolean result = transferRecordService.save(record);
        if (result) {
            WithDrawReq withDrawReq = new WithDrawReq();
            withDrawReq.setHost(category.getInfoHost());
            if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
                if (GameTypeConstant.ZR.equals(gameType)) {
                    withDrawReq.setAesKey(category.getAesKey());
                } else {
                    withDrawReq.setAesKey(category.getIv());
                }
            }
            withDrawReq.setMd5Key(category.getMd5Key());
            withDrawReq.setMerchantCode(category.getMerchantCode());
            withDrawReq.setGameTypeEnum(gameType);
            //接口版本号 默认V1
            withDrawReq.setVersionEnum(VersionEnum.V1);
            //用户名
            withDrawReq.setLoginName(loginName);
            //时间戳 当前时间13位时间戳
            withDrawReq.setTimestamp(timeStamp);
            //交易单号
            withDrawReq.setTransferNo(transferNo);
            //金额 单位元，保留两位小数，超过两位会做四舍五入
            withDrawReq.setAmount(depositAmount);
            //昵称
            withDrawReq.setNickName(gameUser.getNickName());
            //玩家登陆IP
            withDrawReq.setLoginIp(IPUtil.getLocalAddress());
            //设备类型
            withDrawReq.setDeviceType(getUserDeviceType());
            //密码
            withDrawReq.setPassword(SecurityUtils.MD5SaltEncrypt(gameUser.getPassword(), gameUser.getSalt()));
            BaseCommonResp resp = GameService.getGameService().withdraw(withDrawReq);
            System.out.println(GsonUtil.getInstance().toJson(resp));
            //判断是否上分成功
            if (resp.getCode() == 200) {
                record.setTransferStatus(GameTransferStatusEnums.SUCCESS.getStatus());
            } else {
                record.setTransferStatus(GameTransferStatusEnums.FAILURE.getStatus());
            }
            //更新上分记录状态
            transferRecordService.updateById(record);
        }

    }

    /**
     * 会员余额查询
     */
    public BalanceResp queryBalance(GameBalanceReq req) {
        //查询数据库, 是否有该用户的游戏账户, 如果没有, 先创建账户
        Long userId = LoginInfoUtil.getUserId();
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
        }
        //查询分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getCategoryId, req.getCategoryId()).eq(GameCategory::getIsDelete,false).last(" LIMIT 1");
        GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String gameType = category.getGameType();

        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            //没找到游戏账号
            throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
        }

        BalanceReq balanceReq = new BalanceReq();
        balanceReq.setHost(category.getInfoHost());
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                balanceReq.setAesKey(category.getAesKey());
            } else {
                balanceReq.setAesKey(category.getIv());
            }
        }
        balanceReq.setMd5Key(category.getMd5Key());
        balanceReq.setGameTypeEnum(gameType);
        //接口版本号 默认V1
        balanceReq.setVersionEnum(VersionEnum.V1);
        //商户账号
        balanceReq.setMerchantCode(category.getMerchantCode());
        //游戏账号 如果是代理线模式商户中loginName格式应为：子代理+英文符号”-”+会员id，如：A001-test001，其中A001为子代理线，test001为会员id
        balanceReq.setLoginName(gameUser.getAccno());
        //密码
        balanceReq.setPassword(SecurityUtils.MD5SaltEncrypt(gameUser.getPassword(), gameUser.getSalt()));
        //玩家登陆IP
        balanceReq.setLoginIp(IPUtil.getLocalAddress());
        //时间戳 当前时间13位时间戳
        balanceReq.setTimestamp(System.currentTimeMillis());
        BalanceResp resp = GameService.getGameService().balance(balanceReq);
        System.out.println(GsonUtil.getInstance().toJson(resp));
        return resp;
    }


    /**
     * 转账状态查询
     */
    public QueryTransferResp queryTransfer(GameTransferStatusReq req) {

        //查询数据库, 是否有该用户的游戏账户
        Long userId = LoginInfoUtil.getUserId();
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
        }
        //查询分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getCategoryId, req.getCategoryId()).last(" LIMIT 1");
        GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String gameType = category.getGameType();

        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            //没找到游戏账号
            throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
        }
        QueryTransferReq queryTransferReq = new QueryTransferReq();
        queryTransferReq.setHost(category.getInfoHost());
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                queryTransferReq.setAesKey(category.getAesKey());
            } else {
                queryTransferReq.setAesKey(category.getIv());
            }
        }
        queryTransferReq.setMd5Key(category.getMd5Key());
        //商户账号
        queryTransferReq.setMerchantCode(category.getMerchantCode());
        //游戏类型
        queryTransferReq.setGameTypeEnum(gameType);
        //接口版本号 默认V1
        queryTransferReq.setVersionEnum(VersionEnum.V1);
        //用户名
        queryTransferReq.setLoginName(gameUser.getAccno());
        //时间戳 当前时间13位时间戳
        queryTransferReq.setTimestamp(System.currentTimeMillis());
        //交易单号
        queryTransferReq.setTransferNo(req.getTransferNo());
        QueryTransferResp resp = GameService.getGameService().queryTransfer(queryTransferReq);
        System.out.println(GsonUtil.getInstance().toJson(resp));
        return resp;
    }


    /**
     * 转账记录列表
     *
     * @param req
     */
    public BaseCommonResp queryTransferList(GameTransferListReq req) {
        //查询数据库, 是否有该用户的游戏账户
        Long userId = LoginInfoUtil.getUserId();
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
        }
        //查询分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getCategoryId, req.getCategoryId()).last(" LIMIT 1");
        GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String gameType = category.getGameType();

        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            //没找到游戏账号
            throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
        }
        QueryTransferListReq queryTransferReq = new QueryTransferListReq();
        BeanCopyUtil.copyProperties(req, queryTransferReq);
        queryTransferReq.setHost(category.getInfoHost());
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                queryTransferReq.setAesKey(category.getAesKey());
            } else {
                queryTransferReq.setAesKey(category.getIv());
            }
        }
        queryTransferReq.setMd5Key(category.getMd5Key());
        //时间
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        try {
            //设置时间
            Long startDate = DateUtils.parseDate(startTime, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
            Long endDate = DateUtils.parseDate(endTime, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
            queryTransferReq.setStartTime(startDate);
            queryTransferReq.setEndTime(endDate);
            //游戏类型
            queryTransferReq.setGameTypeEnum(gameType);
            //商户账号
            queryTransferReq.setMerchantCode(category.getMerchantCode());
            //接口版本号 默认V1
            queryTransferReq.setVersionEnum(VersionEnum.V1);
            //用户名
            queryTransferReq.setLoginName(gameUser.getAccno());
            //时间戳 当前时间13位时间戳
            queryTransferReq.setTimestamp(System.currentTimeMillis());
            GameService gameService = GameService.getGameService();
            BaseCommonResp resp = gameService.queryTransferList(queryTransferReq);
            return resp;
        } catch (ParseException e) {
            //e.printStackTrace();
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
    }


    /**
     * 查询游戏注单列表
     */
    public QueryBetRecordListResp queryBetRecordList(GameBetRecordListReq req) {
        try {
            //查询数据库, 是否有该用户的游戏账户
            Long userId = LoginInfoUtil.getUserId();
            MemUser user = memUserService.getById(userId);
            if (user == null) {
                throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
            }
            //查询分类信息
            QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
            categoryWrapper.lambda().eq(GameCategory::getCategoryId, req.getCategoryId()).last(" LIMIT 1");
            GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
            if (category == null) {
                throw new BusinessException(StatusCode.PARAM_ERROR);
            }
            String gameType = category.getGameType();

            QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                    .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
            GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
            if (gameUser == null) {
                //没找到游戏账号
                throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
            }
            String str = req.getStartTime();
            String etr = req.getEndTime();
            Long sDate = DateUtils.parseDate(str, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
            Long eDate = DateUtils.parseDate(etr, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
            QueryBetRecordListReq queryBetRecordListReq = new QueryBetRecordListReq();
            BeanCopyUtil.copyProperties(req, queryBetRecordListReq);
            queryBetRecordListReq.setHost(category.getDataHost());
            if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
                if (GameTypeConstant.ZR.equals(gameType)) {
                    queryBetRecordListReq.setAesKey(category.getAesKey());
                } else {
                    queryBetRecordListReq.setAesKey(category.getIv());
                }
            }
            queryBetRecordListReq.setMd5Key(category.getMd5Key());
            queryBetRecordListReq.setGameTypeEnum(gameType);
            //接口版本号 默认V1
            queryBetRecordListReq.setVersionEnum(VersionEnum.V1);
            //商户账号
            queryBetRecordListReq.setMerchantCode(category.getMerchantCode());
            //开始时间 13位时间戳
            queryBetRecordListReq.setStartTime(sDate);
            //结束时间 13位时间戳
            queryBetRecordListReq.setEndTime(eDate);
            //页码
            queryBetRecordListReq.setPageIndex(1);
            //时间戳 当前时间13位时间戳
            queryBetRecordListReq.setTimestamp(System.currentTimeMillis());
            QueryBetRecordListResp resp = GameService.getGameService().queryBetRecordList(queryBetRecordListReq);
            return resp;
        } catch (ParseException e) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
    }

    /**
     * 查询第三方游戏TAG标签列表
     *
     * @return
     */
    public List<AppGameTagListVO> queryGameTagList() {
        QueryWrapper<GameTag> queryWrapper = new QueryWrapper<>();
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

        queryWrapper.lambda().eq(GameTag::getIsDelete, false).eq(GameTag::getIsShow, true).eq(GameTag::getCountryCode, countryCode);
        List<GameTag> list = gameTagService.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            log.info("==========END list is null =========");
            return new LinkedList<>();
        }
        List<Long> tagIdList = new LinkedList<>();
        list.stream().forEach(tag -> tagIdList.add(tag.getId()));
        //查询多语言
        QueryWrapper<GameTagLang> tagLangWrapper = new QueryWrapper<>();
        tagLangWrapper.lambda().in(GameTagLang::getTagId, tagIdList);
        List<GameTagLang> langList = gameTagLangService.list(tagLangWrapper);
        //转map
        Map<String, Map<Long, String>> dataMap = new HashMap<>();
        langList.stream().forEach(item -> {
            String lang = item.getLang();
            Map<Long, String> tagLangMap = dataMap.get(lang);
            if (tagLangMap == null) {
                tagLangMap = new HashMap<>();
                dataMap.put(lang, tagLangMap);
            }
            tagLangMap.put(item.getTagId(), item.getName());
        });

        //赋值多语言
        String lang = LoginInfoUtil.getLang();
        Map<Long, String> gameTagLangs = dataMap.get(lang);
        if (CollectionUtil.isEmpty(gameTagLangs)) {
            dataMap.get(LangConstants.LANG_US);
        }
        if (CollectionUtil.isEmpty(gameTagLangs)) {
            dataMap.get(LangConstants.LANG_CN);
        }
        if (CollectionUtil.isEmpty(gameTagLangs)) {
            log.info("==========END gameTagLangs is null =========");
            return new LinkedList<>();
        }
        list.stream().forEach(item -> item.setName(gameTagLangs.get(item.getId())));
        return BeanCopyUtil.copyCollection(list, AppGameTagListVO.class);
    }


    /**
     * 根据标签ID查询游戏列表
     *
     * @param req
     * @return
     */
    public List<AppGameCenterGameListVO> queryGameListByTag(GameListByTagReq req) {
        req.setLang(LoginInfoUtil.getLang());
        List<GameCenterGame> list = gameCenterGameService.listByTagWithLang(req);
        if (CollectionUtil.isEmpty(list)) {
            return new LinkedList<>();
        }
        return BeanCopyUtil.copyCollection(list, AppGameCenterGameListVO.class);
    }

    /**
     * 查询游戏分类列表和分类下的游戏列表
     *
     * @return
     */
    public List<AppGameTagListDataVO> list() {
        //根据国家code和语言lang查询当前的所有标签
        List<GameTag> tagList = gameTagService.listWithCurrentLang();
        if (CollectionUtil.isEmpty(tagList)) {
            return new LinkedList<>();
        }

        //查询所有的游戏
        List<GameCenterGame> gameList = gameCenterGameService.listWithCurrentLang();

        List<AppGameTagListDataVO> voList = BeanCopyUtil.copyCollection(tagList, AppGameTagListDataVO.class);

        if(CollectionUtil.isNotEmpty(gameList)){
            Map<String, List<GameCenterGame>> gameMap = gameList.stream().collect(Collectors.groupingBy(GameCenterGame::getCode));
            voList.stream().forEach(tag -> {
                List<GameCenterGame> list = gameMap.get(tag.getCode());
                if(CollectionUtil.isNotEmpty(list)){
                    List<AppGameCenterGameListVO> gameVoList = BeanCopyUtil.copyCollection(list, AppGameCenterGameListVO.class);
                    tag.setGameList(gameVoList);
                }
            });
        }
        return voList;
    }


    /**
     * 查询游戏列表
     */
    public void listGame(GameListSyncReq req) {
        //查询游戏关联的分类信息
        GameCategory category = gameCategoryService.getByCategoryId(req.getCategoryId());
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String gameType = category.getGameType();
        GameConfigReq gameConfigReq = new GameConfigReq();
        gameConfigReq.setHost(category.getInfoHost());
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                gameConfigReq.setAesKey(category.getAesKey());
            } else {
                gameConfigReq.setAesKey(category.getIv());
            }
        }
        gameConfigReq.setMd5Key(category.getMd5Key());

        gameConfigReq.setGameTypeEnum(gameType);
        //接口版本号 默认V1
        gameConfigReq.setVersionEnum(VersionEnum.V1);
        //商户账号
        gameConfigReq.setMerchantCode(category.getMerchantCode());
        //时间戳 当前时间13位时间戳
        gameConfigReq.setTimestamp(System.currentTimeMillis());
        GameConfigResp gameListResp = GameService.getGameService().gameConfig(gameConfigReq);
        System.out.println(GsonUtil.getInstance().toJson(gameListResp));
    }

    /**
     * 根据钱包类型, 查询第三方游戏中的余额
     * @param req
     * @return
     */
    public BalanceResp queryWalletBalance(GameWalletReq req) {
        //查询数据库, 是否有该用户的游戏账户
        Long userId = LoginInfoUtil.getUserId();
        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(StatusCode.RESET_NO_USER_ERROR);
        }
        //查询分类信息
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getGameWallet, req.getGameWallet()).eq(GameCategory::getIsDelete,false).last(" LIMIT 1");
        GameCategory category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
        if (category == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        String gameType = category.getGameType();

        QueryWrapper<GameUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GameUser::getUserId, userId).eq(GameUser::getGameType, gameType)
                .eq(GameUser::getMerchantCode, category.getMerchantCode()).last(" LIMIT 1");
        GameUser gameUser = gameUserService.getBaseMapper().selectOne(queryWrapper);
        if (gameUser == null) {
            //没找到游戏账号
            throw new BusinessException(StatusCode.GAME_ACCOUNT_NOT_FOUND);
        }

        BalanceReq balanceReq = new BalanceReq();
        balanceReq.setHost(category.getInfoHost());
        if (GameTypeConstant.DY.equals(gameType) || GameTypeConstant.QP.equals(gameType) || GameTypeConstant.ZR.equals(gameType)) {
            if (GameTypeConstant.ZR.equals(gameType)) {
                balanceReq.setAesKey(category.getAesKey());
            } else {
                balanceReq.setAesKey(category.getIv());
            }
        }
        balanceReq.setMd5Key(category.getMd5Key());
        balanceReq.setGameTypeEnum(gameType);
        //接口版本号 默认V1
        balanceReq.setVersionEnum(VersionEnum.V1);
        //商户账号
        balanceReq.setMerchantCode(category.getMerchantCode());
        //游戏账号 如果是代理线模式商户中loginName格式应为：子代理+英文符号”-”+会员id，如：A001-test001，其中A001为子代理线，test001为会员id
        balanceReq.setLoginName(gameUser.getAccno());
        //密码
        balanceReq.setPassword(SecurityUtils.MD5SaltEncrypt(gameUser.getPassword(), gameUser.getSalt()));
        //玩家登陆IP
        balanceReq.setLoginIp(IPUtil.getLocalAddress());
        //时间戳 当前时间13位时间戳
        balanceReq.setTimestamp(System.currentTimeMillis());
        BalanceResp resp = GameService.getGameService().balance(balanceReq);
        System.out.println(GsonUtil.getInstance().toJson(resp));
        return resp;
    }
}
