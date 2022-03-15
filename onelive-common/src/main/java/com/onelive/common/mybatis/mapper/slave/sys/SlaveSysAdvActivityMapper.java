package com.onelive.common.mybatis.mapper.slave.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.req.mem.usercenter.ActivityListDTO;
import com.onelive.common.mybatis.entity.SysAdvActivity;
import com.onelive.common.mybatis.sqlProvider.SysAdvActivitySqlProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 广告首页轮播表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
public interface SlaveSysAdvActivityMapper extends BaseMapper<SysAdvActivity> {

    @SelectProvider(value = SysAdvActivitySqlProvider.class,method = "listWithCountryAndLang")
    List<SysAdvActivity> listWithCountryAndLang(ActivityListDTO dto);

}
