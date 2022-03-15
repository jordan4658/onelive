package com.onelive.manage.modules.sys.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysBusParameterQueryReq;
import com.onelive.common.model.req.sys.SysBusParameterReq;
import com.onelive.common.model.req.sys.SysBusParameterStatusReq;
import com.onelive.common.model.req.sys.SysBusParameterUpdateReq;
import com.onelive.common.model.vo.sys.SysBusParameterListVO;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysBusParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lorenzo
 * @Description: 业务参数业务类
 * @date 2021/4/6
 */
@Component
@Slf4j
public class SysBusParamBusiness {

    @Resource
    private SysBusParameterService service;

    /**
     * 业务参数分页查询
     *
     * @param param
     * @return
     */
    public PageInfo<SysBusParameterListVO> getList(SysBusParameterQueryReq param) {
        PageInfo<SysBusParameter> pageInfo = service.getList(param);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SysBusParameterListVO.class);
    }

    /**
     * 新增业务参数
     *
     * @param req
     */
    public void addSysBusParameter(SysBusParameterReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (StrUtil.isBlank(req.getParamCode())) {
            throw new BusinessException(101, "业务参数代码不能为空");
        }
        if (StrUtil.isBlank(req.getPParamCode())) {
            throw new BusinessException(102, "业务父级代码不能为空");
        }
        if (req.getStatus() == null) {
            throw new BusinessException(103, "系统参数启用状态不能为空");
        }
        if (req.getParamCode().equals(req.getPParamCode())) {
            throw new BusinessException(104, "父级代码不能与子级代码相同");
        }


        SysBusParameter p = new SysBusParameter();
        BeanUtil.copyProperties(req, p);
        p.setCreateUser(admin.getAccLogin());
        //保存时候，刷新缓存
        service.saveParam(p);
    }

    /**
     * 编辑业务参数
     *
     * @param req
     */
    public void updateSysBusParameter(SysBusParameterUpdateReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req.getId() == null) {
            throw new BusinessException(101, "参数主键不能为空");
        }
        SysBusParameter byId = service.getById(req.getId());
        if (byId == null) {
            throw new BusinessException("找不到业务参数");
        }
        if (req.getParamCode().equals(req.getPParamCode())) {
            throw new BusinessException(104, "父级代码不能与子级代码相同");
        }

        SysBusParameter p = new SysBusParameter();
        p.setUpdateUser(admin.getAccLogin());
        BeanUtil.copyProperties(req, p);
        //更新时候，刷新缓存
        service.updateParam(p);
    }

    /**
     * 切换业务参数状态
     *
     * @param req
     */
    public void switchSysBusParameterStatus(SysBusParameterStatusReq req, LoginUser admin) {
        if (admin == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req.getId() == null) {
            throw new BusinessException(101, "参数主键不能为空");
        }
        SysBusParameter byId = service.getById(req.getId());
        if (byId == null) {
            throw new BusinessException("找不到业务参数");
        }

        SysBusParameter p = new SysBusParameter();
        p.setUpdateUser(admin.getAccLogin());
        BeanUtil.copyProperties(req, p);
        //更新时候，刷新缓存
        service.updateParam(p);
    }

    /**
     * 删除业务参数
     *
     * @param id
     */
    public void deleteParam(Long id, String account) {
        service.deleteParam(id, account);
    }
}
