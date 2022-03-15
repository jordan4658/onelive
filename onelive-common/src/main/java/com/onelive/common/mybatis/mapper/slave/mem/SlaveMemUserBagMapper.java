package com.onelive.common.mybatis.mapper.slave.mem;

import com.onelive.common.model.dto.common.LangDTO;
import com.onelive.common.model.vo.live.AppUserLiveBagVO;
import com.onelive.common.mybatis.entity.MemUserBag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-24
 */
public interface SlaveMemUserBagMapper extends BaseMapper<MemUserBag> {

    List<AppUserLiveBagVO> getUserBagList(LangDTO dto);
}
