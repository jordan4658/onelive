package com.onelive.manage.modules.advertising.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.FlashviewTypeDto;
import com.onelive.common.model.req.sys.SysAdvFlashviewReq;
import com.onelive.common.model.vo.sys.SysAdvFlashviewLangVO;
import com.onelive.common.model.vo.sys.SysAdvFlashviewVO;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.SysAdvFlashview;
import com.onelive.common.mybatis.entity.SysAdvFlashviewLang;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.sys.SysAdvFlashviewLangService;
import com.onelive.manage.service.sys.SysAdvFlashviewService;
import com.onelive.manage.service.sys.SysParameterService;

/**
 * @Description: 首页轮播务类
 */
@Component
public class SysAdvFlashviewBusiness {

	@Resource
	private SysAdvFlashviewService service;

	@Resource
	private SysAdvFlashviewLangService langService;
	
	@Resource
	private SysParameterService sysParameterService;

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public PageInfo<SysAdvFlashviewVO> getList(SysAdvFlashviewReq sysAdvFlashviewReq) {
		PageHelper.startPage(sysAdvFlashviewReq.getPageNum(), sysAdvFlashviewReq.getPageSize());
		List<SysAdvFlashviewVO> list = service.getList();
		return new PageInfo<>(list);
	}

	/**
	 * 新增首页轮播
	 *
	 * @param req
	 */
	public void add(SysAdvFlashviewVO req, LoginUser admin) {
		if (admin == null) {
			throw new BusinessException(401, "没有权限操作");
		}
		SysAdvFlashview sysAdvFlashview = new SysAdvFlashview();
		BeanUtils.copyProperties(req, sysAdvFlashview);
		service.save(sysAdvFlashview);

		// 保存多语言对象
		List<SysAdvFlashviewLangVO> sysAdvFlashviewLangList = req.getSysAdvFlashviewLangList();
		for (SysAdvFlashviewLangVO sysAdvFlashviewLangVO : sysAdvFlashviewLangList) {
			SysAdvFlashviewLang sysAdvFlashviewLang = new SysAdvFlashviewLang();
			BeanUtils.copyProperties(sysAdvFlashviewLangVO, sysAdvFlashviewLang);
			sysAdvFlashviewLang.setId(null);
			sysAdvFlashviewLang.setFlashviewId(sysAdvFlashview.getId());
			sysAdvFlashviewLang.setImgUrl(AWSS3Util.getRelativeUrl(sysAdvFlashviewLang.getImgUrl()));
			langService.save(sysAdvFlashviewLang);
		}
	}

	/**
	 * 编辑首页轮播
	 *
	 * @param req
	 */
	public void update(SysAdvFlashviewVO req, LoginUser admin) {
		if (admin == null) {
			throw new BusinessException("没有权限操作");
		}
		if (req.getId() == null) {
			throw new BusinessException(101, "主键不能为空");
		}
		SysAdvFlashview byId = service.getById(req.getId());
		if (byId == null) {
			throw new BusinessException("找不到对应数据");
		}

		SysAdvFlashview sysAdvFlashview = new SysAdvFlashview();
		BeanUtils.copyProperties(req, sysAdvFlashview);
		service.updateById(sysAdvFlashview);

		// 处理多语言
		List<SysAdvFlashviewLangVO> sysAdvFlashviewLangList = req.getSysAdvFlashviewLangList();
		for (SysAdvFlashviewLangVO sysAdvFlashviewLangVO : sysAdvFlashviewLangList) {
			SysAdvFlashviewLang sysAdvFlashviewLang = new SysAdvFlashviewLang();
			BeanUtils.copyProperties(sysAdvFlashviewLangVO, sysAdvFlashviewLang);
			sysAdvFlashviewLang.setImgUrl(AWSS3Util.getRelativeUrl(sysAdvFlashviewLang.getImgUrl()));
			langService.updateById(sysAdvFlashviewLang);
		}

	}

	/**
	 * 删除首页轮播
	 *
	 * @param id
	 */
	public void delete(Long id, String account) {

		service.removeById(id);
		// 删除多语言对象
		QueryWrapper<SysAdvFlashviewLang> queryWrapper = new QueryWrapper<SysAdvFlashviewLang>();
		queryWrapper.lambda().eq(SysAdvFlashviewLang :: getFlashviewId, id);
		langService.remove(queryWrapper);
	}

	/**
	 * 	轮播图类型
	 * @return
	 */
	public List<FlashviewTypeDto> getFlashviewTypes() {
		List<SysParameterListVO> byType = sysParameterService.getListByType(SysParameterConstants.FLASHVIEW);
		List<FlashviewTypeDto> result = new ArrayList<FlashviewTypeDto>(byType.size());
		for (SysParameterListVO sysParameterListVO : byType) {
			FlashviewTypeDto flashviewTypeDto = new FlashviewTypeDto();
			flashviewTypeDto.setTypeCode(sysParameterListVO.getParamCode());
			flashviewTypeDto.setTypeName(sysParameterListVO.getParamName());
			result.add(flashviewTypeDto);
		}
		return result;
	}
}
