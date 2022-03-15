package com.onelive.manage.modules.sys.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysBlacklistReq;
import com.onelive.common.model.req.sys.SysBlacklistUpdateReq;
import com.onelive.common.model.vo.sys.SysBlacklistListVO;
import com.onelive.common.model.vo.sys.SysBlacklistVO;
import com.onelive.common.mybatis.entity.SysBlacklist;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysBlacklistService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SysBlacklistBusiness {


    @Resource
    private SysBlacklistService service;


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SysBlacklistListVO> getList(String  keyword,Integer pageNum, Integer pageSize) {
        PageInfo<SysBlacklist> pageInfo = service.getList(keyword,pageNum, pageSize);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SysBlacklistListVO.class);
    }

    public void save( SysBlacklistReq req,LoginUser loginUser) {
    	if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (StrUtil.isBlank(req.getIp())) {
            throw new BusinessException("ip不能为空");
        }
        SysBlacklist p = new SysBlacklist();
        BeanUtil.copyProperties(req, p);
        p.setIsDelete(0);
        p.setCreateUser(loginUser.getAccLogin());
        service.save(p);
    }
    
    /**
     * 编辑
    * @param req
    * @return
    * @Note
     */
    public void update(SysBlacklistUpdateReq req,LoginUser loginUser) {
    	 if (loginUser == null) {
           throw new BusinessException(401, "没有权限操作");
         }
    	 if (req.getId() == null) {
             throw new BusinessException("主键不能为空");
         }
    	 SysBlacklist sw =  service.getById(req.getId());  
    	 if (sw == null) {
             throw new BusinessException("找不到对应的信息");
         }
    	 SysBlacklist p = new SysBlacklist();
          p.setUpdateUser(loginUser.getAccLogin());
          BeanUtil.copyProperties(req, p);
          p.setIsDelete(0);
          service.updateById(p);
    }
    /**
     * 根据id获取信息
    * <b>Description:</b><br> 
    * @param id
    * @return
    * @Note
     */
    public SysBlacklistVO getById(Long id) {
    	 if (id == null) {
             throw new BusinessException("主键id不能为空");
         } 
    	 SysBlacklist sw =  service.getById(id);  
    	  if (sw == null) {
              return null;
          }
    	  SysBlacklistVO vo = new SysBlacklistVO();
          BeanUtils.copyProperties(sw, vo);
          return vo;
    }
    /**
     * 删除
     *
     * @param ids
     */
    public void delete(List<Long> ids, String account) {
    	service.removeByIds(ids);
    }
}
    