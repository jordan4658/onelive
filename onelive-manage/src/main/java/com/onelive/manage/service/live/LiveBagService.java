package com.onelive.manage.service.live;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.live.LiveBagListReq;
import com.onelive.common.model.vo.live.LiveBagListVO;
import com.onelive.common.mybatis.entity.LiveBag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-23
 */
public interface LiveBagService extends IService<LiveBag> {

    PageInfo<LiveBagListVO> getList(LiveBagListReq param);

    void deleteBag(Long id, String operateUser);
}
