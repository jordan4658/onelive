package com.onelive.manage.modules.finance.business;


import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ctc.wstx.util.DataUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.mem.UserGroupCountDTO;
import com.onelive.common.model.req.mem.*;
import com.onelive.common.model.vo.mem.MemGroupCurrencyCfgVO;
import com.onelive.common.model.vo.mem.MemUserGroupManageVO;
import com.onelive.common.model.vo.mem.MemUserGroupVO;
import com.onelive.common.mybatis.entity.MemGroupCurrencyCfg;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserGroup;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.service.mem.MemUserMsgRepushRecordService;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.mem.MemGroupCurrencyCfgService;
import com.onelive.manage.service.mem.MemUserGroupService;
import com.onelive.manage.service.mem.MemUserService;
import com.onelive.manage.service.mem.MemWalletService;
import com.onelive.manage.service.sys.SysCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MemGroupBusiness {

    @Resource
    private MemUserGroupService memUserGroupService;

    @Resource
    private MemGroupCurrencyCfgService groupCurrencyCfgService;

    @Resource
    private MemUserService memUserService;

    @Resource
    private SysCountryService sysCountryService;

    @Resource
    private MemUserMsgRepushRecordService memUserMsgRepushRecordService;


    public PageInfo<MemUserGroupVO> pageList(Integer pageSize, Integer pageNum, String currencyCode) {
        PageInfo<MemUserGroupVO> pageInfo = memUserGroupService.pageList(pageSize, pageNum, currencyCode);
        List<Long> groupIds = pageInfo.getList().stream().map(vo -> {
            return vo.getUserGroupId();
        }).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(groupIds)) {
            return pageInfo;
        }
        Long currencyId=null;
        if(StringUtils.isNotEmpty(currencyCode)){
            SysCountry sysCountry=sysCountryService.getCountryByLocalCurrency(currencyCode);
            currencyId= sysCountry.getId();
        }

        //查询对应的层级用户人数
        List<UserGroupCountDTO> groupCountList = memUserService.getByUserGroupId(groupIds,currencyId);
        //查询对应的配置信息
        List<MemGroupCurrencyCfgVO> cfgList = groupCurrencyCfgService.getByUserGroupIds(groupIds);
        List<MemUserGroupVO> newList = pageInfo.getList().stream().map(vo -> {
            List<MemGroupCurrencyCfgVO> filterList = cfgList.stream().filter(cfg -> cfg.getUserGroupId().equals(vo.getUserGroupId())).collect(Collectors.toList());
            Long count = 0L;
            if (!CollectionUtil.isEmpty(groupCountList)) {
                for (UserGroupCountDTO groupCount : groupCountList) {
                    if (groupCount.getUserGroupId().equals(vo.getUserGroupId())) {
                        count = groupCount.getCountTotal();
                    }
                }
            }
            vo.setUserCount(count);
            vo.setGroupCurrencyCfg(filterList);
            return vo;
        }).collect(Collectors.toList());
        pageInfo.setList(newList);
        return pageInfo;
    }

    @Transactional
    public Boolean add(MemUserGroupAddReq req, LoginUser admin) throws Exception {
        if (StringUtils.isEmpty(req.getCurrencyCode())) {
            throw new BusinessException("国家编码为空！");
        }
        if (StringUtils.isEmpty(req.getGroupName())) {
            throw new BusinessException("用户层级名称为空！");
        }
        if (req.getIsInvitationRebates()==null) {
            throw new BusinessException("是否邀请返点为空！");
        }
        if (req.getDepositTimes() == null || req.getDepositTimes() < 0) {
            throw new BusinessException("入款次数为空或小于0！");
        }
        if (req.getWithdrawalsTimes() == null || req.getWithdrawalsTimes() < 0) {
            throw new BusinessException("出款次数为空或小于0！");
        }
        if (req.getStartTime() == null) {
            throw new BusinessException("有效开始时间为空！");
        }
        if (req.getEndTime() == null) {
            throw new BusinessException("有效结束时间为空！");
        }
        if (req.getEndTime().getTime() <= req.getStartTime().getTime()) {
            throw new BusinessException("有效结束时间 必须大于 有效开始时间！");
        }
        if (CollectionUtil.isEmpty(req.getGroupCurrencyCfgAddReq())) {
            throw new BusinessException("用户层级配置信为空！");
        }
        for (MemGroupCurrencyCfgAddReq cfgAddReq : req.getGroupCurrencyCfgAddReq()) {
            if (StringUtils.isEmpty(cfgAddReq.getCurrencyCode())) {
                throw new BusinessException("用户层级配置信息 国家币种为空！");
            }
            if (cfgAddReq.getMaxDeposit() == null || cfgAddReq.getMaxDeposit().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("用户层级配置信息 最大存款金额为空或小于0！");
            }
            if (cfgAddReq.getTotalDeposit() == null || cfgAddReq.getTotalDeposit().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("用户层级配置信息 入款总额为空或小于0！");
            }
            if (cfgAddReq.getTotalDispensing() == null || cfgAddReq.getTotalDispensing().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("用户层级配置信息 出款总额为空或小于0！");
            }

        }
        //保存用户层级基本信息
        Date date = new Date();
        MemUserGroup memUserGroup = new MemUserGroup();
        BeanUtils.copyProperties(req, memUserGroup);
        memUserGroup.setCreateUser(admin.getAccLogin());
        memUserGroup.setUpdateUser(admin.getAccLogin());
        memUserGroup.setCreateTime(date);
        memUserGroup.setUpdateTime(date);
        memUserGroup.setIsDelete(false);
        memUserGroupService.save(memUserGroup);
        //保存用户层级配置信息
        Date dateCfg = new Date();
        List<MemGroupCurrencyCfg> listCfg = BeanCopyUtil.copyCollection(req.getGroupCurrencyCfgAddReq(), MemGroupCurrencyCfg.class);
        for (MemGroupCurrencyCfg cfg : listCfg) {
            cfg.setUserGroupId(memUserGroup.getUserGroupId());
            cfg.setCreateUser(admin.getAccLogin());
            cfg.setUpdateUser(admin.getAccLogin());
            cfg.setCreateTime(dateCfg);
            cfg.setUpdateTime(dateCfg);
            cfg.setIsDelete(false);
            groupCurrencyCfgService.save(cfg);
        }
        return true;

    }

    @Transactional
    public Boolean update(MemUserGroupUpReq req, LoginUser admin) throws Exception {

        if (req.getUserGroupId() == null) {
            throw new BusinessException("用户层级ID为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyCode())) {
            throw new BusinessException("国家编码为空！");
        }
        if (req.getIsInvitationRebates()==null) {
            throw new BusinessException("是否邀请返点为空！");
        }
        if (StringUtils.isEmpty(req.getGroupName())) {
            throw new BusinessException("用户层级名称为空！");
        }
        if (req.getDepositTimes() == null || req.getDepositTimes() < 0) {
            throw new BusinessException("入款次数为空或小于0！");
        }
        if (req.getWithdrawalsTimes() == null || req.getWithdrawalsTimes() < 0) {
            throw new BusinessException("出款次数为空或小于0！");
        }
        if (req.getStartTime() == null) {
            throw new BusinessException("有效开始时间为空！");
        }
        if (req.getEndTime() == null) {
            throw new BusinessException("有效结束时间为空！");
        }
        if (req.getEndTime().getTime() <= req.getStartTime().getTime()) {
            throw new BusinessException("有效结束时间 必须大于 有效开始时间！");
        }
        if (CollectionUtil.isEmpty(req.getGroupCurrencyCfgUpReq())) {
            throw new BusinessException("用户层级配置信为空！");
        }
        for (MemGroupCurrencyCfgUpReq cfgUpReq : req.getGroupCurrencyCfgUpReq()) {
            if (cfgUpReq.getGroupCurrencyId() == null) {
                throw new BusinessException("用户层级配置信息 主建ID为空！");
            }
            if (StringUtils.isEmpty(cfgUpReq.getCurrencyCode())) {
                throw new BusinessException("用户层级配置信息 国家币种为空！");
            }
            if (cfgUpReq.getMaxDeposit() == null || cfgUpReq.getMaxDeposit().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("用户层级配置信息 最大存款金额为空或小于0！");
            }
            if (cfgUpReq.getTotalDeposit() == null || cfgUpReq.getTotalDeposit().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("用户层级配置信息 入款总额为空或小于0！");
            }
            if (cfgUpReq.getTotalDispensing() == null || cfgUpReq.getTotalDispensing().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("用户层级配置信息 出款总额为空或小于0！");
            }

        }

        //更新用户层级基本信息
        Date date = new Date();
        QueryWrapper<MemUserGroup> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(MemUserGroup::getIsDelete, false);
        queryWrapper.lambda().eq(MemUserGroup::getUserGroupId, req.getUserGroupId());
        MemUserGroup memUserGroup = memUserGroupService.getBaseMapper().selectOne(queryWrapper);
        if (memUserGroup == null) {
            throw new BusinessException("用户层级基本信息不存在！");
        }
        BeanUtils.copyProperties(req, memUserGroup);
        memUserGroup.setUpdateUser(admin.getAccLogin());
        memUserGroup.setUpdateTime(date);
        memUserGroupService.updateById(memUserGroup);
        //更新用户层级配置信息
        Date dateCfg = new Date();
        List<MemGroupCurrencyCfg> listCfg = BeanCopyUtil.copyCollection(req.getGroupCurrencyCfgUpReq(), MemGroupCurrencyCfg.class);
        for (MemGroupCurrencyCfg cfg : listCfg) {
            cfg.setUserGroupId(memUserGroup.getUserGroupId());
            cfg.setUpdateUser(admin.getAccLogin());
            cfg.setUpdateTime(dateCfg);
            cfg.setIsDelete(false);
            groupCurrencyCfgService.updateById(cfg);
        }
        return true;

    }

    public boolean delete(MemUserGroupDlReq req, LoginUser admin) {
        if (CollectionUtil.isEmpty(req.getUserGroupIds())) {
            throw new BusinessException("用户层级IDs为空！");
        }
        //删除用户层级基本信息
        Date date = new Date();
        for (Long id : req.getUserGroupIds()) {
            MemUserGroup memUserGroup = new MemUserGroup();
            memUserGroup.setUserGroupId(id);
            memUserGroup.setUpdateTime(date);
            memUserGroup.setUpdateUser(admin.getAccLogin());
            memUserGroup.setIsDelete(true);
            memUserGroupService.updateById(memUserGroup);
        }
        //删除用户层级配置信息
        UpdateWrapper<MemGroupCurrencyCfg> memGroupCurrencyCfgQueryWrapper = new UpdateWrapper<>();
        memGroupCurrencyCfgQueryWrapper.lambda().set(MemGroupCurrencyCfg::getIsDelete, true)
                .set(MemGroupCurrencyCfg::getUpdateUser, admin.getAccLogin())
                .set(MemGroupCurrencyCfg::getUpdateTime, date);
        memGroupCurrencyCfgQueryWrapper.lambda().in(MemGroupCurrencyCfg::getUserGroupId, req.getUserGroupIds());
        groupCurrencyCfgService.update(null, memGroupCurrencyCfgQueryWrapper);
        return true;
    }

    public PageInfo<MemUserGroupManageVO> MemUserGroupManageList(MemUserGroupManageReq req) {
//        if (StringUtils.isEmpty(req.getCurrencyId())) {
//            throw new BusinessException("国家ID为空！");
//        }
        if (req.getUserGroupId()==null) {
            throw new BusinessException("层级ID为空！");
        }
        if (req.getParaWhere() == null) {
            throw new BusinessException("条件类型为空！");
        }
        if (req.getMinNum() != null && req.getMaxNum() != null) {
            if (req.getMinNum().compareTo(req.getMaxNum()) > 0) {
                throw new BusinessException("条件参数：最小值 不能大于 最大值！");
            }
        }
        if (req.getStartTime() != null && req.getEndTime() != null) {
            if (req.getStartTime().getTime() > req.getEndTime().getTime()) {
                throw new BusinessException("开始时间不能大于结束时间！");
            }
        }
        if (req.getPageNum() == null || req.getPageSize() == null) {
            throw new BusinessException("分页参数为空！");
        }
        PageInfo<MemUserGroupManageVO> pageInfo = memUserGroupService.getMemUserGroupManageList(req);
        return pageInfo;
    }

    public Boolean batchUserGroupUpdate(MemUserGroupManageUpReq req, LoginUser admin) {
        if (req.getUserGroupId() == null) {
            throw new BusinessException("层级ID为空！");
        }
        MemUserGroup group = memUserGroupService.getById(req.getUserGroupId());
        if (group == null) {
            throw new BusinessException("目标层级不存在！");
        }
        if (CollectionUtil.isEmpty(req.getUserIds())) {
            throw new BusinessException("用户ID为空！");
        }
        try {
            UpdateWrapper<MemUser> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().set(MemUser::getGroupId, req.getUserGroupId());
            updateWrapper.lambda().set(MemUser::getUpdateBy, admin.getAccLogin());
            updateWrapper.lambda().set(MemUser::getUpdateTime, new Date());
            updateWrapper.lambda().in(MemUser::getId, req.getUserIds());
            memUserService.update(updateWrapper);
            //用户层级变动推送系统消息
            memUserMsgRepushRecordService.addRecordByUserIdList(req.getUserIds());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public Boolean userLevelsOfMigration(UserLevelsOfMigrationVO req, LoginUser loginAdmin) {
        if (req.getSourceUserGroupId() == null) {
            throw new BusinessException("当前层级ID为空！");
        }
        if (req.getTargetUserGroupId() == null) {
            throw new BusinessException("目标层级ID为空！");
        }
        MemUserGroup group = memUserGroupService.getById(req.getTargetUserGroupId());
        if (group == null) {
            throw new BusinessException("目标层级不存在！");
        }
        try {
            QueryWrapper<MemUser> queryWrapper=new QueryWrapper<>();
            queryWrapper.lambda().eq(MemUser::getGroupId,req.getSourceUserGroupId());
            List<MemUser> userList=memUserService.list(queryWrapper);

            UpdateWrapper<MemUser> updateWrapper = new UpdateWrapper();
            updateWrapper.lambda().set(MemUser::getGroupId, req.getTargetUserGroupId());
            updateWrapper.lambda().set(MemUser::getUpdateBy, loginAdmin.getAccLogin());
            updateWrapper.lambda().set(MemUser::getUpdateTime, new Date());
            updateWrapper.lambda().eq(MemUser::getGroupId, req.getSourceUserGroupId());
            memUserService.update(updateWrapper);

            //用户层级变动推送系统消息
            memUserMsgRepushRecordService.addRecordByUserList(userList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MemUserGroupSelectVO> getList(String currencyCode) {
        QueryWrapper<MemUserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUserGroup::getIsDelete, false);
        queryWrapper.lambda().eq(MemUserGroup::getCurrencyCode, currencyCode);
        queryWrapper.lambda().orderByAsc(MemUserGroup::getUserGroupId);
        List<MemUserGroup> list = memUserGroupService.getBaseMapper().selectList(queryWrapper);
        List<MemUserGroupSelectVO> listVo = BeanCopyUtil.copyCollection(list, MemUserGroupSelectVO.class);
        return listVo;
    }
}
