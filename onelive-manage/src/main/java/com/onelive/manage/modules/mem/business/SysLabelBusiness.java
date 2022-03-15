package com.onelive.manage.modules.mem.business;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.manage.service.sys.SysLabelService;

/**
 * @ClassName SysLabelBusiness
 * @Desc 标签业务类
 * @Date 2021/4/5 17:08
 */
@Component
public class SysLabelBusiness {

    @Resource
    private SysLabelService sysLabelService;

    /**
     * 获取标签列表
     * @return
     */
    public List<SelectStringVO> queryLabelList(){
        return sysLabelService.queryLabelList();
    }

}    
    