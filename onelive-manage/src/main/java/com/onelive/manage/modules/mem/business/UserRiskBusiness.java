package com.onelive.manage.modules.mem.business;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.mem.UserRiskListVO;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.mem.MemUserService;

import lombok.extern.slf4j.Slf4j;


/**
 * 用户风控
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRiskBusiness {


    @Resource
    private MemUserService memUserService;


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<UserRiskListVO> getRiskList(String startDate, String endDate, Long countryId, String equip, String userAccount, String ip,Integer pageNum, Integer pageSize) {
        PageInfo<MemUser> pageInfo = memUserService.getRiskList(startDate,endDate,countryId, equip,userAccount,ip,pageNum, pageSize);
        return PageInfoUtil.pageInfo2DTO(pageInfo, UserRiskListVO.class);
    }

   
}
    