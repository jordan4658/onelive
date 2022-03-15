package com.onelive.manage.service.live;

import com.onelive.common.mybatis.entity.LiveGameTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface LiveGameTagService extends IService<LiveGameTag> {

    LiveGameTag getByCode(String code);
}
