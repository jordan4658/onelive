package com.onelive.common.mybatis.mapper.slave.mem;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.req.mem.family.MemFamilyListReq;
import com.onelive.common.model.vo.mem.MemFamilyListVO;
import com.onelive.common.mybatis.entity.MemFamily;

/**
 * <p>
 * 家族表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface SlaveMemFamilyMapper extends BaseMapper<MemFamily> {

	List<MemFamilyListVO> getList(MemFamilyListReq memFamilyReq);

}
