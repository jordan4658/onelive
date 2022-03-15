package com.onelive.manage.modules.mem.business;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.mem.family.MemFamilyListReq;
import com.onelive.common.model.req.mem.family.MemFamilySaveReq;
import com.onelive.common.model.vo.mem.MemFamilyListVO;
import com.onelive.common.model.vo.mem.MemFamilyVO;
import com.onelive.common.mybatis.entity.MemFamily;
import com.onelive.manage.service.mem.MemFamilyService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * 家族管理
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemFamilyBusiness {
    @Resource
    private MemFamilyService memFamilyService;

    /**
     * 分页查询
     *
     * @return
     */
    public PageInfo<MemFamilyListVO> getList(MemFamilyListReq req) {
        PageInfo<MemFamilyListVO> pageInfo = memFamilyService.getList(req);
        return pageInfo;
    }

    public void saveFamily(MemFamilySaveReq req, LoginUser loginUser) throws Exception {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }

        checkParams(req);
        req.setUpdateBy(loginUser.getAccLogin());
        memFamilyService.saveFamily(req);
    }

    /**
     * 校验参数
     * @param req
     */
    private void checkParams(MemFamilySaveReq req) {
        if (StrUtil.isBlank(req.getFamilyName())) {
            throw new BusinessException("家族名称不能为空");
        }
        if (StrUtil.isBlank(req.getRegisterAreaCode())) {
            throw new BusinessException("区号不能为空");
        }
        if (StrUtil.isBlank(req.getMobilePhone())) {
            throw new BusinessException("手机号码不能为空");
        }
        if (StrUtil.isBlank(req.getUserAccount())) {
            throw new BusinessException("家族账号不能为空");
        }
        if (StrUtil.isBlank(req.getPassword())) {
            throw new BusinessException("登录密码不能为空");
        }
        if (req.getIsFrozen() == null) {
            throw new BusinessException("账号状态不能为空");
        }
        if (req.getGiftRatio() == null) {
            throw new BusinessException("礼物抽成比例不能为空");
        }
        if (req.getGiftRatio().compareTo(new BigDecimal("100")) == 1) {
   		 	throw new BusinessException("礼物分成比例不能大于100");
   	 	}
    }

    /**
     * 家族编辑
     *
     * @param req
     * @return
     * @Note
     */
    public void updateFamily(MemFamilySaveReq req, LoginUser loginUser) {
        if (loginUser == null) {
            throw new BusinessException(401, "没有权限操作");
        }
        if (req.getId() == null) {
            throw new BusinessException("主键不能为空");
        }
        checkParams(req);
        MemFamily mFamily = memFamilyService.getById(req.getId());
        if (mFamily == null) {
            throw new BusinessException("找不到对应的家族信息");
        }
        req.setUpdateBy(loginUser.getAccLogin());
        memFamilyService.updateFamily(req);
    }

    /**
     * 根据id获取家族信息
     * <b>Description:</b><br>
     *
     * @param id
     * @return
     * @Note
     */
    public MemFamilyVO getById(Long id) {
        if (id == null) {
            throw new BusinessException("主键id不能为空");
        }
        MemFamily mFamily = memFamilyService.getById(id);
        if (mFamily == null) {
            return null;
        }
        MemFamilyVO vo = new MemFamilyVO();
        BeanUtils.copyProperties(mFamily, vo);
        return vo;
    }
    
}
    