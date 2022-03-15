package com.onelive.manage.modules.sys.business;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysWhitelistReq;
import com.onelive.common.model.req.sys.SysWhitelistUpdateReq;
import com.onelive.common.model.vo.sys.SysWhitelistListVO;
import com.onelive.common.model.vo.sys.SysWhitelistVO;
import com.onelive.common.mybatis.entity.SysWhitelist;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysWhitelistService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SysWhitelistBusiness {


    @Resource
    private SysWhitelistService service;


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SysWhitelistListVO> getList(String  keyword,Integer pageNum, Integer pageSize) {
        PageInfo<SysWhitelist> pageInfo = service.getList(keyword,pageNum, pageSize);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SysWhitelistListVO.class);
    }

    public void save( SysWhitelistReq req,LoginUser loginUser) {
    	if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (StrUtil.isBlank(req.getIp())) {
            throw new BusinessException("ip不能为空");
        }
        SysWhitelist p = new SysWhitelist();
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
    public void update(SysWhitelistUpdateReq req,LoginUser loginUser) {
    	 if (loginUser == null) {
           throw new BusinessException(401, "没有权限操作");
         }
    	 if (req.getId() == null) {
             throw new BusinessException("主键不能为空");
         }
    	 SysWhitelist sw =  service.getById(req.getId());  
    	 if (sw == null) {
             throw new BusinessException("找不到对应的信息");
         }
    	 SysWhitelist p = new SysWhitelist();
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
    public SysWhitelistVO getById(Long id) {
    	 if (id == null) {
             throw new BusinessException("主键id不能为空");
         } 
    	 SysWhitelist sw =  service.getById(id);  
    	  if (sw == null) {
              return null;
          }
    	  SysWhitelistVO vo = new SysWhitelistVO();
          BeanUtils.copyProperties(sw, vo);
          return vo;
    }
    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id, String account) {
        service.delete(id, account);
    }
}
    