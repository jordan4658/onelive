package com.onelive.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.mem.usercenter.ActivityListDTO;
import com.onelive.common.mybatis.entity.SysAdvActivity;

import java.util.List;

/**
 * <p>
 * 广告首页轮播表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
public interface SysAdvActivityService extends IService<SysAdvActivity> {

    List<SysAdvActivity> listWithCountryAndLang(ActivityListDTO dto);
}
