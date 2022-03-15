package com.onelive.manage.service.sys.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.sys.SysAdvAreaListVO;
import com.onelive.common.mybatis.entity.SysAdvArea;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvAreaMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.sys.SysAdvAreaService;

/**
 * <p>
 * 广告设置表 服务实现类
 * </p>
 *
 * @since 2021-10-19
 */
@Service
public class SysAdvAreaServiceImpl extends ServiceImpl<SysAdvAreaMapper, SysAdvArea> implements SysAdvAreaService {

	/* （非 Javadoc）
	 * @see com.onelive.manage.service.sys.SysAdvAreaService#getList()
	 */
	@Override
	public List<SysAdvAreaListVO> getList() {
		// TODO 
		  LambdaQueryWrapper<SysAdvArea> queryWrapper = Wrappers.lambdaQuery(SysAdvArea.class)
	                .eq(SysAdvArea::getMerchantCode, LoginInfoUtil.getMerchantCode());
		return this.list(queryWrapper).stream().map(r->{
			SysAdvAreaListVO vo = new SysAdvAreaListVO();
			vo.setAreaCode(r.getAreaCode());
			vo.setAreaName(r.getAreaName());
			vo.setId(r.getId());
			return vo;
		}).collect(Collectors.toList());
	}

}
