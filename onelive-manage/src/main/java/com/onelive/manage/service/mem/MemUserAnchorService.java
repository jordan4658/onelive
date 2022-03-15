package com.onelive.manage.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.report.AnchorReportDto;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorListReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.vo.mem.MemUserAnchorVO;
import com.onelive.common.mybatis.entity.MemUserAnchor;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-18
 */
public interface MemUserAnchorService extends IService<MemUserAnchor> {

	int save(MemUserAnchorSaveReq req) throws Exception;

	PageInfo<MemUserAnchorVO> getList(MemUserAnchorListReq memUserAnchorReq);

	int update(MemUserAnchorSaveReq req);

	List<AnchorReportDto> getReportList(AnchorReportDto anchorReportDto);

	/**
	 * 	根据userid获取主播的基础信息
	 * @param userId
	 * @return
	 */
	MemUserAnchor getInfoByUserId(Long userId);

}
