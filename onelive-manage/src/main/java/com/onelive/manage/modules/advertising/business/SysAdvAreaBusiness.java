package com.onelive.manage.modules.advertising.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onelive.common.model.vo.sys.SysAdvAreaListVO;
import com.onelive.manage.service.sys.SysAdvAreaService;

/**
 * @ClassName SysAdvAreaBusiness
 * @Desc  广告设置业务类
 */
@Component
public class SysAdvAreaBusiness {

    @Resource
    private SysAdvAreaService sysAdvAreaService;

    /**
     *获取列表
     */
    public List<SysAdvAreaListVO> getList() {
    	return sysAdvAreaService.getList();
    }
   

}    