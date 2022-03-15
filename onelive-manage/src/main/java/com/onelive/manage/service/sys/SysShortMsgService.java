package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.sys.ShortMsgReq;
import com.onelive.common.model.vo.sys.ShortMsgVO;
import com.onelive.common.mybatis.entity.SysShortMsg;

import java.util.List;

/**
 * <p>
 * 短信记录表 服务类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-05
 */
public interface SysShortMsgService extends IService<SysShortMsg> {

    /**
     * 查询短信信息列表
     *
     * @param req
     * @return
     */
    List<ShortMsgVO> queryShortMsgList(ShortMsgReq req);
}
