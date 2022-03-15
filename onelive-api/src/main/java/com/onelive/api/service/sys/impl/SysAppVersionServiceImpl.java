package com.onelive.api.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysAppVersionService;
import com.onelive.common.mybatis.entity.SysAppVersion;
import com.onelive.common.mybatis.mapper.master.sys.SysAppVersionMapper;
import org.springframework.stereotype.Service;

@Service
public class SysAppVersionServiceImpl  extends ServiceImpl<SysAppVersionMapper, SysAppVersion> implements SysAppVersionService {

}
