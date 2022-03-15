package com.onelive.api.service.tab.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.tab.LiveTabItemService;
import com.onelive.common.model.vo.live.LiveTabItemVo;
import com.onelive.common.mybatis.entity.LiveTabItem;
import com.onelive.common.mybatis.mapper.master.live.LiveTabItemMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveTabItemMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class LiveTabItemServiceImpl extends ServiceImpl<LiveTabItemMapper,LiveTabItem> implements LiveTabItemService {

    @Resource
    private SlaveLiveTabItemMapper slaveLiveTabItemMapper;

    @Override
    public List<LiveTabItemVo> getLiveTabItemList() {

        List<LiveTabItemVo> voList  = new ArrayList<>();

        QueryWrapper<LiveTabItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(LiveTabItem::getSort);
        List<LiveTabItem> itemList = slaveLiveTabItemMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(itemList)){
            voList = BeanCopyUtil.copyCollection(itemList, LiveTabItemVo.class);
        }
        return voList;
    }

}
