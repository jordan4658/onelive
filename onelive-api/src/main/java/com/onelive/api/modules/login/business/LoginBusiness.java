package com.onelive.api.modules.login.business;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Splitter;
import com.onelive.api.modules.sys.business.SysBusiness;
import com.onelive.api.service.agent.AgentInviteCodeService;
import com.onelive.api.service.agent.AgentInviteRecordService;
import com.onelive.api.service.mem.MemLevelLinkService;
import com.onelive.api.service.mem.MemLoginRecordService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.api.service.sys.SysCountryService;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.service.sys.SysShortMsgService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.business.sms.SmsBusiness;
import com.onelive.common.constants.business.InviteCodeConstants;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.enums.*;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.platformConfig.AccountConfigDto;
import com.onelive.common.model.dto.platformConfig.PlatformConfigDto;
import com.onelive.common.model.req.login.*;
import com.onelive.common.model.vo.common.BooleanVO;
import com.onelive.common.model.vo.login.AppLoginTokenVo;
import com.onelive.common.model.vo.login.CountryListVo;
import com.onelive.common.model.vo.login.UserAreaVo;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.service.sms.SmsSendCommonUtils;
import com.onelive.common.utils.Login.IPAddressUtil;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.img.HeaderImgRamUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.InviteCodeUtils;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.common.utils.redis.SysBusinessRedisUtils;
import com.onelive.common.utils.redis.UserBusinessRedisUtils;
import com.onelive.common.utils.upload.AWSS3Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class LoginBusiness {

    @Resource
    private MemUserService memUserService;
    @Resource
    private MemUserMsgRepushRecordService repushRecordService;
    @Resource
    private SysShortMsgService sysShortMsgService;
    @Resource
    private MemWalletService memWalletService;
    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private MemLoginRecordService memLoginRecordService;
    @Resource
    private MemLevelLinkService memLevelLinkService;
    @Resource
    private AgentInviteCodeService agentInviteCodeService;
    @Resource
    private AgentInviteRecordService agentInviteRecordService;
    @Resource
    private SmsBusiness smsBusiness;
    @Resource
    private SysBusiness sysBusiness;
    @Resource
    private SysParameterService sysParameterService;


    private final static String tokenPre = "onelive";

    /**
     * ????????????????????????
     *
     * @param req
     * @return
     */
    public Boolean isExistsAccount(ApiCheckAccountReq req) {
        return memUserService.isExistByAccount(req.getUserAccount());
    }


    /**
     * ????????????
     */
    @Transactional
    public ResultInfo<AppLoginTokenVo> register(RegisterReq req) throws Exception {
        Boolean isTest = true;
        String areaCode = req.getAreaCode();
        String mobilePhone = req.getMobilePhone();
        String password = req.getPassword();
        String smsCode = req.getSmsCode();
        String imgCode = req.getImgCode();
        String captchaKey = req.getCaptchaKey();
        String inviteCode = req.getInviteCode();//?????????
        String countryCode = req.getCountryCode();

        //????????????????????????
        if (StringUtils.isBlank(areaCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_AREACODE);
        }
        // ?????????????????????????????????????????????
        Long defaultHierarchy = configCheck(areaCode);

        //??????????????????????????????
        if (StringUtils.isBlank(countryCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_AREACODE);
        }

        //?????????????????????
        if (StringUtils.isBlank(mobilePhone)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PHONE);
        }
        //??????????????????
        if (StringUtils.isBlank(password)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
        }

        //???????????????????????????
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //?????????????????????????????????
        if (isTest) {
            //???????????????????????????
        } else {
            sysShortMsgService.checkSmsCode(mobilePhone, areaCode, smsCode, SendTypeEnum.REGISTER.getCode());
        }

        //???????????????????????????
        if (StringUtils.isBlank(imgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
        }
        //?????????????????????????????????
        if (StringUtils.isBlank(captchaKey)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }
        //?????????????????????????????????
        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(captchaKey);
        if (!imgCode.equalsIgnoreCase(trueImgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }
        //??????????????????
        if (StringUtils.isBlank(password)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
        }
        //??????????????????  TODO,???????????????????????????


        //?????????????????????????????????
        LoginInfoUtil.setInviteCode(inviteCode);
        //??????????????????
        Boolean accloginExists = memUserService.isExistByAccount(areaCode + mobilePhone);
        if (accloginExists) {
            return ResultInfo.getInstance(StatusCode.REGISTER_EXISTS_PHONE);
        }

        Date now = new Date();
        MemUser memUser = new MemUser();
        memUser.setAvatar(HeaderImgRamUtil.userHeadImg());
        memUser.setAccno(InviteCodeUtils.accountCode());
        memUser.setSalt(SecurityUtils.getRandomSalt());
        memUser.setPassword(SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt()));
        memUser.setMobilePhone(mobilePhone);
        memUser.setRegisterAreaCode(areaCode);
        memUser.setUserAccount(areaCode + mobilePhone);
        //????????????????????????????????????????????????????????????
        SysCountry country = SysBusinessRedisUtils.getCountryInfo(countryCode); // sysCountryService.getCountryByCountryCode(countryCode);
        memUser.setRegisterCountryCode(country.getCountryCode());
        //??????????????????code
        memUser.setDefaultCountryCode(country.getCountryCode());
        //????????????????????????code
        memUser.setOpenCountryCode(country.getCountryCode());
        memUser.setCountryId(country.getId());
        memUser.setRegisterArea(country.getZhName());
        memUser.setNickName(InviteCodeUtils.nickName());
        //id???1??????????????????TODO ???????????????????????????????????? Not stratified
        memUser.setGroupId(defaultHierarchy);
        //?????????????????????0
        memUser.setRegisterIp(LoginInfoUtil.getIp());
        memUser.setRegisterTime(now);
        memUser.setRegisterSource(LoginInfoUtil.getSource());

        String deviceType = LoginInfoUtil.getDeviceType();
        if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
            deviceType = deviceType.substring(0, 100);
        }
        memUser.setRegisterDevice(deviceType);

        memUser.setLastLoginIp(memUser.getRegisterIp());
        memUser.setLastLoginTime(now);
        memUser.setLastLoginSource(memUser.getLastLoginSource());
        memUser.setLastLoginArea(memUser.getRegisterArea());
        memUser.setIsFrozen(false);
        memUser.setIsCommission(true);
        memUser.setIsBet(true);
        memUser.setIsDispensing(true);
        memUser.setIsSuperLiveManage(false);
        memUser.setIsOnline(true);
        memUser.setMerchantCode(LoginInfoUtil.getMerchantCode());
        memUser.setUserLevel(0);
        memUser.setSex(SexEnums.UNKNOWN.getCode());

        //??????????????????????????????
        memUser.setMobileAuthenticated(true);

        memUserService.save(memUser);

        //???????????????????????????
        this.initRegister(memUser);

        //??????token?????????????????????
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, true);
        return ResultInfo.ok(loginVO);
    }


    /**
     * ??????
     *
     * @param req
     * @return
     */
    public ResultInfo<AppLoginTokenVo> login(AppLoginReq req) {
        String areaCode = req.getAreaCode();
        String mobilePhone = req.getMobilePhone();
        String password = req.getPassword();
        String imgCode = req.getImgCode();
        String captchaKey = req.getCaptchaKey();

        if (StringUtils.isBlank(areaCode)
                || StringUtils.isBlank(mobilePhone)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(imgCode)
                || StringUtils.isBlank(captchaKey)
        ) {
            return ResultInfo.getInstance(StatusCode.LOGIN_PARAM_ERROR);
        }
        //???????????????????????????
        MemUser memUser = memUserService.queryByAccount(areaCode + mobilePhone);
        if (memUser == null) {
            return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
        }
        //????????????
        password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
        if (!password.equals(memUser.getPassword())) {
            //?????????????????????????????????????????????1???????????????,5????????????TODO ????????????????????????
            Integer loginNum = ApiBusinessRedisUtils.getLoginProtect(memUser.getAccno());
            if (loginNum >= 5) {
                return ResultInfo.getInstance(StatusCode.LOGIN_LOCK_PASSWORD);
            } else {
                ApiBusinessRedisUtils.setLoginProtect(memUser.getAccno(), loginNum + 1);
                return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
            }
        }
        //??????????????????
        if (memUser.getIsFrozen()) {
            return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
        }
        //???????????? TODO,???????????????????????????

        //?????????????????????????????????
        memUser.setLastLoginIp(LoginInfoUtil.getIp());
        //memUser.setLastLoginArea(IPAddressUtil.getClientArea(memBaseInfo.getLastLoginIp()));
        memUser.setLastLoginTime(DateUtil.date());
        memUser.setLastLoginSource(LoginInfoUtil.getSource());
        String deviceType = LoginInfoUtil.getDeviceType();
        if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
            deviceType = deviceType.substring(0, 100);
        }
        memUser.setLastLoginDevice(deviceType);
        memUserService.updateById(memUser);
        //?????????????????????????????????
        checkingWallet(memUser);
        //app????????????????????????,?????????????????????
        ApiBusinessRedisUtils.deleteAllToken(memUser);
        //??????token?????????????????????
        SysCountry country = sysCountryService.getById(memUser.getCountryId());

        String countryCode = null;
        if (country != null) {
            countryCode = country.getCountryCode();
        }
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, false);

        return ResultInfo.ok(loginVO);
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public ResultInfo<AppLoginTokenVo> visitorLogin() {
        AppLoginTokenVo loginVO;
        Long userId = ApiBusinessRedisUtils.getLoginTourist(LoginInfoUtil.getDeviceId());

        //????????????, ?????????????????????
        if (userId == null) {
            QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(MemUser::getUserDeviceId, LoginInfoUtil.getDeviceId()).eq(MemUser::getMerchantCode, LoginInfoUtil.getMerchantCode());
            List<MemUser> list = memUserService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                for (MemUser memUser : list) {
                    if (memUser.getUserType() == UserTypeEnum.tourist.getCode()) {
                        userId = memUser.getId();
                        break;
                    }
                }
            }
        }

        //????????????
        if (userId == null) {
            Date now = new Date();
            MemUser memUser = new MemUser();
            memUser.setAvatar(HeaderImgRamUtil.userHeadImg());
            memUser.setAccno(InviteCodeUtils.accountCode());
            memUser.setSalt(null);
            memUser.setPassword(null);
            memUser.setMobilePhone(null);
            memUser.setRegisterAreaCode(null);
            memUser.setUserAccount(memUser.getAccno());
            //???????????????????????????????????????????????????????????????TODO ????????????redis???
            //SysCountry country = sysCountryService.getCountryByCountryCode(req.getCountryCode());
            String countryCode = ApiBusinessRedisUtils.getTouristCountryCode(LoginInfoUtil.getDeviceId());
            if (StrUtil.isNotBlank(countryCode)) {
                SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
                memUser.setRegisterAreaSelectStatus(false);
                memUser.setRegisterCountryCode(countryCode);
                //??????????????????code
                memUser.setDefaultCountryCode(countryCode);
                //????????????????????????code
                memUser.setOpenCountryCode(countryCode);
                memUser.setCountryId(country.getId());
                memUser.setRegisterArea(country.getZhName());
                memUser.setRegisterAreaCode(country.getAreaCode());
            }
            memUser.setNickName(InviteCodeUtils.nickName());
            //id???1??????????????????TODO ???????????????????????????????????? Not stratified
            // ?????????????????????????????????????????????
            Long defaultHierarchy = configCheck(null);

            memUser.setGroupId(defaultHierarchy);
            //?????????????????????0
//            memUser.setUserLevel(1);
            memUser.setRegisterIp(LoginInfoUtil.getIp());
            memUser.setRegisterTime(now);
            memUser.setRegisterSource(LoginInfoUtil.getSource());

            String deviceType = LoginInfoUtil.getDeviceType();
            if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
                deviceType = deviceType.substring(0, 100);
            }
            memUser.setRegisterDevice(deviceType);

            memUser.setLastLoginIp(memUser.getRegisterIp());
            memUser.setLastLoginTime(now);
            memUser.setLastLoginSource(memUser.getLastLoginSource());
            memUser.setLastLoginArea(memUser.getRegisterArea());
            memUser.setIsFrozen(false);
            memUser.setIsCommission(true);
            memUser.setIsBet(true);
            memUser.setIsDispensing(true);
            memUser.setIsSuperLiveManage(false);
            memUser.setIsOnline(true);
            memUser.setMerchantCode(LoginInfoUtil.getMerchantCode());
            memUser.setUserLevel(0);
            memUser.setSex(SexEnums.UNKNOWN.getCode());
            memUser.setUserDeviceId(LoginInfoUtil.getDeviceId());
            memUser.setUserType(UserTypeEnum.tourist.getCode());
            memUserService.save(memUser);

            //??????????????????id
            ApiBusinessRedisUtils.setLoginTourist(LoginInfoUtil.getDeviceId(), memUser.getId());
            //???????????????????????????
            this.initRegister(memUser);

            //??????token?????????????????????
            loginVO = dealLogin(memUser, countryCode, true);
        } else {
            //????????????
            MemUser memUser = memUserService.getById(userId);
            if (memUser == null) {
                return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
            }
            //?????????????????????????????????????????????
            if (memUser.getUserType() == UserTypeEnum.general.getCode()) {
                return ResultInfo.getInstance(StatusCode.LOGIN_RESET_WAY);
            }
            //??????????????????
            if (memUser.getIsFrozen()) {
                return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
            }
            //?????????????????????????????????
            memUser.setLastLoginIp(LoginInfoUtil.getIp());
            //memUser.setLastLoginArea(IPAddressUtil.getClientArea(memBaseInfo.getLastLoginIp()));
            memUser.setLastLoginTime(DateUtil.date());
            memUser.setLastLoginSource(LoginInfoUtil.getSource());
            String deviceType = LoginInfoUtil.getDeviceType();
            if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
                deviceType = deviceType.substring(0, 100);
            }
            memUser.setLastLoginDevice(deviceType);

            if (StrUtil.isBlank(memUser.getRegisterCountryCode())) {
                String countryCode = ApiBusinessRedisUtils.getTouristCountryCode(LoginInfoUtil.getDeviceId());
                if (StrUtil.isNotBlank(countryCode)) {
                    SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
                    memUser.setRegisterAreaSelectStatus(false);
                    memUser.setRegisterCountryCode(countryCode);
                    //??????????????????code
                    memUser.setDefaultCountryCode(countryCode);
                    //????????????????????????code
                    memUser.setOpenCountryCode(countryCode);
                    memUser.setCountryId(country.getId());
                    memUser.setRegisterArea(country.getZhName());
                    memUser.setRegisterAreaCode(country.getAreaCode());
                }
            }

            memUserService.updateById(memUser);
            //?????????????????????????????????
            checkingWallet(memUser);

            //app????????????????????????,?????????????????????
            ApiBusinessRedisUtils.deleteAllToken(memUser);
            //??????token?????????????????????
            String countryCode = null;
            Long countryId = memUser.getCountryId();
            if (countryId != null) {
                SysCountry country = sysCountryService.getById(countryId);
                countryCode = country.getCountryCode();
            }
            loginVO = dealLogin(memUser, countryCode, false);
        }

        //????????????????????????
        ApiBusinessRedisUtils.removeTouristCountryCode(LoginInfoUtil.getDeviceId());

        return ResultInfo.ok(loginVO);
    }

    /**
     * ??????
     *
     * @param account
     * @param token
     */
    public void logout(String account, String token) {
        ApiBusinessRedisUtils.deleteToken(account, token);
    }


    /**
     * ??????????????????
     *
     * @param req
     * @return
     */
    public ResultInfo<String> resetPassword(ResetPasswordReq req) throws Exception {
        String areaCode = req.getAreaCode();
        String mobilePhone = req.getMobilePhone();
        String password = req.getPassword();
        String smsCode = req.getSmsCode();
        String imgCode = req.getImgCode();
        String captchaKey = req.getCaptchaKey();

        if (StringUtils.isBlank(password)) {
            return ResultInfo.getInstance(StatusCode.RESET_EMPTY_PASSWORD);
        }

        if (StringUtils.isBlank(areaCode)
                || StringUtils.isBlank(mobilePhone)
                || StringUtils.isBlank(imgCode)
                || StringUtils.isBlank(smsCode)
                || StringUtils.isBlank(captchaKey)
        ) {
            return ResultInfo.getInstance(StatusCode.LOGIN_PARAM_ERROR);
        }

        //??????????????????????????????????????????
        MemUser memUser = memUserService.queryByAccount(areaCode + mobilePhone);
        if (memUser == null) {
            return ResultInfo.getInstance(StatusCode.RESET_EMPTY_PASSWORD);
        }

        //???????????????????????????
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //?????????????????????????????????
        sysShortMsgService.checkSmsCode(mobilePhone, areaCode, smsCode, SendTypeEnum.CHANGEPASSWORD.getCode());

        //???????????????????????????
        if (StringUtils.isBlank(imgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
        }
        //?????????????????????????????????
        if (StringUtils.isBlank(captchaKey)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }
        //?????????????????????????????????
        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(captchaKey);
        if (imgCode.equalsIgnoreCase(trueImgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }

        memUser.setPassword(SecurityUtils.MD5SaltEncrypt(req.getPassword(), memUser.getSalt()));
        memUserService.updateById(memUser);

        return ResultInfo.ok();
    }


    /**
     * ??????????????????
     *
     * @param req
     * @return
     */
    public ResultInfo<Boolean> bindPhone(BindPhoneReq req) throws Exception {
        String areaCode = req.getAreaCode();
        String mobilePhone = req.getMobilePhone();
//        String password = req.getPassword();
        String smsCode = req.getSmsCode();
//        String imgCode = req.getImgCode();
//        String captchaKey = req.getCaptchaKey();

//        if (StringUtils.isBlank(password)) {
//            return ResultInfo.getInstance(StatusCode.RESET_EMPTY_PASSWORD);
//        }

        if (StringUtils.isBlank(areaCode)
                || StringUtils.isBlank(mobilePhone)
//                || StringUtils.isBlank(imgCode)
//                || StringUtils.isBlank(smsCode)
//                || StringUtils.isBlank(captchaKey)
        ) {
            return ResultInfo.getInstance(StatusCode.LOGIN_PARAM_ERROR);
        }

        //??????????????????????????????????????????
        Long userId = LoginInfoUtil.getUserId();
        MemUser memUser = memUserService.getById(userId);
        if (memUser == null) {
            return ResultInfo.getInstance(StatusCode.RESET_NO_USER_ERROR);
        }
        //???????????????????????????
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //?????????????????????????????????
        Boolean flag = SmsSendCommonUtils.getVerificationCode(areaCode + mobilePhone, 3,req.getSmsCode());
        if (!flag) {
            throw new BusinessException(StatusCode.SMS_CODE_ERROR);
        }

        //???????????????????????????
//        if(StringUtils.isBlank(imgCode)){
//            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
//        }
//        //?????????????????????????????????
//        if(StringUtils.isBlank(captchaKey)){
//            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
//        }
//        //?????????????????????????????????
//        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(captchaKey);
//        if(imgCode.equalsIgnoreCase(trueImgCode)){
//            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
//        }

        memUser.setMobilePhone(mobilePhone);
        memUser.setRegisterAreaCode(areaCode);
        memUser.setUserAccount(areaCode + mobilePhone);
        memUser.setMobileAuthenticated(true);
//        memUser.setSalt(SecurityUtils.getRandomSalt());
//        memUser.setPassword(SecurityUtils.MD5SaltEncrypt(req.getPassword(),memUser.getSalt()));
        memUser.setUserType(UserTypeEnum.general.getCode());
        memUserService.updateById(memUser);

        //????????????????????????
        UpdateWrapper<MemWallet> walletUpdateWrapper=new UpdateWrapper<>();
        walletUpdateWrapper.lambda().set(MemWallet::getAccount,areaCode + mobilePhone);
        walletUpdateWrapper.lambda().eq(MemWallet::getUserId,userId);
        memWalletService.update(walletUpdateWrapper);
        //????????????????????????????????????
        ApiBusinessRedisUtils.setVisitorPhone(memUser.getUserDeviceId());

        //1???redis??????????????????
        AppLoginUser user = new AppLoginUser();
        user.setId(memUser.getId());
        user.setAccno(memUser.getAccno());
        user.setMobilePhone(memUser.getMobilePhone());
        user.setAreaCode(memUser.getRegisterAreaCode());
        user.setUserAccount(memUser.getUserAccount());
        user.setMerchantCode(memUser.getMerchantCode());
        user.setRegisterCountryCode(memUser.getRegisterCountryCode());
        user.setCountryCode(memUser.getRegisterCountryCode());
        user.setLang(LoginInfoUtil.getLang());
        user.setNickName(memUser.getNickName());
        user.setPersonalSignature(memUser.getPersonalSignature());
        user.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
        user.setLevel(memUser.getUserLevel());
        user.setUserType(memUser.getUserType());
        String token = LoginInfoUtil.getToken();
        String jsonStr = JSON.toJSONString(user);
        //????????????token
        ApiBusinessRedisUtils.setAccessToken(memUser, token, jsonStr);
        //????????????token
        UserBusinessRedisUtils.setAppUserToken(memUser.getId(), token);
        //??????????????????
        ApiBusinessRedisUtils.setUserStatus(memUser);
        return ResultInfo.ok();
    }

    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    public ResultInfo<Boolean> checkVisitor() {
        String deviceId = LoginInfoUtil.getDeviceId();
        return ResultInfo.ok(ApiBusinessRedisUtils.getVisitorPhone(deviceId));
    }


    ///////////////////////////////////////////????????????///////////////////////////////////////////////////////////////

    /**
     * ?????????????????????????????????
     *
     * @param memUser
     */
    private void checkingWallet(MemUser memUser) {
        MemWallet wallet = memWalletService.getWalletByMemId(memUser.getId(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (wallet == null) {
            wallet = new MemWallet();
            wallet.setAccount(memUser.getUserAccount());
            wallet.setCreateUser(wallet.getAccount());
            wallet.setWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
            wallet.setUserId(memUser.getId());
            memWalletService.save(wallet);
        }
    }


    /**
     * ?????????????????????
     *
     * @param memUser
     */
    private void initRegister(MemUser memUser) {
        //???????????????
//        MemWallet wallet = new MemWallet();
//        wallet.setBalance(BigDecimal.ZERO);
//        wallet.setUserId(memUser.getId());
//        wallet.setMerchantCode(memUser.getMerchantCode());
//        memWalletService.save(wallet);
        MemWallet wallet = new MemWallet();
        wallet.setAccount(memUser.getUserAccount());
        wallet.setCreateUser(wallet.getAccount());
        wallet.setWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
        wallet.setUserId(memUser.getId());
        memWalletService.save(wallet);


        //?????????????????????
        MemLevelLink levelLink = new MemLevelLink();
        //??????????????????2???????????????id???2????????????0
        levelLink.setLevelId(2l);
        levelLink.setUserId(memUser.getId());
        memLevelLinkService.save(levelLink);

        //??????????????????????????????????????????

        //???????????????
        dealWithInviteCode(memUser);

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

            //TODO ?????????mogodb?????????????????????

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

        //???????????????????????????, ????????????????????????
        // if(isInvite) {
        //??????mq???????????????, ????????????????????????
        //   rabbitTemplate.convertAndSend(RabbitConfig.AGENT_INVITE_USER_EXCHANGE_TOPIC, "invite_user", agentInviteRecord.getId());
        // }

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
     * @param memUser
     * @param isRegister ??????????????????
     * @return
     */
    private AppLoginTokenVo dealLogin(MemUser memUser, String countryCode, Boolean isRegister) {
        //????????????????????????
        this.saveLogs(memUser);
        //??????????????????token
        String acctoken = this.getToken(memUser, countryCode);
        AppLoginTokenVo loginVO = new AppLoginTokenVo();
        loginVO.setAcctoken(acctoken);
        loginVO.setRegisterCountryCode(countryCode);
        return loginVO;
        /*//????????????????????????
        AppLoginVO loginVO = new AppLoginVO();
        loginVO.setAcctoken(acctoken);
        loginVO.setAccno(memUser.getAccno());
        loginVO.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
        loginVO.setUserLevel(memUser.getUserLevel());
        //???????????????
        loginVO.setRegisterTime(memUser.getRegisterTime());
        loginVO.setSex(memUser.getSex());
        loginVO.setAreaCode(memUser.getRegisterAreaCode());
        loginVO.setBirthday(memUser.getBirthday());
        loginVO.setMobilePhone(memUser.getMobilePhone());
        loginVO.setCountryCode(countryCode);

        //????????????
        if (isRegister) {
            loginVO.setAmount(BigDecimal.ZERO.setScale(3, BigDecimal.ROUND_HALF_UP));
        } else {
            MemWallet wallet =   memWalletService.getWalletByMemId(memUser.getId());
            if(wallet != null){
                loginVO.setAmount(wallet.getBalance());
            }
        }
        return loginVO;*/
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
        //1???redis??????????????????
        AppLoginUser user = new AppLoginUser();
        user.setId(memUser.getId());
        user.setAccno(memUser.getAccno());
        user.setMobilePhone(memUser.getMobilePhone());
        user.setAreaCode(memUser.getRegisterAreaCode());
        user.setUserAccount(memUser.getUserAccount());
        user.setMerchantCode(memUser.getMerchantCode());
        user.setRegisterCountryCode(memUser.getRegisterCountryCode());
        user.setCountryCode(countryCode);
        user.setLang(LoginInfoUtil.getLang());
        user.setNickName(memUser.getNickName());
        user.setPersonalSignature(memUser.getPersonalSignature());
        user.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
        user.setLevel(memUser.getUserLevel());
        user.setUserType(memUser.getUserType());

        String jsonStr = JSON.toJSONString(user);
        String acctoken = DigestUtil.md5Hex(tokenPre + IdUtil.fastSimpleUUID());
        //????????????token
        ApiBusinessRedisUtils.setAccessToken(memUser, acctoken, jsonStr);
        //????????????token
        UserBusinessRedisUtils.setAppUserToken(memUser.getId(), acctoken);
        //??????????????????
        ApiBusinessRedisUtils.setUserStatus(memUser);

        //????????????token
        return acctoken;
    }

    /**
     * ????????????????????????
     *
     * @param request
     * @return
     */
    public UserAreaVo checkArea(HttpServletRequest request) {
        String ip = IPAddressUtil.getIpAddress(request);
        String enName = IPAddressUtil.getClientAreaEn(ip);
        // SysCountry country = sysCountryService.getCountryByEnName(enName);
        List<SysCountry> countryList = SysBusinessRedisUtils.getCountryList();// sysCountryService.getAllCountry();
        List<CountryListVo> list = new LinkedList<>();
        UserAreaVo vo = new UserAreaVo();

        for (int i = 0; i < countryList.size(); i++) {
            SysCountry country = countryList.get(i);

            //?????????????????????
            if (i == 0) {
                vo.setCountry(country.getZhName());
                vo.setAreaCode(country.getAreaCode());
                vo.setCountryCode(country.getCountryCode());
            }

            //??????IP??????????????????
            if (country.getEnName().equals(enName)) {
                vo.setCountry(country.getZhName());
                vo.setAreaCode(country.getAreaCode());
                vo.setCountryCode(country.getCountryCode());
            }
            CountryListVo listVo = new CountryListVo();
            listVo.setCountry(country.getZhName());
            listVo.setAreaCode(country.getAreaCode());
            listVo.setCountryCode(country.getCountryCode());
            list.add(listVo);
        }
        vo.setList(list);
        return vo;
    }

    /**
     * ??????????????????
     *
     * @param req
     * @param request
     * @return
     */
    public ResultInfo<Boolean> selectArea(AppVistorChangeAreaReq req, HttpServletRequest request) {
        String token = request.getHeader(HeaderConstants.AUTHORIZATION);
        String deviceId = LoginInfoUtil.getDeviceId();
        String countryCode = req.getCountryCode();

        if (StrUtil.isBlank(countryCode)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        if (StrUtil.isBlank(token) && StrUtil.isBlank(deviceId)) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        log.info("token={}  devideId={}", token, deviceId);

        if (StrUtil.isNotBlank(token)) {
            //????????????, ???????????????
            AppLoginUser user = ApiBusinessRedisUtils.getLoginUserByToken(token);
            if (user == null) {
                //?????????Redis??????
                ApiBusinessRedisUtils.setTouristCountryCode(deviceId, countryCode);
            } else {
                //??????country
                SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
                if (country == null) {
                    throw new BusinessException(StatusCode.PARAM_ERROR);
                }

                MemUser memUser = memUserService.getById(user.getId());
                //??????????????????
//                if (!memUser.getRegisterAreaSelectStatus()) {
//                    throw new BusinessException(StatusCode.REGISTER_COUNTRY_NOT_ALLOW_CHANGE);
//                }

                memUser.setRegisterAreaSelectStatus(false);
                memUser.setCountryId(country.getId());
                memUser.setRegisterCountryCode(countryCode);
                memUser.setRegisterAreaCode(country.getAreaCode());
                memUser.setRegisterArea(country.getZhName());
                //??????????????????code
                memUser.setDefaultCountryCode(countryCode);
                //????????????????????????code
                memUser.setOpenCountryCode(countryCode);
                memUserService.updateById(memUser);
                repushRecordService.addRecordByUser(memUser);
                //????????????????????????
                user.setCountryCode(countryCode);
                user.setRegisterCountryCode(countryCode);
                String jsonStr = JSON.toJSONString(user);
                //????????????token
                ApiBusinessRedisUtils.setAccessToken(memUser, token, jsonStr);
                //????????????????????????
                ApiBusinessRedisUtils.removeTouristCountryCode(LoginInfoUtil.getDeviceId());
            }
        } else {
            //?????????Redis??????
            ApiBusinessRedisUtils.setTouristCountryCode(deviceId, countryCode);
        }


        return ResultInfo.ok();
    }


    /**
     * @param req
     * @return
     */
    public ResultInfo<AppLoginTokenVo> registAndLogin(AppRegistAndLoginReq req) throws Exception {
        String areaCode = req.getAreaCode();
        String mobilePhone = req.getMobilePhone();
        String smsCode = req.getSmsCode();
        String countryCode = req.getCountryCode();

        //????????????????????????
        if (StringUtils.isBlank(areaCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_AREACODE);
        }
        //?????????????????????
        if (StringUtils.isBlank(mobilePhone)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PHONE);
        }
        //???????????????????????????
        if (StringUtils.isBlank(req.getImgCode())) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
        }
        //????????????????????????
        if (StringUtils.isBlank(req.getCaptchaKey())) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGLOGO);
        }
        //?????????????????????
        sysBusiness.checkImgCodeTow(req.getImgCode(), req.getCaptchaKey());
        // ???????????????????????????
        Long defaultHierarchy = configCheck(areaCode);
        //???????????????????????????
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //?????????????????????????????????
        Boolean flag = SmsSendCommonUtils.getVerificationCode(areaCode + mobilePhone, 1,req.getSmsCode());
        if (!flag) {
            throw new BusinessException(StatusCode.SMS_CODE_ERROR);
        }
        //???????????????????????????
        MemUser memUser = memUserService.queryByAccount(areaCode + mobilePhone);
        if (memUser == null) {
            //????????????
            return ResultInfo.ok(registUser(areaCode, mobilePhone, countryCode, defaultHierarchy));
        } else {
            //??????????????????
            if (memUser.getIsFrozen()) {
                return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
            }
            //??????
            return ResultInfo.ok(loginUser(memUser));
        }
    }


    /**
     * ???????????????????????????
     * ????????????????????????????????????????????????
     */
    private Long configCheck(String areaCode) {
        SysParameter accountConfig = sysParameterService.getByCode(SysParameterConstants.ACCOUNT_CONFIG);
        SysParameter platformConfig = sysParameterService.getByCode(SysParameterConstants.PLATFORM_CONFIG);

        Long defaultHierarchy = 1L;
        if (accountConfig != null && StringUtils.isNotEmpty(accountConfig.getParamValue())) {
            AccountConfigDto accountConfigDto = JSON.parseObject(accountConfig.getParamValue(), AccountConfigDto.class);
            // ?????????????????????????????????????????????
            if (StringUtils.isNotEmpty(areaCode)) {
                String phoneAreaCode = accountConfigDto.getPhoneAreaCode();
                List<String> areaCodeList = Splitter.on(",").trimResults().splitToList(phoneAreaCode);
                // ??????????????????????????????????????????????????????
                if (areaCodeList.contains(areaCode)) {
                    throw new BusinessException(StatusCode.PHONE_AREACODE_NOT_USE);
                }
            }
            defaultHierarchy = Long.parseLong(accountConfigDto.getDefaultHierarchy());
        }

        if (platformConfig != null && StringUtils.isNotEmpty(platformConfig.getParamValue())) {
            PlatformConfigDto platformConfigDto = JSON.parseObject(accountConfig.getParamValue(), PlatformConfigDto.class);
            // ?????????????????????
            if (!platformConfigDto.getIsOpenRegister()) {
                throw new BusinessException(StatusCode.PLATFORM_REGISTER_CLOSE);
            }
        }
        return defaultHierarchy;
    }


    /**
     * ????????????
     *
     * @return
     */
    private AppLoginTokenVo registUser(String areaCode, String mobilePhone, String countryCode, Long defaultHierarchy) {
        Date now = new Date();
        MemUser memUser = new MemUser();
        memUser.setAvatar(HeaderImgRamUtil.userHeadImg());
        memUser.setAccno(InviteCodeUtils.accountCode());
        memUser.setSalt(SecurityUtils.getRandomSalt());
        memUser.setMobilePhone(mobilePhone);
        memUser.setRegisterAreaCode(areaCode);
        memUser.setUserAccount(areaCode + mobilePhone);
        //???????????????????????????????????????????????????????????????TODO ????????????redis???
//        SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
        SysCountry country = sysCountryService.getCountryByAreaCode(areaCode);
        countryCode = country.getCountryCode();
        memUser.setRegisterCountryCode(country.getCountryCode());
        //??????????????????code
        memUser.setDefaultCountryCode(country.getCountryCode());
        //????????????????????????code
        memUser.setOpenCountryCode(country.getCountryCode());
        memUser.setCountryId(country.getId());
        memUser.setRegisterArea(country.getZhName());
        memUser.setNickName(InviteCodeUtils.nickName());
        //id???1?????????????????????????????????????????????
        memUser.setGroupId(defaultHierarchy);
        //?????????????????????0
        memUser.setRegisterIp(LoginInfoUtil.getIp());
        memUser.setRegisterTime(now);
        memUser.setRegisterSource(LoginInfoUtil.getSource());

        String deviceType = LoginInfoUtil.getDeviceType();
        if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
            deviceType = deviceType.substring(0, 100);
        }
        memUser.setRegisterDevice(deviceType);
        memUser.setLastLoginIp(memUser.getRegisterIp());
        memUser.setLastLoginTime(now);
        memUser.setLastLoginSource(memUser.getLastLoginSource());
        memUser.setLastLoginArea(memUser.getRegisterArea());
        memUser.setIsFrozen(false);
        memUser.setIsCommission(true);
        memUser.setIsBet(true);
        memUser.setIsDispensing(true);
        memUser.setIsSuperLiveManage(false);
        memUser.setIsOnline(true);
        memUser.setMerchantCode(LoginInfoUtil.getMerchantCode());
        memUser.setUserLevel(0);
        memUser.setSex(SexEnums.UNKNOWN.getCode());
        //??????????????????????????????
        memUser.setMobileAuthenticated(true);
        memUserService.save(memUser);
        //???????????????????????????
        this.initRegister(memUser);
        //??????token?????????????????????
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, true);
        return loginVO;
    }


    /**
     * ??????????????????
     *
     * @param memUser
     * @return
     */
    private AppLoginTokenVo loginUser(MemUser memUser) {
        //???????????? TODO,???????????????????????????
        //?????????????????????????????????
        memUser.setLastLoginIp(LoginInfoUtil.getIp());
        //memUser.setLastLoginArea(IPAddressUtil.getClientArea(memBaseInfo.getLastLoginIp()));
        memUser.setLastLoginTime(DateUtil.date());
        memUser.setLastLoginSource(LoginInfoUtil.getSource());
        String deviceType = LoginInfoUtil.getDeviceType();
        if (StringUtils.isNotBlank(deviceType) && deviceType.length() > 100) {
            deviceType = deviceType.substring(0, 100);
        }
        memUser.setLastLoginDevice(deviceType);
        memUserService.updateById(memUser);
        //app????????????????????????,?????????????????????
        ApiBusinessRedisUtils.deleteAllToken(memUser);
        //??????token?????????????????????
        SysCountry country = sysCountryService.getById(memUser.getCountryId());
        String countryCode = null;
        if (country != null) {
            countryCode = country.getCountryCode();
        }
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, false);
        return loginVO;
    }


    /**
     * ????????????ID???????????????????????????
     *
     * @return
     */
    public BooleanVO checkAccountByDeviceId() {
        QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUser::getUserDeviceId, LoginInfoUtil.getDeviceId()).eq(MemUser::getMerchantCode, LoginInfoUtil.getMerchantCode());
        int count = memUserService.count(queryWrapper);
        BooleanVO vo = new BooleanVO();
        vo.setValue(count > 0);
        return vo;
    }
}
