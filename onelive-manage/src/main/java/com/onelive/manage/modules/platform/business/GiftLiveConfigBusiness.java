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
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.GifTypeDto;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.LiveGift;
import com.onelive.common.mybatis.entity.LiveGiftLang;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.platform.LiveGiftLangService;
import com.onelive.manage.service.platform.LiveGiftService;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GiftLiveConfigBusiness {

	@Resource
	private LiveGiftService liveGiftService;

	@Resource
	private LiveGiftLangService liveGiftLangService;

	@Resource
	private SysParameterService sysParameterService;

	/**
	 * 简单查询礼物配置
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<LiveGiftDto> getList(LiveGiftDto liveGiftDto) {
		PageHelper.startPage(liveGiftDto.getPageNum(), liveGiftDto.getPageSize());
		List<LiveGiftDto> list = liveGiftService.getList(liveGiftDto);
		return new PageInfo<>(list);
	}
	
	/**
	 * 保存礼物
	 * 
	 * @param req
	 * @param loginUser
	 * @throws Exception
	 */
	public void save(LiveGiftDto req, LoginUser loginUser) {
		if (req.getGiftType() == 8) {
			LiveGift barrage = liveGiftService.getBarrage();
			if (barrage != null) {
				throw new BusinessException("弹幕礼物已存在，不可以再添加");
			}
		}
		
		// 保存礼物信息
		LiveGift liveGift = new LiveGift();
		BeanUtils.copyProperties(req, liveGift);
		liveGift.setGiftId(null);
		liveGift.setDynamicImage(AWSS3Util.getRelativeUrl(liveGift.getDynamicImage()));
		liveGift.setImageUrl(AWSS3Util.getRelativeUrl(liveGift.getImageUrl()));
		liveGiftService.save(liveGift);
		// 保存礼物多语言信息
		List<LiveGiftLang> liveGiftLangList = BeanCopyUtil.copyCollection(req.getLiveGiftLangList(), LiveGiftLang.class);
		liveGiftLangList.forEach(t -> {
			t.setGiftId(liveGift.getGiftId());
			liveGiftLangService.save(t);
		});
		
		SysBusinessRedisUtils.dels(RedisCacheableKeys.GIFT_LIST);
		SysBusinessRedisUtils.dels(RedisCacheableKeys.LIVE_GIFT_LIST);
		if (req.getGiftType() == 8) {
			SysBusinessRedisUtils.dels(RedisCacheableKeys.GIFT_BARRAGE);
		}
	}

	/**
	 * 礼物编辑
	 */
	public void update(LiveGiftDto req, LoginUser loginUser) {
		if (req.getGiftId() == null) {
			throw new BusinessException("礼物id不能为空");
		}
		
		LiveGift already = liveGiftService.getById(req.getGiftId());
		if (already == null) {
			throw new BusinessException("找不到对应的礼物信息");
		}
		
		if (req.getGiftType() == 8) {
			LiveGift barrage = liveGiftService.getBarrage();
			// 如果已有弹幕，且不是当前这个
			if (barrage != null && !req.getGiftId().equals(barrage.getGiftId())) {
				throw new BusinessException("弹幕礼物已存在，不可以再添加");
			}
		}
		
		LiveGift liveGift = new LiveGift();
		BeanUtils.copyProperties(req, liveGift);
		liveGift.setDynamicImage(AWSS3Util.getRelativeUrl(liveGift.getDynamicImage()));
		liveGift.setImageUrl(AWSS3Util.getRelativeUrl(liveGift.getImageUrl()));
		liveGiftService.saveOrUpdate(liveGift);
		// 更新礼物多语言信息
		List<LiveGiftLang> liveGiftLangList = BeanCopyUtil.copyCollection(req.getLiveGiftLangList(), LiveGiftLang.class);
		liveGiftLangList.forEach(t -> {
			t.setGiftId(req.getGiftId());
			liveGiftLangService.saveOrUpdate(t);
		});
		SysBusinessRedisUtils.dels(RedisCacheableKeys.GIFT_LIST);
		SysBusinessRedisUtils.dels(RedisCacheableKeys.LIVE_GIFT_LIST);
		if (req.getGiftType() == 8) {
			SysBusinessRedisUtils.dels(RedisCacheableKeys.GIFT_BARRAGE);
		}
	}
	
	/**
	 * 礼物删除
	 */
	public void delete(LiveGiftDto req, LoginUser loginUser) {
		if (req.getGiftId() == null) {
			throw new BusinessException("礼物id不能为空");
		}
		LiveGift already = liveGiftService.getById(req.getGiftId());
		if (already == null) {
			throw new BusinessException("找不到对应的礼物信息");
		}
		
		liveGiftService.removeById(req.getGiftId());

		// 删除多语言对象
		QueryWrapper<LiveGiftLang> queryWrapper = new QueryWrapper<LiveGiftLang>();
		queryWrapper.lambda().eq(LiveGiftLang :: getGiftId, req.getGiftId());
		liveGiftLangService.remove(queryWrapper);
		SysBusinessRedisUtils.dels(RedisCacheableKeys.GIFT_LIST);
		if (already.getGiftType() == 8) {
			SysBusinessRedisUtils.dels(RedisCacheableKeys.GIFT_BARRAGE);
		}
	}

	/**
	 * 系统变量表中查询礼物类型list
	 * 
	 * @return
	 */
	public List<GifTypeDto> getGiftTypes() {
		List<SysParameterListVO> byType = sysParameterService.getListByType(SysParameterConstants.GIFT_TYPE);
		List<GifTypeDto> result = new ArrayList<GifTypeDto>(byType.size());
		for (SysParameterListVO sysParameterListVO : byType) {
			GifTypeDto gifTypeDto = new GifTypeDto();
			gifTypeDto.setGiftTypeId(sysParameterListVO.getParamValue());
			gifTypeDto.setTypeName(sysParameterListVO.getParamName());
			result.add(gifTypeDto);
		}
		return result;
	}

}
