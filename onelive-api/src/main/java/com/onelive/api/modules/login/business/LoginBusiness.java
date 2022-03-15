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
     * 查询账号是否存在
     *
     * @param req
     * @return
     */
    public Boolean isExistsAccount(ApiCheckAccountReq req) {
        return memUserService.isExistByAccount(req.getUserAccount());
    }


    /**
     * 会员注册
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
        String inviteCode = req.getInviteCode();//邀请码
        String countryCode = req.getCountryCode();

        //手机区号不能为空
        if (StringUtils.isBlank(areaCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_AREACODE);
        }
        // 系统配置参数校验，返回默认层级
        Long defaultHierarchy = configCheck(areaCode);

        //获取所在地区信息失败
        if (StringUtils.isBlank(countryCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_AREACODE);
        }

        //手机号不能为空
        if (StringUtils.isBlank(mobilePhone)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PHONE);
        }
        //密码不能为空
        if (StringUtils.isBlank(password)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
        }

        //短信验证码不能为空
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //验证短信验证码是否正确
        if (isTest) {
            //跳过手机验证码注册
        } else {
            sysShortMsgService.checkSmsCode(mobilePhone, areaCode, smsCode, SendTypeEnum.REGISTER.getCode());
        }

        //图片验证码不能为空
        if (StringUtils.isBlank(imgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
        }
        //获取不了图片验证码标识
        if (StringUtils.isBlank(captchaKey)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }
        //验证图片验证码是否正确
        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(captchaKey);
        if (!imgCode.equalsIgnoreCase(trueImgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }
        //密码不能为空
        if (StringUtils.isBlank(password)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PASSWORD);
        }
        //注册频率限制  TODO,有鉴权功能，先不搞


        //把邀请码存储到工具类中
        LoginInfoUtil.setInviteCode(inviteCode);
        //账号是否存在
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
        //通过注册时候的手机区号，判断用户所在国家
        SysCountry country = SysBusinessRedisUtils.getCountryInfo(countryCode); // sysCountryService.getCountryByCountryCode(countryCode);
        memUser.setRegisterCountryCode(country.getCountryCode());
        //设置默认国家code
        memUser.setDefaultCountryCode(country.getCountryCode());
        //设置开放提现国家code
        memUser.setOpenCountryCode(country.getCountryCode());
        memUser.setCountryId(country.getId());
        memUser.setRegisterArea(country.getZhName());
        memUser.setNickName(InviteCodeUtils.nickName());
        //id为1是正常层级，TODO 层级表初始化一个正常层级 Not stratified
        memUser.setGroupId(defaultHierarchy);
        //用户等级默认是0
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

        //设置手机为已认证状态
        memUser.setMobileAuthenticated(true);

        memUserService.save(memUser);

        //初始化用户其他信息
        this.initRegister(memUser);

        //生成token和其他登录信息
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, true);
        return ResultInfo.ok(loginVO);
    }


    /**
     * 登录
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
        //查询是否有用户信息
        MemUser memUser = memUserService.queryByAccount(areaCode + mobilePhone);
        if (memUser == null) {
            return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
        }
        //密码检验
        password = SecurityUtils.MD5SaltEncrypt(password, memUser.getSalt());
        if (!password.equals(memUser.getPassword())) {
            //登录次数过多，提示用户已锁定，1小时后解锁,5次锁定，TODO 次数配置在配置表
            Integer loginNum = ApiBusinessRedisUtils.getLoginProtect(memUser.getAccno());
            if (loginNum >= 5) {
                return ResultInfo.getInstance(StatusCode.LOGIN_LOCK_PASSWORD);
            } else {
                ApiBusinessRedisUtils.setLoginProtect(memUser.getAccno(), loginNum + 1);
                return ResultInfo.getInstance(StatusCode.LOGIN_WRONG_PASSWORD);
            }
        }
        //账号已被冻结
        if (memUser.getIsFrozen()) {
            return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
        }
        //登录保护 TODO,有鉴权功能，先不搞

        //更新会员表最后登录相关
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
        //检查用户钱包是否初始化
        checkingWallet(memUser);
        //app端逻辑是单点登录,清除之前登录的
        ApiBusinessRedisUtils.deleteAllToken(memUser);
        //生成token和其他登录信息
        SysCountry country = sysCountryService.getById(memUser.getCountryId());

        String countryCode = null;
        if (country != null) {
            countryCode = country.getCountryCode();
        }
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, false);

        return ResultInfo.ok(loginVO);
    }

    /**
     * 游客注册登录
     *
     * @return
     */
    public ResultInfo<AppLoginTokenVo> visitorLogin() {
        AppLoginTokenVo loginVO;
        Long userId = ApiBusinessRedisUtils.getLoginTourist(LoginInfoUtil.getDeviceId());

        //如果为空, 查询一下数据库
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

        //自动注册
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
            //通过注册时候的手机区号，判断用户所在国家，TODO 后面读取redis，
            //SysCountry country = sysCountryService.getCountryByCountryCode(req.getCountryCode());
            String countryCode = ApiBusinessRedisUtils.getTouristCountryCode(LoginInfoUtil.getDeviceId());
            if (StrUtil.isNotBlank(countryCode)) {
                SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
                memUser.setRegisterAreaSelectStatus(false);
                memUser.setRegisterCountryCode(countryCode);
                //设置默认国家code
                memUser.setDefaultCountryCode(countryCode);
                //设置开放提现国家code
                memUser.setOpenCountryCode(countryCode);
                memUser.setCountryId(country.getId());
                memUser.setRegisterArea(country.getZhName());
                memUser.setRegisterAreaCode(country.getAreaCode());
            }
            memUser.setNickName(InviteCodeUtils.nickName());
            //id为1是正常层级，TODO 层级表初始化一个正常层级 Not stratified
            // 系统配置参数校验，返回默认层级
            Long defaultHierarchy = configCheck(null);

            memUser.setGroupId(defaultHierarchy);
            //用户等级默认是0
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

            //记录设备用户id
            ApiBusinessRedisUtils.setLoginTourist(LoginInfoUtil.getDeviceId(), memUser.getId());
            //初始化用户其他信息
            this.initRegister(memUser);

            //生成token和其他登录信息
            loginVO = dealLogin(memUser, countryCode, true);
        } else {
            //游客登录
            MemUser memUser = memUserService.getById(userId);
            if (memUser == null) {
                return ResultInfo.getInstance(StatusCode.LOGIN_NOEXISTS_ACCOUNT);
            }
            //已绑定的手机号，需要走手机登录
            if (memUser.getUserType() == UserTypeEnum.general.getCode()) {
                return ResultInfo.getInstance(StatusCode.LOGIN_RESET_WAY);
            }
            //账号已被冻结
            if (memUser.getIsFrozen()) {
                return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
            }
            //更新会员表最后登录相关
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
                    //设置默认国家code
                    memUser.setDefaultCountryCode(countryCode);
                    //设置开放提现国家code
                    memUser.setOpenCountryCode(countryCode);
                    memUser.setCountryId(country.getId());
                    memUser.setRegisterArea(country.getZhName());
                    memUser.setRegisterAreaCode(country.getAreaCode());
                }
            }

            memUserService.updateById(memUser);
            //检查用户钱包是否初始化
            checkingWallet(memUser);

            //app端逻辑是单点登录,清除之前登录的
            ApiBusinessRedisUtils.deleteAllToken(memUser);
            //生成token和其他登录信息
            String countryCode = null;
            Long countryId = memUser.getCountryId();
            if (countryId != null) {
                SysCountry country = sysCountryService.getById(countryId);
                countryCode = country.getCountryCode();
            }
            loginVO = dealLogin(memUser, countryCode, false);
        }

        //清除国家缓存记录
        ApiBusinessRedisUtils.removeTouristCountryCode(LoginInfoUtil.getDeviceId());

        return ResultInfo.ok(loginVO);
    }

    /**
     * 退出
     *
     * @param account
     * @param token
     */
    public void logout(String account, String token) {
        ApiBusinessRedisUtils.deleteToken(account, token);
    }


    /**
     * 找回密码接口
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

        //通过区号与手机号获取真实账号
        MemUser memUser = memUserService.queryByAccount(areaCode + mobilePhone);
        if (memUser == null) {
            return ResultInfo.getInstance(StatusCode.RESET_EMPTY_PASSWORD);
        }

        //短信验证码不能为空
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //验证短信验证码是否正确
        sysShortMsgService.checkSmsCode(mobilePhone, areaCode, smsCode, SendTypeEnum.CHANGEPASSWORD.getCode());

        //图片验证码不能为空
        if (StringUtils.isBlank(imgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
        }
        //获取不了图片验证码标识
        if (StringUtils.isBlank(captchaKey)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }
        //验证图片验证码是否正确
        String trueImgCode = ApiBusinessRedisUtils.getCaptchaKey(captchaKey);
        if (imgCode.equalsIgnoreCase(trueImgCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
        }

        memUser.setPassword(SecurityUtils.MD5SaltEncrypt(req.getPassword(), memUser.getSalt()));
        memUserService.updateById(memUser);

        return ResultInfo.ok();
    }


    /**
     * 绑定手机接口
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

        //通过区号与手机号获取真实账号
        Long userId = LoginInfoUtil.getUserId();
        MemUser memUser = memUserService.getById(userId);
        if (memUser == null) {
            return ResultInfo.getInstance(StatusCode.RESET_NO_USER_ERROR);
        }
        //短信验证码不能为空
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //验证短信验证码是否正确
        Boolean flag = SmsSendCommonUtils.getVerificationCode(areaCode + mobilePhone, 3,req.getSmsCode());
        if (!flag) {
            throw new BusinessException(StatusCode.SMS_CODE_ERROR);
        }

        //图片验证码不能为空
//        if(StringUtils.isBlank(imgCode)){
//            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
//        }
//        //获取不了图片验证码标识
//        if(StringUtils.isBlank(captchaKey)){
//            return ResultInfo.getInstance(StatusCode.REGISTER_WRONG_IMG);
//        }
//        //验证图片验证码是否正确
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

        //修改游客钱包信息
        UpdateWrapper<MemWallet> walletUpdateWrapper=new UpdateWrapper<>();
        walletUpdateWrapper.lambda().set(MemWallet::getAccount,areaCode + mobilePhone);
        walletUpdateWrapper.lambda().eq(MemWallet::getUserId,userId);
        memWalletService.update(walletUpdateWrapper);
        //记录设备是否已绑定手机号
        ApiBusinessRedisUtils.setVisitorPhone(memUser.getUserDeviceId());

        //1、redis存储登录信息
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
        //设置用户token
        ApiBusinessRedisUtils.setAccessToken(memUser, token, jsonStr);
        //保存用户token
        UserBusinessRedisUtils.setAppUserToken(memUser.getId(), token);
        //设置用户状态
        ApiBusinessRedisUtils.setUserStatus(memUser);
        return ResultInfo.ok();
    }

    /**
     * 判断是否可以走游客注册登录
     *
     * @return
     */
    public ResultInfo<Boolean> checkVisitor() {
        String deviceId = LoginInfoUtil.getDeviceId();
        return ResultInfo.ok(ApiBusinessRedisUtils.getVisitorPhone(deviceId));
    }


    ///////////////////////////////////////////私有方法///////////////////////////////////////////////////////////////

    /**
     * 检查用户钱包是否初始化
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
     * 初始化用户信息
     *
     * @param memUser
     */
    private void initRegister(MemUser memUser) {
        //初始化钱包
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


        //初始化用户等级
        MemLevelLink levelLink = new MemLevelLink();
        //初始化数据，2对应数据库id为2，等级是0
        levelLink.setLevelId(2l);
        levelLink.setUserId(memUser.getId());
        memLevelLinkService.save(levelLink);

        //。。。。后续再加，例如邀请码

        //邀请码相关
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

            //TODO 后面走mogodb或缓存提升效率

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

        //如果是被邀请注册的, 需要异步处理信息
        // if(isInvite) {
        //发送mq消息到队列, 异步更新邀请关系
        //   rabbitTemplate.convertAndSend(RabbitConfig.AGENT_INVITE_USER_EXCHANGE_TOPIC, "invite_user", agentInviteRecord.getId());
        // }

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
     * 登录信息处理
     *
     * @param memUser
     * @param isRegister 是否刚注册过
     * @return
     */
    private AppLoginTokenVo dealLogin(MemUser memUser, String countryCode, Boolean isRegister) {
        //记录用户登录日志
        this.saveLogs(memUser);
        //获取用户登录token
        String acctoken = this.getToken(memUser, countryCode);
        AppLoginTokenVo loginVO = new AppLoginTokenVo();
        loginVO.setAcctoken(acctoken);
        loginVO.setRegisterCountryCode(countryCode);
        return loginVO;
        /*//返回到前端的信息
        AppLoginVO loginVO = new AppLoginVO();
        loginVO.setAcctoken(acctoken);
        loginVO.setAccno(memUser.getAccno());
        loginVO.setAvatar(AWSS3Util.getAbsoluteUrl(memUser.getAvatar()));
        loginVO.setUserLevel(memUser.getUserLevel());
        //注册时间戳
        loginVO.setRegisterTime(memUser.getRegisterTime());
        loginVO.setSex(memUser.getSex());
        loginVO.setAreaCode(memUser.getRegisterAreaCode());
        loginVO.setBirthday(memUser.getBirthday());
        loginVO.setMobilePhone(memUser.getMobilePhone());
        loginVO.setCountryCode(countryCode);

        //用户余额
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
        //1、redis存储登录信息
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
        //设置用户token
        ApiBusinessRedisUtils.setAccessToken(memUser, acctoken, jsonStr);
        //保存用户token
        UserBusinessRedisUtils.setAppUserToken(memUser.getId(), acctoken);
        //设置用户状态
        ApiBusinessRedisUtils.setUserStatus(memUser);

        //返回用户token
        return acctoken;
    }

    /**
     * 检测用户所在地区
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

            //默认第一个数据
            if (i == 0) {
                vo.setCountry(country.getZhName());
                vo.setAreaCode(country.getAreaCode());
                vo.setCountryCode(country.getCountryCode());
            }

            //根据IP查询到的数据
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
     * 游客切换地区
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
            //查询用户, 更新用户表
            AppLoginUser user = ApiBusinessRedisUtils.getLoginUserByToken(token);
            if (user == null) {
                //保存到Redis缓存
                ApiBusinessRedisUtils.setTouristCountryCode(deviceId, countryCode);
            } else {
                //查询country
                SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
                if (country == null) {
                    throw new BusinessException(StatusCode.PARAM_ERROR);
                }

                MemUser memUser = memUserService.getById(user.getId());
                //暂时放开限制
//                if (!memUser.getRegisterAreaSelectStatus()) {
//                    throw new BusinessException(StatusCode.REGISTER_COUNTRY_NOT_ALLOW_CHANGE);
//                }

                memUser.setRegisterAreaSelectStatus(false);
                memUser.setCountryId(country.getId());
                memUser.setRegisterCountryCode(countryCode);
                memUser.setRegisterAreaCode(country.getAreaCode());
                memUser.setRegisterArea(country.getZhName());
                //设置默认国家code
                memUser.setDefaultCountryCode(countryCode);
                //设置开放提现国家code
                memUser.setOpenCountryCode(countryCode);
                memUserService.updateById(memUser);
                repushRecordService.addRecordByUser(memUser);
                //更新登陆缓存数据
                user.setCountryCode(countryCode);
                user.setRegisterCountryCode(countryCode);
                String jsonStr = JSON.toJSONString(user);
                //设置用户token
                ApiBusinessRedisUtils.setAccessToken(memUser, token, jsonStr);
                //清除国家缓存记录
                ApiBusinessRedisUtils.removeTouristCountryCode(LoginInfoUtil.getDeviceId());
            }
        } else {
            //保存到Redis缓存
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

        //手机区号不能为空
        if (StringUtils.isBlank(areaCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_AREACODE);
        }
        //手机号不能为空
        if (StringUtils.isBlank(mobilePhone)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_PHONE);
        }
        //图片验证码不能为空
        if (StringUtils.isBlank(req.getImgCode())) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGCODE);
        }
        //图片标识不能为空
        if (StringUtils.isBlank(req.getCaptchaKey())) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_IMGLOGO);
        }
        //验证图片验证码
        sysBusiness.checkImgCodeTow(req.getImgCode(), req.getCaptchaKey());
        // 注册时校验系统参数
        Long defaultHierarchy = configCheck(areaCode);
        //短信验证码不能为空
        if (StringUtils.isBlank(smsCode)) {
            return ResultInfo.getInstance(StatusCode.REGISTER_BLANK_SMSCODE);
        }
        //验证短信验证码是否正确
        Boolean flag = SmsSendCommonUtils.getVerificationCode(areaCode + mobilePhone, 1,req.getSmsCode());
        if (!flag) {
            throw new BusinessException(StatusCode.SMS_CODE_ERROR);
        }
        //查询是否有用户信息
        MemUser memUser = memUserService.queryByAccount(areaCode + mobilePhone);
        if (memUser == null) {
            //注册账号
            return ResultInfo.ok(registUser(areaCode, mobilePhone, countryCode, defaultHierarchy));
        } else {
            //账号已被冻结
            if (memUser.getIsFrozen()) {
                return ResultInfo.getInstance(StatusCode.LOGIN_FROZEN);
            }
            //登陆
            return ResultInfo.ok(loginUser(memUser));
        }
    }


    /**
     * 注册时校验系统参数
     * 是否开放注册，手机区号，默认层级
     */
    private Long configCheck(String areaCode) {
        SysParameter accountConfig = sysParameterService.getByCode(SysParameterConstants.ACCOUNT_CONFIG);
        SysParameter platformConfig = sysParameterService.getByCode(SysParameterConstants.PLATFORM_CONFIG);

        Long defaultHierarchy = 1L;
        if (accountConfig != null && StringUtils.isNotEmpty(accountConfig.getParamValue())) {
            AccountConfigDto accountConfigDto = JSON.parseObject(accountConfig.getParamValue(), AccountConfigDto.class);
            // 区号不为空才校验，兼容游客注册
            if (StringUtils.isNotEmpty(areaCode)) {
                String phoneAreaCode = accountConfigDto.getPhoneAreaCode();
                List<String> areaCodeList = Splitter.on(",").trimResults().splitToList(phoneAreaCode);
                // 如果不在可注册的配置区号内，注册失败
                if (areaCodeList.contains(areaCode)) {
                    throw new BusinessException(StatusCode.PHONE_AREACODE_NOT_USE);
                }
            }
            defaultHierarchy = Long.parseLong(accountConfigDto.getDefaultHierarchy());
        }

        if (platformConfig != null && StringUtils.isNotEmpty(platformConfig.getParamValue())) {
            PlatformConfigDto platformConfigDto = JSON.parseObject(accountConfig.getParamValue(), PlatformConfigDto.class);
            // 平台未开放注册
            if (!platformConfigDto.getIsOpenRegister()) {
                throw new BusinessException(StatusCode.PLATFORM_REGISTER_CLOSE);
            }
        }
        return defaultHierarchy;
    }


    /**
     * 注册用户
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
        //通过注册时候的手机区号，判断用户所在国家，TODO 后面读取redis，
//        SysCountry country = sysCountryService.getCountryByCountryCode(countryCode);
        SysCountry country = sysCountryService.getCountryByAreaCode(areaCode);
        countryCode = country.getCountryCode();
        memUser.setRegisterCountryCode(country.getCountryCode());
        //设置默认国家code
        memUser.setDefaultCountryCode(country.getCountryCode());
        //设置开放提现国家code
        memUser.setOpenCountryCode(country.getCountryCode());
        memUser.setCountryId(country.getId());
        memUser.setRegisterArea(country.getZhName());
        memUser.setNickName(InviteCodeUtils.nickName());
        //id为1是正常层级，读取配置的默认层级
        memUser.setGroupId(defaultHierarchy);
        //用户等级默认是0
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
        //设置手机为已认证状态
        memUser.setMobileAuthenticated(true);
        memUserService.save(memUser);
        //初始化用户其他信息
        this.initRegister(memUser);
        //生成token和其他登录信息
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, true);
        return loginVO;
    }


    /**
     * 用户登陆逻辑
     *
     * @param memUser
     * @return
     */
    private AppLoginTokenVo loginUser(MemUser memUser) {
        //登录保护 TODO,有鉴权功能，先不搞
        //更新会员表最后登录相关
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
        //app端逻辑是单点登录,清除之前登录的
        ApiBusinessRedisUtils.deleteAllToken(memUser);
        //生成token和其他登录信息
        SysCountry country = sysCountryService.getById(memUser.getCountryId());
        String countryCode = null;
        if (country != null) {
            countryCode = country.getCountryCode();
        }
        AppLoginTokenVo loginVO = dealLogin(memUser, countryCode, false);
        return loginVO;
    }


    /**
     * 根据设备ID查询是否有账号存在
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
