package com.onelive.api.service.live;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.LiveGameTag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface LiveGameTagService extends IService<LiveGameTag> {

    List<LiveGameTag> listWithLang();
}
