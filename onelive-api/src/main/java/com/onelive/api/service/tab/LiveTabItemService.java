package com.onelive.api.service.tab;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.live.LiveTabItemVo;
import com.onelive.common.mybatis.entity.LiveTabItem;
import org.springframework.stereotype.Service;

import java.util.List;
public interface LiveTabItemService extends IService<LiveTabItem> {

    List<LiveTabItemVo> getLiveTabItemList();
}
