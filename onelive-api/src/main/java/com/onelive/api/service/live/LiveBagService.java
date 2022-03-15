package com.onelive.api.service.live;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.LiveBag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-23
 */
public interface LiveBagService extends IService<LiveBag> {
    List<LiveBag> getListWithLang();
}
