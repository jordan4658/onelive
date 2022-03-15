package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.mybatis.entity.SysAdvActivity;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvActivityMapper;
import com.onelive.manage.service.sys.SysAdvActivityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告首页轮播表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
@Service
public class SysAdvActivityServiceImpl extends ServiceImpl<SysAdvActivityMapper, SysAdvActivity> implements SysAdvActivityService {

    @Override
    public PageInfo<SysAdvActivity> getActivityList(PageReq req) {
        QueryWrapper<SysAdvActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysAdvActivity::getIsDelete,false);
        return PageHelper.startPage(req.getPageNum(),req.getPageSize()).doSelectPageInfo(()->this.list(queryWrapper));
    }

    @Override
    public void deleteActivity(Long id, String operateUser) {
        UpdateWrapper<SysAdvActivity> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(SysAdvActivity::getIsDelete, true)
                .set(SysAdvActivity::getUpdateUser, operateUser)
                .eq(SysAdvActivity::getId, id);
        this.update(null, wrapper);
    }
}
