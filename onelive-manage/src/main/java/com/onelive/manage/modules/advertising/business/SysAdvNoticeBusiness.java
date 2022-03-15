package com.onelive.manage.modules.advertising.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysAdvNoticeQueryReq;
import com.onelive.common.model.vo.sys.SysAdvNoticeVO;
import com.onelive.common.mybatis.entity.SysAdvNotice;
import com.onelive.common.mybatis.entity.SysAdvNoticeLang;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.sys.SysAdvNoticeLangService;
import com.onelive.manage.service.sys.SysAdvNoticeService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @Description: 首页公告务类
 */
@Component
public class SysAdvNoticeBusiness {

	@Resource
	private SysAdvNoticeService service;
	@Resource
	private SysAdvNoticeLangService langService;

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public PageInfo<SysAdvNoticeVO> getList(SysAdvNoticeQueryReq req) {
		PageInfo<SysAdvNoticeVO> pageInfo = service.getAllList(req);
		return pageInfo;
	}

	/**
	 * 新增首页公告
	 *
	 * @param req
	 */
	public void add(SysAdvNoticeVO req, LoginUser admin) {
		if (StrUtil.isBlank(req.getNoticeName())) {
			throw new BusinessException("公告名称不能为空");
		}
		if (req.getStartDate() == null || req.getEndDate() == null) {
			throw new BusinessException("显示时间不能为空");
		}

		SysAdvNotice sysAdvNotice = new SysAdvNotice();
		BeanUtil.copyProperties(req, sysAdvNotice);
		sysAdvNotice.setCreateUser(admin.getAccLogin());
		service.save(sysAdvNotice);

		// 保存语言相关
		List<SysAdvNoticeLang> sysAdvNoticeLangs = BeanCopyUtil.copyCollection(req.getSysAdvNoticeLangVos(),
				SysAdvNoticeLang.class);
		sysAdvNoticeLangs.forEach(t -> {
			t.setNoticeId(sysAdvNotice.getId());
			langService.save(t);
		});

	}

	/**
	 * 编辑首页公告
	 *
	 * @param req
	 */
	public void update(SysAdvNoticeVO req, LoginUser admin) {
		if (req.getId() == null) {
			throw new BusinessException(101, "主键不能为空");
		}
		SysAdvNotice byId = service.getById(req.getId());
		if (byId == null) {
			throw new BusinessException("找不到对应数据");
		}

		SysAdvNotice p = new SysAdvNotice();
		p.setUpdateUser(admin.getAccLogin());
		BeanUtil.copyProperties(req, p);
		service.updateById(p);

		// 处理多语言
		List<SysAdvNoticeLang> sysAdvNoticeLangs = BeanCopyUtil.copyCollection(req.getSysAdvNoticeLangVos(),
				SysAdvNoticeLang.class);
		sysAdvNoticeLangs.forEach(t -> {
			t.setNoticeId(p.getId());
			langService.saveOrUpdate(t);
		});
	}

	/**
	 * 删除首页公告
	 *
	 * @param id
	 */
	public void delete(Long id) {
		service.removeById(id);
		QueryWrapper<SysAdvNoticeLang> queryWrapper = new QueryWrapper<SysAdvNoticeLang>();
		queryWrapper.lambda().eq(SysAdvNoticeLang :: getNoticeId, id);
		langService.remove(queryWrapper);
	}
}
