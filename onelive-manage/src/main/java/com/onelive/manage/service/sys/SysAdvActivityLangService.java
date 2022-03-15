package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.SysAdvActivityLang;

import java.util.List;

/**
 * <p>
 * 广告首页轮播语种配置表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
public interface SysAdvActivityLangService extends IService<SysAdvActivityLang> {

    void deleteActivityLangByActiveId(Long id, String operateUser);

    List<SysAdvActivityLang> getListByActivityId(Long activityId);
}
