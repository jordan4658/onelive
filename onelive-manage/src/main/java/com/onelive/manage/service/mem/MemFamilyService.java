package com.onelive.manage.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.mem.family.MemFamilyListReq;
import com.onelive.common.model.req.mem.family.MemFamilySaveReq;
import com.onelive.common.model.vo.mem.MemFamilyListVO;
import com.onelive.common.mybatis.entity.MemFamily;

/**
 * 家族表 服务类
 */
public interface MemFamilyService extends IService<MemFamily> {

	/**
	 * 分页查询
	 */
	PageInfo<MemFamilyListVO> getList(MemFamilyListReq req);

	/**
	 * 添加家族
	 */
	int saveFamily(MemFamilySaveReq req) throws Exception;

	int updateFamily(MemFamilySaveReq req);

}
