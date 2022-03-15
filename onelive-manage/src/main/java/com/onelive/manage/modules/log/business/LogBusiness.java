package com.onelive.manage.modules.log.business;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.log.LogQueryParam;
import com.onelive.common.model.vo.log.SysLogListVO;
import com.onelive.common.model.vo.log.SysLogVO;
import com.onelive.common.mybatis.entity.SysLog;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.SysLogService;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author lorenzo
 * @Description:
 * @date 2021/4/2
 */
@Component
public class LogBusiness {

    @Resource
    private SysLogService sysLogService;

    /**
     * 按条件查询系统日志分页
     *
     * @param param
     * @return
     */
    public PageInfo<SysLogVO> getLogList(LogQueryParam param) {
        PageInfo<SysLog> logList = sysLogService.getLogList(param);
        return PageInfoUtil.pageInfo2DTO(logList, SysLogVO.class);
    }

    /**
     * 查询某一条日志
     *
     * @param id
     * @return
     */
    public SysLogVO getLog(Long id) {
        SysLog byId = sysLogService.getById(id);
        SysLogVO vo = new SysLogVO();
        BeanUtil.copyProperties(byId, vo);
        return vo;
    }

    public void delAllErrorLog() {
        sysLogService.delByLogType("ERROR");
    }

    public void delAllInfoLog() {
        sysLogService.delByLogType("INFO");
    }
    
    /**
     * 按条件查询操作日志分页
     *
     * @param param
     * @return
     */
    public PageInfo<SysLogListVO> getList(String operator,Integer pageNum,Integer pageSize) {
        PageInfo<SysLog> logList = sysLogService.getList(operator, pageNum, pageSize);
        return PageInfoUtil.pageInfo2DTO(logList, SysLogListVO.class);
    }

}
