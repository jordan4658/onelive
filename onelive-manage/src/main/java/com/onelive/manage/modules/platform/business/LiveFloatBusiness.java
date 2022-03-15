package com.onelive.manage.modules.platform.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.FloatTypeDto;
import com.onelive.common.model.dto.platform.LiveFloatDto;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.LiveFloat;
import com.onelive.common.mybatis.entity.LiveFloatLang;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.platform.LiveFloatLangService;
import com.onelive.manage.service.platform.LiveFloatService;
import com.onelive.manage.service.sys.SysParameterService;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LiveFloatBusiness {

	@Resource
	private LiveFloatService liveFloatService;

	@Resource
	private LiveFloatLangService liveFloatLangService;

	@Resource
	private SysParameterService sysParameterService;

	/**
	 * 简单查询悬浮窗配置
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<LiveFloatDto> getList(LiveFloatDto liveFloatDto) {
		PageHelper.startPage(liveFloatDto.getPageNum(), liveFloatDto.getPageSize());
		List<LiveFloatDto> list = liveFloatService.getList(liveFloatDto);
		return new PageInfo<>(list);
	}

	/**
	 * 保存悬浮窗
	 * 
	 * @param req
	 * @param loginUser
	 * @throws Exception
	 */
	public void save(LiveFloatDto req, LoginUser loginUser) {
		// 保存悬浮窗信息
		LiveFloat liveFloat = new LiveFloat();
		BeanUtils.copyProperties(req, liveFloat);
		liveFloat.setId(null);
		liveFloatService.save(liveFloat);
		// 保存悬浮窗多语言信息
		List<LiveFloatLang> liveFloatLangList = BeanCopyUtil.copyCollection(req.getLiveFloatLangList(),
				LiveFloatLang.class);
		liveFloatLangList.forEach(t -> {
			t.setFloatId(liveFloat.getId());
			t.setImgUrl(AWSS3Util.getRelativeUrl(t.getImgUrl()));
			liveFloatLangService.save(t);
		});
	}

	/**
	 * 悬浮窗编辑
	 */
	public void update(LiveFloatDto req, LoginUser loginUser) {
		if (req.getId() == null) {
			throw new BusinessException("悬浮窗id不能为空");
		}

		LiveFloat already = liveFloatService.getById(req.getId());
		if (already == null) {
			throw new BusinessException("找不到对应的悬浮窗信息");
		}
		LiveFloat liveFloat = new LiveFloat();
		BeanUtils.copyProperties(req, liveFloat);
		liveFloatService.saveOrUpdate(liveFloat);
		// 更新悬浮窗多语言信息
		List<LiveFloatLang> liveFloatLangList = BeanCopyUtil.copyCollection(req.getLiveFloatLangList(),
				LiveFloatLang.class);
		liveFloatLangList.forEach(t -> {
			t.setFloatId(req.getId());
			t.setImgUrl(AWSS3Util.getRelativeUrl(t.getImgUrl()));
			liveFloatLangService.saveOrUpdate(t);
		});
	}

	/**
	 * 悬浮窗删除
	 */
	public void delete(LiveFloatDto req, LoginUser loginUser) {
		if (req.getId() == null) {
			throw new BusinessException("悬浮窗id不能为空");
		}
		LiveFloat already = liveFloatService.getById(req.getId());
		if (already == null) {
			throw new BusinessException("找不到对应的悬浮窗信息");
		}

		liveFloatService.removeById(req.getId());

		// 删除多语言对象
		QueryWrapper<LiveFloatLang> queryWrapper = new QueryWrapper<LiveFloatLang>();
		queryWrapper.lambda().eq(LiveFloatLang :: getFloatId, req.getId());
		liveFloatLangService.remove(queryWrapper);
	}

	/**
	 * 系统变量表中查询悬浮窗类型list
	 * 
	 * @return
	 */
	public List<FloatTypeDto> getFloatTypes() {
		List<SysParameterListVO> byType = sysParameterService.getListByType(SysParameterConstants.FLOAT_TYPE);
		List<FloatTypeDto> result = new ArrayList<FloatTypeDto>(byType.size());
		for (SysParameterListVO sysParameterListVO : byType) {
			FloatTypeDto floatTypeDto = new FloatTypeDto();
			floatTypeDto.setFloatType(sysParameterListVO.getParamCode());
			floatTypeDto.setTypeName(sysParameterListVO.getParamName());
			result.add(floatTypeDto);
		}
		return result;
	}

}
