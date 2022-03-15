package com.onelive.common.mybatis.mapper.slave.mem;

import com.onelive.common.model.req.mem.MemUserGroupManageReq;
import com.onelive.common.model.vo.mem.MemUserGroupManageVO;
import com.onelive.common.model.vo.mem.MemUserGroupVO;
import com.onelive.common.mybatis.entity.MemUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户层级表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
public interface SlaveMemUserGroupMapper extends BaseMapper<MemUserGroup> {

    List<MemUserGroupManageVO> getMemUserGroupManageList(MemUserGroupManageReq req);

    List<MemUserGroupVO> pageList(String currencyCode);
}
