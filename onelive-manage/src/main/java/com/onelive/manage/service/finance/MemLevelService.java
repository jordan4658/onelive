package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.finance.memlevel.MemLevelDTO;
import com.onelive.common.model.req.finance.memlevel.MemLevelListReq;
import com.onelive.common.mybatis.entity.MemLevel;

/**
 * <p>
 * 用户层级表 服务类
 * </p>
 *
 * @author kevin
 * @since 2021-10-26
 */
public interface MemLevelService extends IService<MemLevel> {

    /**
     * getPage
     *
     * @param memLevelListReq
     * @return PageInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午4:58:07
     */
    PageInfo<MemLevelDTO> getPage(MemLevelListReq memLevelListReq);


    /**
     * getById
     *
     * @param id
     * @return MemLevelDto
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午5:00:46
     */
    MemLevelDTO getById(Long id);
}
