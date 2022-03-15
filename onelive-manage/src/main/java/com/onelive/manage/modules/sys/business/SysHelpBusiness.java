package com.onelive.manage.modules.sys.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysHelpInfoListReq;
import com.onelive.common.model.req.sys.SysHelpInfoReq;
import com.onelive.common.model.req.sys.SysHelpInfoUpdateListReq;
import com.onelive.common.model.req.sys.SysHelpInfoUpdateReq;
import com.onelive.common.model.vo.sys.SysHelpInfoLangVO;
import com.onelive.common.model.vo.sys.SysHelpInfoListVO;
import com.onelive.common.mybatis.entity.SysHelpInfo;
import com.onelive.common.mybatis.entity.SysHelpLang;
import com.onelive.manage.service.sys.SysHelpInfoService;
import com.onelive.manage.service.sys.SysHelpLangService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 帮助中心
 */
@Component
@Slf4j
public class SysHelpBusiness {

    @Resource
    private SysHelpInfoService service;
    @Resource
    private SysHelpLangService langService;

    /**
     * 查询
     *
     * @param
     * @return
     */
    public List<SysHelpInfoListVO> getList() {
  	  QueryWrapper<SysHelpInfo> queryWrapper = new QueryWrapper<>();
      queryWrapper.lambda().eq(SysHelpInfo::getIsDelete, false);
    	List<SysHelpInfo> list = service.list(queryWrapper);
        return list.stream().map(r->{
        	SysHelpInfoListVO vo = new SysHelpInfoListVO();
        	BeanUtils.copyProperties(r, vo);
        	return vo;
        }).collect(Collectors.toList());
    }


    public List<SysHelpInfoLangVO> getDetailList(Long id){
        QueryWrapper<SysHelpLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysHelpLang::getHelpId,id);
        queryWrapper.lambda().eq(SysHelpLang::getIsDelete,false);
        List<SysHelpLang> list = langService.list(queryWrapper);

        return list.stream().map(r->{
            SysHelpInfoLangVO vo = new SysHelpInfoLangVO();
            BeanUtils.copyProperties(r, vo);
            return vo;
        }).collect(Collectors.toList());

    }



    /**
     * 新增
     *
     * @param req
     */
    public void add(SysHelpInfoReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if(req == null){
            throw new BusinessException(StatusCode.PARAM_ERROR.getCode(),StatusCode.PARAM_ERROR.getMsg());
        }
        List<SysHelpInfoListReq> firstList = req.getData().stream().filter(a->a.getLang().equals("en_US")).collect(Collectors.toList());
        SysHelpInfoListReq first = firstList.get(0);
        Date now = new Date();

        SysHelpInfo p = new SysHelpInfo();
        p.setUpdateTime(now);
        p.setUpdateUser(admin.getAccLogin());
        p.setCreateTime(now);
        p.setCreateUser(admin.getAccLogin());
        p.setIsDelete(false);
        p.setLang(first.getLang());
        p.setTitle(first.getTitle());
        p.setContent(first.getContent());
        service.save(p);


        Iterator<SysHelpInfoListReq> iterator = req.getData().iterator();
        while (iterator.hasNext()){
            SysHelpInfoListReq bo = iterator.next();
            //保存语言相关
            SysHelpLang langEntity = new SysHelpLang();
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
    public void update(SysHelpInfoUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException("没有权限操作");
        }
        if (req == null || req.getId() == null) {
            throw new BusinessException(101, "主键不能为空");
        }
        SysHelpInfo p = service.getById(req.getId());
        if (p == null) {
            throw new BusinessException("找不到对应数据");
        }

        Date now = new Date();
        List<SysHelpInfoUpdateListReq> firstList = req.getData().stream().filter(a->a.getLang().equals("en_US")).collect(Collectors.toList());
        SysHelpInfoUpdateListReq first = firstList.get(0);
        p.setContent(first.getContent());
        p.setLang(first.getLang());
        p.setTitle(first.getTitle());
        p.setUpdateTime(now);
        p.setUpdateUser(admin.getAccLogin());
        service.updateById(p);
        
        //处理多语言
        QueryWrapper<SysHelpLang> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.lambda().eq(SysHelpLang::getHelpId, p.getId());
        langService.getBaseMapper().delete(deleteWrapper);


        Iterator<SysHelpInfoUpdateListReq> iterator = req.getData().iterator();
        while (iterator.hasNext()){
            SysHelpInfoUpdateListReq bo = iterator.next();
            //保存语言相关
            SysHelpLang langEntity = new SysHelpLang();
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
