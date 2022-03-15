package com.onelive.manage.modules.mem.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.InviteCodeConstants;
import com.onelive.common.enums.SexEnums;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.mem.*;
import com.onelive.common.model.vo.mem.*;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.utils.img.HeaderImgRamUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.InviteCodeUtils;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.redis.UserBusinessRedisUtils;
import com.onelive.manage.service.agent.AgentForbiddenProfitService;
import com.onelive.manage.service.agent.AgentInviteCodeService;
import com.onelive.manage.service.agent.AgentInviteRecordService;
import com.onelive.manage.service.mem.MemLevelLinkService;
import com.onelive.manage.service.mem.MemLoginRecordService;
import com.onelive.manage.service.mem.MemUserService;
import com.onelive.manage.service.mem.MemWalletService;
import com.onelive.manage.service.sys.SysCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * 用户列表
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemUserListBusiness {

    @Resource
    private MemUserService memUserService;
    @Resource
    private MemLoginRecordService memLoginRecordService;

    @Resource
    private SysCountryService sysCountryService;

    @Resource
    private MemLevelLinkService memLevelLinkService;

    @Resource
    private AgentInviteCodeService agentInviteCodeService;

    @Resource
    private AgentInviteRecordService agentInviteRecordService;

    @Resource
    private AgentForbiddenProfitService agentForbiddenProfitService;

    @Resource
    private MemWalletService memWalletService;
    @Resource
    private MemUserMsgRepushRecordService repushRecordService;

    public ResultInfo<PageInfo<MemUserListVO>> getList(MemUserListReq req) {
        PageInfo<MemUserListVO> list = memUserService.getList(req);
        return ResultInfo.ok(list);
    }

    /**
     * 后台批量封停/解封用户
     * @return
     */
    public ResultInfo<Boolean> updateFrozenUsers(MemUserUpdateFrozenReq req) {
        if (req.getIsFrozen() == null) {
            throw new BusinessException("缺少参数! isFrozen不能为空");
        }
        ArrayList<Long> userIds = req.getUserIds();
        if (userIds == null || userIds.size() == 0) {
            throw new BusinessException("缺少参数! 用户ID不能为空");
        }
        memUserService.updateFrozenUsers(req);
        return ResultInfo.ok(Boolean.TRUE);
    }

    /**
     * 新增用户
     *
     * @param req
     * @param loginUser
     * @return
     */
    @Transactional
    public ResultInfo<Boolean> addUser(MemUserAddReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
//        if (StrUtil.isBlank(req.getAccno())) {
//            throw new BusinessException("ID不能为空");
//        }
        String areaCode = req.getRegisterAreaCode();
        if (StrUtil.isBlank(areaCode)) {
            throw new BusinessException("注册区号不能为空");
        }
        if (StrUtil.isBlank(req.getMobilePhone())) {
            throw new BusinessException("登陆账号不能为空");
        }
       /* if (StrUtil.isBlank(req.getPassword())) {
            throw new BusinessException("登陆密码不能为空");
        }
        if (StrUtil.isBlank(req.getRepassword())) {
            throw new BusinessException("确认登陆密码不能为空");
        }

        if (!req.getPassword().equals(req.getRepassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }*/

        if (req.getGroupId() == null) {
            throw new BusinessException("用户层级不能为空");
        }

        //账号是否存在
        Boolean accountExists = memUserService.isExistByAccount(areaCode + req.getMobilePhone());
        if (accountExists) {
            throw new BusinessException("该账号已存在");
        }

        //如果没有选择用户等级, 默认为普通用户
        if (req.getUserLevel() == null) {
            req.setUserLevel(0);
        }


        Date now = new Date();
        MemUser memUser = new MemUser();
        BeanUtil.copyProperties(req, memUser);
        memUser.setAccno(InviteCodeUtils.accountCode());
        memUser.setCreatedBy(loginUser.getAccLogin());
        memUser.setAvatar(HeaderImgRamUtil.userHeadImg());
//        memUser.setSalt(SecurityUtils.getRandomSalt());
//        memUser.setPassword(SecurityUtils.MD5SaltEncrypt(req.getPassword(), memUser.getSalt()));
        memUser.setUserAccount(areaCode + req.getMobilePhone());
        //通过注册时候的手机区号，判断用户所在国家，TODO 后面读取redis，
        SysCountry country = sysCountryService.getCountryByAreaCode(areaCode);
        memUser.setRegisterCountryCode(country.getCountryCode());
        //设置默认国家code
        memUser.setDefaultCountryCode(country.getCountryCode());
        //设置开放提现国家code
        memUser.setOpenCountryCode(country.getCountryCode());
        memUser.setCountryId(country.getId());
        memUser.setRegisterArea(country.getZhName());
        memUser.setRegisterTime(now);
        memUser.setRegisterSource("platform");

        memUser.setIsFrozen(false);
        memUser.setIsCommission(true);
        memUser.setIsBet(true);
        memUser.setIsDispensing(true);
        memUser.setIsSuperLiveManage(false);
        memUser.setIsOnline(false);
        memUser.setMerchantCode("0");
        memUser.setSex(SexEnums.UNKNOWN.getCode());
        //设置手机为已认证状态
        memUser.setMobileAuthenticated(true);
        memUserService.save(memUser);

        //初始化其他相关信息
        initRegister(memUser);

        //邀请码相关
        dealWithInviteCode(memUser,req);
        return ResultInfo.ok(Boolean.TRUE);
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
        //钱包信息-其他信息已在数据库赋默认值
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
    }

    /**
     * 处理邀请码相关逻辑
     *
     * @param memUser
     * @param req
     */
    private void dealWithInviteCode(MemUser memUser, MemUserAddReq req) {
        //注册成功后, 检查是否有邀请码,创建邀请关系记录,

        int level = 1; //默认代理等级
        Long rootUserId = memUser.getId();//默认自己是根用户
        Long inviteUserId = 0L;
        String inviteUserAccount = InviteCodeConstants.INVITE_CODE_PLATFORM;

        //获取上级账号
        String parentUserAccount = req.getParentUserAccount();
        if(StrUtil.isNotBlank(parentUserAccount)){
            MemUser parentUser = memUserService.queryByAccount(parentUserAccount);
            if(parentUser!=null){
                inviteUserId = parentUser.getId();
                inviteUserAccount = parentUserAccount;
                AgentInviteCode parentInviteCode = agentInviteCodeService.getSysInviteCodeByUserId(inviteUserId);
                if(parentInviteCode!=null){
                    rootUserId = parentInviteCode.getRootUserId();
                    level = parentInviteCode.getAgentLevel() + 1;
                }
            }
        }


        //生成邀请记录
        AgentInviteRecord agentInviteRecord = new AgentInviteRecord();
        agentInviteRecord.setUserId(memUser.getId());
        agentInviteRecord.setInviteCode(InviteCodeConstants.INVITE_CODE_PLATFORM);
        agentInviteRecord.setInviteUserId(inviteUserId);
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
                agentInviteCode.setIsAutoCreate(false);
                agentInviteCode.setInviteUserId(inviteUserId);
                agentInviteCode.setInviteUserAccount(inviteUserAccount);
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
     * 查询用户状态
     */
    public MemUserSetVO getUserSet(Long userId) {
        MemUser user = memUserService.getById(userId);
        MemUserSetVO vo = new MemUserSetVO();
        BeanUtil.copyProperties(user,vo);
        return vo;
    }


    /**
     * 强制用户下线
     * @param userId
     * @return
     */
    public ResultInfo<Boolean> forceOffline(Long userId) {
        MemUser user = memUserService.getById(userId);
        if(user==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        UserBusinessRedisUtils.deleteAllToken(user);
        return ResultInfo.ok(Boolean.TRUE);
    }


    /**
     * 后台设置用户状态
     * @param req
     * @param loginUser
     * @return
     */
    @Transactional
    public ResultInfo<Boolean> setUserStatus(MemUserSetStatusReq req, LoginUser loginUser) {

        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        Long userId = req.getId();
        MemUser user = memUserService.getById(userId);
        if(user==null){
            throw new BusinessException("用户不存在");
        }

        //检查是否禁用代理返点状态,对应操作
        if(req.getIsCommission()){
            //删除禁止记录
            if(!user.getIsCommission()){
                ArrayList<Long> userIds = new ArrayList<>();
                userIds.add(userId);
                //删除禁止记录
                agentForbiddenProfitService.removeByUserIds(userIds);
            }
        }else {
            //增加禁止记录
            if(user.getIsCommission()){
                //添加禁止记录
                AgentForbiddenProfit forbiddenProfit = new AgentForbiddenProfit();
                forbiddenProfit.setUserId(userId);
                forbiddenProfit.setUserAccount(user.getUserAccount());
                forbiddenProfit.setRemark("后台禁止返点");
                forbiddenProfit.setRegistTime(user.getRegisterTime());
                agentForbiddenProfitService.save(forbiddenProfit);
            }
        }

        BeanUtil.copyProperties(req,user);
        user.setUpdateBy(loginUser.getAccLogin());
        memUserService.updateById(user);

        return ResultInfo.ok(Boolean.TRUE);
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public ResultInfo<MemUserVO> getUserVoById(Long id) {
        MemUser user = memUserService.getById(id);
        if(user==null){
            throw new BusinessException("该用户不存在");
        }
        MemUserVO vo = new MemUserVO();
        BeanCopyUtil.copyProperties(user,vo);
        //查询用户的上级
        AgentInviteCode sysInviteCode = agentInviteCodeService.getSysInviteCodeByUserId(id);
        if(sysInviteCode!=null){
            vo.setParentUserAccount(sysInviteCode.getInviteUserAccount());
        }
        return ResultInfo.ok(vo);
    }

    /**
     * 更新用户
     * @param req
     * @param loginUser
     * @return
     */
    public ResultInfo<Boolean> updateUser(MemUserUpdateReq req, LoginUser loginUser) {

        if(loginUser==null){
            throw new BusinessException(401, "没有权限操作");
        }

        Long userId = req.getId();

        if(userId==null){
            throw new BusinessException("用户ID不能为空");
        }

        MemUser user = memUserService.getById(userId);
        if(user==null){
            throw new BusinessException("用户不存在");
        }

        BeanCopyUtil.copyProperties(req,user);

        memUserService.updateById(user);
        repushRecordService.addRecordByUser(user);

        return ResultInfo.ok(Boolean.TRUE);
    }

    /**
     * 查询用户的登陆情况
     * @return
     */
    public PageInfo<MemUserLoginVO> queryLoginInfo(MemUserLoginReq req) {

        return memLoginRecordService.queryLoginInfo(req);
    }


    /**
     * 	查询用于选择的用户列表
     * @param req
     * @return
     */
    public PageInfo<MemUserSelectListVO> getSelectUserList(MemUserSelectReq req) {
        QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
        PageInfo<MemUser> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> memUserService.list(queryWrapper));
        return PageInfoUtil.pageInfo2DTO(pageInfo,MemUserSelectListVO.class);
    }
}
