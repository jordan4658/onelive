package com.onelive.manage.converter.finance;

import org.mapstruct.Mapper;

import com.onelive.common.model.dto.finance.memlevel.MemLevelDTO;
import com.onelive.common.model.req.finance.memlevel.MemLevelListReq;
import com.onelive.common.model.req.finance.memlevel.MemLevelSaveReq;
import com.onelive.common.model.req.finance.memlevel.MemLevelUpdateReq;
import com.onelive.common.model.vo.finance.memlevel.MemLevelVO;
import com.onelive.common.mybatis.entity.MemLevel;
import com.onelive.manage.converter.BaseConverter;

/**
 * MemLevelConverter
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月27日 下午3:27:38
 */
@Mapper(componentModel = "spring")
public interface MemLevelConverter extends BaseConverter<MemLevelListReq, MemLevelDTO, MemLevel, MemLevelVO> {

    MemLevel saveReqToEntity(MemLevelSaveReq memLevelSaveReq);

    MemLevel updateReqToEntity(MemLevelUpdateReq memLevelUpdateReq);
}
