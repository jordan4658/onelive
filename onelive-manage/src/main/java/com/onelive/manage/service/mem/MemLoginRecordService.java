package com.onelive.manage.service.mem;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.mem.MemUserLoginReq;
import com.onelive.common.model.vo.mem.MemUserLoginVO;
import com.onelive.common.mybatis.entity.MemLoginRecord;

/**
 * <p>
 * 登录记录表 服务类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-07
 */
public interface MemLoginRecordService extends IService<MemLoginRecord> {

    PageInfo<MemUserLoginVO> queryLoginInfo(MemUserLoginReq req);
}
