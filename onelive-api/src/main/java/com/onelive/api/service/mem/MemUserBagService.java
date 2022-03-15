package com.onelive.api.service.mem;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.live.AppUserLiveBagVO;
import com.onelive.common.mybatis.entity.MemUserBag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-24
 */
public interface MemUserBagService extends IService<MemUserBag> {

    PageInfo<AppUserLiveBagVO> getUserBagList();
}
