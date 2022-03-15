package com.onelive.manage.modules.agent.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.InviteCodeConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.agent.*;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.vo.agent.AgentInviteCodeInfoVo;
import com.onelive.common.model.vo.agent.AgentInviteCodeVo;
import com.onelive.common.model.vo.agent.SearchUserVo;
import com.onelive.common.mybatis.entity.AgentInviteCode;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.InviteCodeUtils;
import com.onelive.manage.service.agent.AgentInviteCodeService;
import com.onelive.manage.service.mem.MemUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 邀请码相关功能
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AgentInvitCodeBusiness {

    @Resource
    private AgentInviteCodeService agentInviteCodeService;

    @Resource
    private MemUserService memUserService;


    /**
     * 邀请码列表查询
     * @param param
     * @return
     */
    public ResultInfo<PageInfo<AgentInviteCodeVo>> getList(@ModelAttribute AgentInviteCodeReq param) {
        param.setMerchantCode(LoginInfoUtil.getMerchantCode());
        PageInfo<AgentInviteCodeVo> list = agentInviteCodeService.getList(param);
        return ResultInfo.ok(list);
    }

    /**
     * 后台添加邀请码
     * @param req
     * @return
     */
    public ResultInfo<Boolean> addInviteCode(AgentInviteCodeAddReq req) {

        System.out.println(req);


        //查询用户是否存在
        Long userId = req.getUserId();
        if(userId==null){
            throw new BusinessException("用户ID不能为空!");
        }

        MemUser user = memUserService.getById(userId);
        if(user==null){
            throw new BusinessException("该用户不存在!");
        }
        String inviteCode = req.getInviteCode();
        //查询添加的邀请码是否已经存在
        AgentInviteCode dbInviteCode = agentInviteCodeService.getOneByInviteCode(inviteCode);
        if(dbInviteCode!=null){
            throw new BusinessException("该邀请码已经存在!");
        }

        //查询用户系统生成的邀请码
        AgentInviteCode sysInviteCode = agentInviteCodeService.getSysInviteCodeByUserId(userId);

        Boolean status = req.getStatus();//设置邀请码状态

        AgentInviteCode newInviteCode = new AgentInviteCode();
        newInviteCode.setInviteCode(inviteCode);
        newInviteCode.setUserId(userId);
        newInviteCode.setUserAccount(user.getUserAccount());
        newInviteCode.setIsSys(false);
        newInviteCode.setInviteUserId(0L);
        newInviteCode.setIsAutoCreate(false);
        newInviteCode.setInviteUserAccount(InviteCodeConstants.INVITE_CODE_PLATFORM);
        int level = 1;
        Long rootUserId = user.getId();

        if(sysInviteCode!=null){
            level = sysInviteCode.getAgentLevel();
            rootUserId = sysInviteCode.getRootUserId();
        }

        newInviteCode.setAgentLevel(level);
        newInviteCode.setRootUserId(rootUserId);
        newInviteCode.setStatus(status);
        return ResultInfo.ok(agentInviteCodeService.save(newInviteCode));
    }

    /**
     * 后台批量删除邀请码
     * @return
     */
    public ResultInfo<Boolean> delInviteCode(AgentInviteCodeDeleteReq req) {
        ArrayList<Long> ids = req.getIds();
        if(ids==null || ids.size()==0){
            throw new BusinessException("邀请码ID不能为空!");
        }
        return ResultInfo.ok(agentInviteCodeService.removeByIds(ids));
    }

    /**
     * 查询用户信息
     * @return
     */
    public ResultInfo<SearchUserVo> searchUser(SearchUserReq req) {
        String userAccount = req.getUserAccount();

        if(StrUtil.isBlank(userAccount)){
            throw new BusinessException("账号不能为空");
        }

        MemUser memUser = memUserService.queryByAccount(userAccount);

        if(memUser==null){
            throw new BusinessException("账号不存在");
        }

        //TODO 用户层级还没有, 后面需改为联表查询
        SearchUserVo vo = new SearchUserVo();
        BeanUtil.copyProperties(memUser,vo);

        //生成一个邀请码
        String inviteCode = InviteCodeUtils.inviteCode();
        vo.setInviteCode(inviteCode);
        //默认启用
        vo.setStatus(true);
        return ResultInfo.ok(vo);
    }

    /**
     * 查询邀请码信息
     * @param req
     * @return
     */
    public AgentInviteCodeInfoVo getInviteCodeInfo(LongIdReq req) {
        if(req.getId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        AgentInviteCode inviteCode = agentInviteCodeService.getById(req.getId());
        AgentInviteCodeInfoVo vo = new AgentInviteCodeInfoVo();
        BeanCopyUtil.copyProperties(inviteCode,vo);
        return vo;
    }

    /**
     * 更新邀请码状态
     * @param req
     * @return
     */
    public ResultInfo<Boolean> changeInviteCodeStatus(AgentInviteCodeChangeStatusReq req) {
        if(req.getIds()==null || req.getIds().size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        agentInviteCodeService.updateStatusByIds(req.getIds(),req.getStatus());
        return ResultInfo.ok();
    }
}
