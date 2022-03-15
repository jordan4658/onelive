//package com.onelive.api.service.platform.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.onelive.api.service.platform.PlatformShareConfigService;
//import com.onelive.common.model.dto.platform.ShareForIndexDto;
//import com.onelive.common.mybatis.entity.PlatformShareConfig;
//import com.onelive.common.mybatis.mapper.master.platform.PlatformShareConfigMapper;
//import com.onelive.common.mybatis.mapper.slave.platform.SlavePlatformShareConfigMapper;
//import com.onelive.common.utils.Login.LoginInfoUtil;
//
///**
// * <p>
// * 服务实现类
// * </p>
// *
// * @author ${author}
// * @since 2021-12-23
// */
//@Service
//public class PlatformShareConfigServiceImpl extends ServiceImpl<PlatformShareConfigMapper, PlatformShareConfig>
//		implements PlatformShareConfigService {
//	
//	@Resource
//	SlavePlatformShareConfigMapper slavePlatformShareConfigMapper;
//
//	@Override
//	public List<ShareForIndexDto> listForIndex() {
//
//		return slavePlatformShareConfigMapper.listForIndex(LoginInfoUtil.getLang());
//	}
//
//}
