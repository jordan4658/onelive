package com.onelive.manage.modules.sys.business;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysDocumentReq;
import com.onelive.common.model.req.sys.SysDocumentUpdateReq;
import com.onelive.common.model.vo.sys.SysDocumentLangVO;
import com.onelive.common.model.vo.sys.SysDocumentListVO;
import com.onelive.common.mybatis.entity.SysDocument;
import com.onelive.common.mybatis.entity.SysDocumentLang;
import com.onelive.manage.service.sys.SysDocumentLangService;
import com.onelive.manage.service.sys.SysDocumentService;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 文章管理
 */
@Component
@Slf4j
public class SysDocumentBusiness {

    @Resource
    private SysDocumentService service;
    @Resource
    private SysDocumentLangService langService;

    /**
     * 查询
     *
     * @param param
     * @return
     */
    public List<SysDocumentListVO> getList() {
  	  QueryWrapper<SysDocument> queryWrapper = new QueryWrapper<>();
      queryWrapper.lambda().eq(SysDocument::getIsDelete, false);
    	List<SysDocument> list = service.list(queryWrapper);
        return list.stream().map(r->{
        	SysDocumentListVO vo = new SysDocumentListVO();
        	BeanUtils.copyProperties(r, vo);
        	//获取语种
        	QueryWrapper<SysDocumentLang> docQueryWrapper = new QueryWrapper<>();
        	docQueryWrapper.lambda().eq(SysDocumentLang::getDocId, r.getId());
        	vo.setLangVOs(langService.list(docQueryWrapper).stream().map(l->{
        		SysDocumentLangVO lang = new SysDocumentLangVO();
        		BeanUtils.copyProperties(l, lang);
        		return lang;
        	}).collect(Collectors.toList())); 
        	return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 新增
     *
     * @param req
     */
    public void add(SysDocumentReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        SysDocument p = new SysDocument();
        BeanUtil.copyProperties(req, p);
        p.setCreateUser(admin.getAccLogin());
        p.setIsDelete(false);
        service.save(p);
        
        //保存语言相关
        SysDocumentLang langEntity = new SysDocumentLang();
        langEntity.setDocId(p.getId());
        langEntity.setLang(p.getLang());
        langService.save(langEntity);
    }

    /**
     * 编辑
     *
     * @param req
     */
    public void update(SysDocumentUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException("没有权限操作");
        }
        if (req.getId() == null) {
            throw new BusinessException(101, "主键不能为空");
        }
        SysDocument byId = service.getById(req.getId());
        if (byId == null) {
            throw new BusinessException("找不到对应数据");
        }

        SysDocument p = new SysDocument();
        p.setUpdateUser(admin.getAccLogin());
        BeanUtil.copyProperties(req, p);
       // p.setIsDelete(0);
        service.updateById(p);
        
        //处理多语言
        QueryWrapper<SysDocumentLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysDocumentLang::getDocId, p.getId());
        queryWrapper.lambda().eq(SysDocumentLang::getLang, p.getLang());
        List<SysDocumentLang> list = langService.list(queryWrapper);
        if (list != null && list.size() > 0) {
        	SysDocumentLang langEntity = new SysDocumentLang();
	        langEntity.setContent(p.getContent());
	        langEntity.setTitle(p.getTitle());
        	langService.updateById(langEntity);
		}else {
			SysDocumentLang langEntity = new SysDocumentLang();
	        langEntity.setDocId(p.getId());
	        langEntity.setLang(p.getLang());
	        langEntity.setContent(p.getContent());
	        langEntity.setTitle(p.getTitle());
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
