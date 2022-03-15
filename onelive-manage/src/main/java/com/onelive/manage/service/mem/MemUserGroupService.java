package com.onelive.manage.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.mem.MemUserGroupManageReq;
import com.onelive.common.model.vo.mem.MemUserGroupManageVO;
import com.onelive.common.model.vo.mem.MemUserGroupVO;
import com.onelive.common.mybatis.entity.MemUserGroup;

/**
 * <p>
 * 用户层级表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
public interface MemUserGroupService extends IService<MemUserGroup> {

    PageInfo<MemUserGroupVO> pageList(Integer pageSize, Integer pageNum,String currencyCode);

    PageInfo<MemUserGroupManageVO> getMemUserGroupManageList(MemUserGroupManageReq req);
}
