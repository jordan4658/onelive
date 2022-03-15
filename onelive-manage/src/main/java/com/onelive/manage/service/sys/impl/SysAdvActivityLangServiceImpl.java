package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysAdvActivityLang;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvActivityLangMapper;
import com.onelive.manage.service.sys.SysAdvActivityLangService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 广告首页轮播语种配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
@Service
public class SysAdvActivityLangServiceImpl extends ServiceImpl<SysAdvActivityLangMapper, SysAdvActivityLang> implements SysAdvActivityLangService {

    @Override
    public void deleteActivityLangByActiveId(Long id, String operateUser) {
        UpdateWrapper<SysAdvActivityLang> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(SysAdvActivityLang::getIsDelete, true)
                .set(SysAdvActivityLang::getUpdateUser, operateUser)
                .eq(SysAdvActivityLang::getActivityId, id);
        this.update(null, wrapper);
    }

    @Override
    public List<SysAdvActivityLang> getListByActivityId(Long activityId) {
        QueryWrapper<SysAdvActivityLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysAdvActivityLang::getActivityId, activityId);
        return this.list(queryWrapper);
    }

}
