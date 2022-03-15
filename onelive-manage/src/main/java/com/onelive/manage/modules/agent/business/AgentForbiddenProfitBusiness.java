package com.onelive.manage.modules.agent.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.agent.AgentForbiddenProfitReq;
import com.onelive.common.model.req.agent.ForbiddenAgentRelieveReq;
import com.onelive.common.model.req.agent.ForbiddenAgentReq;
import com.onelive.common.model.vo.agent.AgentForbiddenProfitVo;
import com.onelive.common.mybatis.entity.AgentForbiddenProfit;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.agent.AgentForbiddenProfitService;
import com.onelive.manage.service.mem.MemUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 禁止代理返点
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AgentForbiddenProfitBusiness {

    @Resource
    private AgentForbiddenProfitService agentForbiddenProfitService;

    @Resource
    private MemUserService memUserService;

    /**
     * 被禁止的代理列表
     *
     * @param param
     * @return
     */
    public ResultInfo<PageInfo<AgentForbiddenProfitVo>> getList(@ModelAttribute AgentForbiddenProfitReq param) {
        param.setMerchantCode(LoginInfoUtil.getMerchantCode());
        PageInfo<AgentForbiddenProfitVo> list = agentForbiddenProfitService.getList(param);
        return ResultInfo.ok(list);
    }

    /**
     * 禁止指定的代理用户返点
     *
     * @return
     */
    @Transactional
    public ResultInfo<Boolean> forbiddenAgent(ForbiddenAgentReq req) {

        //查询用户是否存在
        Long userId = req.getUserId();
        if (userId == null) {
            throw new BusinessException("用户ID不能为空!");
        }

        MemUser user = memUserService.getById(userId);
        if (user == null) {
            throw new BusinessException("该用户不存在!");
        }

        //判断用户当前是否禁止状态
        if (!user.getIsCommission()) {
            throw new BusinessException("该用户已是禁止状态!");
        }
        user.setIsCommission(false);
        memUserService.updateById(user);
        //添加禁止记录
        AgentForbiddenProfit forbiddenProfit = new AgentForbiddenProfit();
        forbiddenProfit.setUserId(userId);
        forbiddenProfit.setUserAccount(user.getUserAccount());
        forbiddenProfit.setRemark(req.getRemark());
        forbiddenProfit.setRegistTime(user.getRegisterTime());
        //返回结果
        return ResultInfo.ok(agentForbiddenProfitService.save(forbiddenProfit));
    }

    /**
     * 后台批量解禁
     * @return
     */
    @Transactional
    public ResultInfo<Boolean> relieveForbidden(ForbiddenAgentRelieveReq req) {
        ArrayList<Long> userIds = req.getUserIds();
        if (userIds == null || userIds.size() == 0) {
            throw new BusinessException("ID不能为空!");
        }

        //TODO 事务是否生效需要测试

        //批量更新用户状态
        memUserService.updateCommissionUsers(userIds, true);

        //删除禁止记录
        agentForbiddenProfitService.removeByUserIds(userIds);

        return ResultInfo.ok(Boolean.TRUE);
    }
}
