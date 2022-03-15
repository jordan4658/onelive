package com.onelive.manage.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.sys.SysAdvFlashviewLangVO;
import com.onelive.common.model.vo.sys.SysAdvFlashviewVO;
import com.onelive.common.mybatis.entity.SysAdvFlashview;
import com.onelive.common.mybatis.entity.SysAdvFlashviewLang;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvFlashviewMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAdvFlashviewLangMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAdvFlashviewMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.sys.SysAdvFlashviewService;

/**
 * <p>
 *		 首页轮播表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Service
public class SysAdvFlashviewServiceImpl extends ServiceImpl<SysAdvFlashviewMapper, SysAdvFlashview>
		implements SysAdvFlashviewService {

	@Resource
	private SlaveSysAdvFlashviewMapper slaveSysAdvFlashviewMapper;
	@Resource
	private SlaveSysAdvFlashviewLangMapper slaveSysAdvFlashviewLangMapper;

	@Override
	public List<SysAdvFlashviewVO> getList() {
		List<SysAdvFlashview> list = slaveSysAdvFlashviewMapper.selectList(new QueryWrapper<SysAdvFlashview>());
		List<SysAdvFlashviewVO> result = BeanCopyUtil.copyCollection(list, SysAdvFlashviewVO.class);
		result.forEach(t -> {
			LambdaQueryWrapper<SysAdvFlashviewLang> wrapper = Wrappers.<SysAdvFlashviewLang>lambdaQuery()
					.eq(SysAdvFlashviewLang::getFlashviewId, t.getId());
			List<SysAdvFlashviewLang> selectList = slaveSysAdvFlashviewLangMapper.selectList(wrapper);
			for (SysAdvFlashviewLang langTarGet : selectList) {
				langTarGet.setImgUrl(AWSS3Util.getAbsoluteUrl(langTarGet.getImgUrl()));
			}
			t.setSysAdvFlashviewLangList(BeanCopyUtil.copyCollection(selectList, SysAdvFlashviewLangVO.class));
		});

		return result;
	}

}
