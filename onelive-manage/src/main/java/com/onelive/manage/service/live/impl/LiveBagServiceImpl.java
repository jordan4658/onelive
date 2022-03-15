package com.onelive.manage.service.live.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.live.LiveBagListReq;
import com.onelive.common.model.vo.live.LiveBagListVO;
import com.onelive.common.mybatis.entity.LiveBag;
import com.onelive.common.mybatis.mapper.master.live.LiveBagMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveBagMapper;
import com.onelive.manage.service.live.LiveBagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-23
 */
@Service
public class LiveBagServiceImpl extends ServiceImpl<LiveBagMapper, LiveBag> implements LiveBagService {

    @Resource
    private SlaveLiveBagMapper slaveLiveBagMapper;

    @Override
    public PageInfo<LiveBagListVO> getList(LiveBagListReq param) {
        return PageHelper.startPage(param.getPageNum(),param.getPageSize()).doSelectPageInfo(()->slaveLiveBagMapper.getList(param));
    }

    @Override
    public void deleteBag(Long id, String operateUser) {
        LiveBag bag = this.getById(id);
        if(bag==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        bag.setIsDelete(true);
        bag.setUpdateUser(operateUser);
        this.updateById(bag);
    }
}
