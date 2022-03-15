package com.onelive.manage.modules.sys.business;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysParameterReq;
import com.onelive.common.model.req.sys.SysParameterSwitchReq;
import com.onelive.common.model.vo.sys.SysParameterByCodeVO;
import com.onelive.common.model.vo.sys.SysParameterByIdVO;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.manage.service.sys.SysParameterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SysParamBusiness
 * @Desc 系统参数业务类
 * @Date 2021/3/24 10:39
 */
@Component
public class SysParamBusiness {

    @Resource
    private SysParameterService sysParameterService;

    /**
     * 分页获取系统配置
     *
     * @param paramCode 系统参数code值
     * @return
     */
    public PageInfo<SysParameterListVO> getList(String paramCode, Integer pageNum, Integer pageSize) {
        //单独使用 PageHelper.startPage 后面必须紧跟的方法是查询数据库的，不然会出现线程不安全的分页问题
        PageHelper.startPage(pageNum, pageSize);
        List<SysParameterListVO> list = sysParameterService.getList(paramCode);
        PageInfo<SysParameterListVO> pageInfo = new PageInfo(list);
        return pageInfo;
    }


    /**
     * 根据id获取系统配置
     *
     * @param paramId
     * @return
     */
    public SysParameterByIdVO getById(Long paramId) {
        SysParameter info = sysParameterService.getById(paramId);
        if (info == null || info.getIsDelete()) {
            return null;
        }
        SysParameterByIdVO vo = new SysParameterByIdVO();
        BeanUtils.copyProperties(info, vo);
        return vo;
    }

    /**
     * 根据code获取系统配置
     *
     * @param code
     * @return
     */
    public SysParameterByCodeVO getByCode(String code) {
        SysParameter info = sysParameterService.getByCode(code);
        if (info == null || info.getIsDelete()) {
            return null;
        }
        SysParameterByCodeVO vo = new SysParameterByCodeVO();
        BeanUtils.copyProperties(info, vo);
        return vo;
    }

    /**
     * 系统参数编辑
     *
     * @param req
     * @param loginUser
     */
    public void updateSysParameter(SysParameterReq req, LoginUser loginUser) {
        if (req.getParamId() == null) {
            throw new BusinessException("ID不能为空！");
        }
        if (StringUtils.isBlank(req.getParamCode())) {
            throw new BusinessException("缺失参数！");
        }
        //记录操作人
        SysParameter sysParameter = new SysParameter();
        BeanUtils.copyProperties(req, sysParameter);
        sysParameter.setUpdateUser(loginUser.getAccLogin());

        //更新数据库记录以及redis缓存
        sysParameterService.updateSysParameter(sysParameter);
    }


    /**
     * 切换状态
     *
     * @param req
     * @param loginUser
     */
    public void updateSwitchStatus(SysParameterSwitchReq req, LoginUser loginUser) {
        if (req.getParamId() == null) {
            throw new BusinessException("ID不能为空！");
        }
        // 记录操作人
        SysParameter sysParameter = new SysParameter();
        sysParameter.setParamStatus(req.getParamStatus());
        sysParameter.setParamId(req.getParamId());
        sysParameter.setUpdateUser(loginUser.getAccLogin());
        //更新数据库记录以及redis缓存
        sysParameterService.updateSysParameter(sysParameter);
    }

    /**
     * 删除系统参数
     *
     * @param id
     */
    public void updateDeleteStatus(Long id) {
        sysParameterService.updateDeleteStatus(id);
    }
}
    