package com.onelive.manage.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.SysAdvNoticeQueryReq;
import com.onelive.common.model.vo.sys.SysAdvNoticeLangVo;
import com.onelive.common.model.vo.sys.SysAdvNoticeVO;
import com.onelive.common.mybatis.entity.SysAdvNotice;
import com.onelive.common.mybatis.entity.SysAdvNoticeLang;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvNoticeLangMapper;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvNoticeMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAdvNoticeMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.sys.SysAdvNoticeService;

/**
 * <p>
 * 广告公告表 服务实现类
 * </p>
 *
 * @since 2021-10-19
 */
@Service
public class SysAdvNoticeServiceImpl extends ServiceImpl<SysAdvNoticeMapper, SysAdvNotice>
		implements SysAdvNoticeService {

	@Resource
	private SlaveSysAdvNoticeMapper slaveSysAdvNoticeMapper;

	@Resource
	private SysAdvNoticeLangMapper sysAdvNoticeLangMapper;

	@Override
	public PageInfo<SysAdvNoticeVO> getAllList(SysAdvNoticeQueryReq req) {
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		LambdaQueryWrapper<SysAdvNotice> wrapper = Wrappers.<SysAdvNotice>lambdaQuery()
				.eq(SysAdvNotice::getMerchantCode, LoginInfoUtil.getMerchantCode());
		if (req.getType() != null) {
			wrapper.eq(SysAdvNotice::getType, req.getType());
		}

		List<SysAdvNoticeVO> resultList = BeanCopyUtil.copyCollection(slaveSysAdvNoticeMapper.selectList(wrapper),
				SysAdvNoticeVO.class);
		// 循环获取多语言对象
		for (SysAdvNoticeVO target : resultList) {
			LambdaQueryWrapper<SysAdvNoticeLang> wrapperLang = Wrappers.<SysAdvNoticeLang>lambdaQuery()
					.eq(SysAdvNoticeLang::getNoticeId, target.getId());
			List<SysAdvNoticeLang> langList = sysAdvNoticeLangMapper.selectList(wrapperLang);
			target.setSysAdvNoticeLangVos(BeanCopyUtil.copyCollection(langList, SysAdvNoticeLangVo.class));
		}

		return new PageInfo<SysAdvNoticeVO>(resultList);
	}

}
