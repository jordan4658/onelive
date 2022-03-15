package com.onelive.manage.modules.sys.business;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.SendTypeEnum;
import com.onelive.common.enums.ShortMsgStatusEnum;
import com.onelive.common.model.req.sys.ShortMsgReq;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.model.vo.sys.ShortMsgVO;
import com.onelive.manage.service.sys.SysShortMsgService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SysShortMsgBusiness
 * @Desc 短信业务类
 * @Date 2021/4/5 17:08
 */
@Component
public class SysShortMsgBusiness {

    @Resource
    private SysShortMsgService sysShortMsgService;

    /**
     * 短信状态
     */
    public static List<SelectVO> shortMsgStatusList;
    /**
     * 短信类型
     */
    public static List<SelectVO> shortMsgTypeList;

    //类加载时候，立即初始化-短信状态和短信类型
    static {
        shortMsgStatusList = new ArrayList<>();
        shortMsgTypeList = new ArrayList<>();
        for (ShortMsgStatusEnum type : ShortMsgStatusEnum.values()) {
            SelectVO vo = new SelectVO();
            vo.setValue(type.getCode().longValue());
            vo.setDes(type.getMsg());
            shortMsgStatusList.add(vo);
        }
        for (SendTypeEnum type : SendTypeEnum.values()) {
            SelectVO vo = new SelectVO();
            vo.setValue(type.getCode().longValue());
            vo.setDes(type.getValue());
            shortMsgTypeList.add(vo);
        }
    }

    /**
     * 查询短信列表
     *
     * @param req
     * @return
     */
    public PageInfo<ShortMsgVO> queryShortMsgList(ShortMsgReq req) {
        //单独使用 PageHelper.startPage 后面必须紧跟的方法是查询数据库的，不然会出现线程不安全的分页问题
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<ShortMsgVO> list = sysShortMsgService.queryShortMsgList(req);
        PageInfo<ShortMsgVO> pageInfo = new PageInfo(list);
        return pageInfo;
    }


}    
    