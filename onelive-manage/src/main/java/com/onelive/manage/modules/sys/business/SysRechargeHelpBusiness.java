package com.onelive.manage.modules.sys.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysRechargeHelpListReq;
import com.onelive.common.model.req.sys.SysRechargeHelpReq;
import com.onelive.common.model.req.sys.SysRechargeHelpUpdateListReq;
import com.onelive.common.model.req.sys.SysRechargeHelpUpdateReq;
import com.onelive.common.model.vo.sys.SysRechargeHelpLangVO;
import com.onelive.common.model.vo.sys.SysRechargeHelpListVO;
import com.onelive.common.mybatis.entity.SysRechargeHelp;
import com.onelive.common.mybatis.entity.SysRechargeHelpLang;
import com.onelive.manage.service.sys.SysRechargeHelpLangService;
import com.onelive.manage.service.sys.SysRechargeHelpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 充值帮助
 */
@Component
@Slf4j
public class SysRechargeHelpBusiness {

    @Resource
    private SysRechargeHelpService service;
    @Resource
    private SysRechargeHelpLangService langService;

    /**
     * 查询
     *
     * @param
     * @return
     */
    public List<SysRechargeHelpListVO> getList() {
    	  QueryWrapper<SysRechargeHelp> queryWrapper = new QueryWrapper<>();
          queryWrapper.lambda().eq(SysRechargeHelp::getIsDelete, false);
    	List<SysRechargeHelp> list = service.list(queryWrapper);
        return list.stream().map(r->{
        	SysRechargeHelpListVO vo = new SysRechargeHelpListVO();
        	BeanUtils.copyProperties(r, vo);
        	return vo;
        }).collect(Collectors.toList());
    }


    public List<SysRechargeHelpLangVO> getDetailList(Long id){
        QueryWrapper<SysRechargeHelpLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRechargeHelpLang::getHelpId,id);
        queryWrapper.lambda().eq(SysRechargeHelpLang::getIsDelete,false);
        List<SysRechargeHelpLang> list = langService.list(queryWrapper);

        return list.stream().map(r->{
            SysRechargeHelpLangVO vo = new SysRechargeHelpLangVO();
            BeanUtils.copyProperties(r, vo);
            return vo;
        }).collect(Collectors.toList());

    }

    /**
     * 新增
     *
     * @param req
     */
    public void add(SysRechargeHelpReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if(req == null){
            throw new BusinessException(StatusCode.PARAM_ERROR.getCode(),StatusCode.PARAM_ERROR.getMsg());
        }

        List<SysRechargeHelpListReq> firstList = req.getData().stream().filter(a->a.getLang().equals("en_US")).collect(Collectors.toList());
        SysRechargeHelpListReq first = firstList.get(0);
        Date now = new Date();

        SysRechargeHelp p = new SysRechargeHelp();
        p.setUpdateTime(now);
        p.setUpdateUser(admin.getAccLogin());
        p.setCreateTime(now);
        p.setCreateUser(admin.getAccLogin());
        p.setIsDelete(false);
        p.setLang(first.getLang());
        p.setTitle(first.getTitle());
        p.setContent(first.getContent());
        service.save(p);

        
        //保存语言相关
        Iterator<SysRechargeHelpListReq> iterator = req.getData().iterator();
        while (iterator.hasNext()){
            SysRechargeHelpListReq bo = iterator.next();
            //保存语言相关
            SysRechargeHelpLang langEntity = new SysRechargeHelpLang();
            langEntity.setHelpId(p.getId());
            langEntity.setLang(bo.getLang());
            langEntity.setTitle(bo.getTitle());
            langEntity.setContent(bo.getContent());
            langEntity.setCreateTime(now);
            langEntity.setCreateUser(admin.getAccLogin());
            langEntity.setIsDelete(false);
            langService.save(langEntity);
        }
    }

    /**
     * 编辑
     *
     * @param req
     */
    public void update(SysRechargeHelpUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException("没有权限操作");
        }
        if (req == null || req.getId() == null) {
            throw new BusinessException(101, "主键不能为空");
        }
        SysRechargeHelp p = service.getById(req.getId());
        if (p == null) {
            throw new BusinessException("找不到对应数据");
        }
        Date now = new Date();

        List<SysRechargeHelpUpdateListReq> firstList = req.getData().stream().filter(a->a.getLang().equals("en_US")).collect(Collectors.toList());
        SysRechargeHelpUpdateListReq first = firstList.get(0);

        p.setContent(first.getContent());
        p.setLang(first.getLang());
        p.setTitle(first.getTitle());
        p.setUpdateTime(now);
        p.setUpdateUser(admin.getAccLogin());
        service.updateById(p);

        
        //处理多语言
        QueryWrapper<SysRechargeHelpLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRechargeHelpLang::getHelpId, p.getId());
        langService.getBaseMapper().delete(queryWrapper);

        Iterator<SysRechargeHelpUpdateListReq> iterator = req.getData().iterator();
        while (iterator.hasNext()){
            SysRechargeHelpUpdateListReq bo = iterator.next();
            //保存语言相关
            SysRechargeHelpLang langEntity = new SysRechargeHelpLang();
            langEntity.setHelpId(p.getId());
            langEntity.setLang(bo.getLang());
            langEntity.setTitle(bo.getTitle());
            langEntity.setContent(bo.getContent());
            langEntity.setCreateTime(now);
            langEntity.setCreateUser(admin.getAccLogin());
            langEntity.setIsDelete(false);
            langService.save(langEntity);
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id, String account) {
        service.updateStatus(id, account);
    }
}
