package com.onelive.manage.modules.sys.business;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysImageUpdateReq;
import com.onelive.common.model.vo.sys.SysImageListVO;
import com.onelive.common.mybatis.entity.SysImageInfo;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysImageInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 图片管理
 */
@Component
@Slf4j
public class SysImageBusiness {

    @Resource
    private SysImageInfoService service;

    /**
     * 查询
     *
     * @param param
     * @return
     */
    public PageInfo<SysImageListVO> getList(Integer pageNum, Integer pageSize) {
    	  QueryWrapper<SysImageInfo> queryWrapper = new QueryWrapper<>();
          queryWrapper.lambda().eq(SysImageInfo::getIsDelete, false);
        PageHelper.startPage(pageNum,pageSize);
    	List<SysImageInfo> list = service.list(queryWrapper);
        PageInfo<SysImageInfo> pageInfo = new PageInfo<>(list);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SysImageListVO.class);
    }

    /**
     * 新增
     *
     * @param req
     */
    public void add(SysImageUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        SysImageInfo p = new SysImageInfo();
        BeanUtil.copyProperties(req, p);
        p.setCreateUser(admin.getAccLogin());
        p.setIsDelete(false);
        service.save(p);
       
    }

    /**
     * 编辑
     *
     * @param req
     */
    public void update(SysImageUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException("没有权限操作");
        }
        if (req.getId() == null) {
            throw new BusinessException(101, "主键不能为空");
        }
        SysImageInfo byId = service.getById(req.getId());
        if (byId == null) {
            throw new BusinessException("找不到对应数据");
        }

        SysImageInfo p = new SysImageInfo();
        p.setUpdateUser(admin.getAccLogin());
        BeanUtil.copyProperties(req, p);
        p.setIsDelete(false);
        service.updateById(p);
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
