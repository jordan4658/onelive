package com.onelive.manage.service.platform.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.dto.platform.LiveGiftLangDto;
import com.onelive.common.mybatis.entity.LiveGift;
import com.onelive.common.mybatis.entity.LiveGiftLang;
import com.onelive.common.mybatis.mapper.master.platform.LiveGiftMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveGiftLangMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveGiftMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.platform.LiveGiftService;

/**
 * <p>
 * 礼物信息表 服务实现类
 * </p>
 *
 * @author maomao
 * @since 2021-11-03
 */
@Service
public class LiveGiftServiceImpl extends ServiceImpl<LiveGiftMapper, LiveGift> implements LiveGiftService {

	@Resource
	private SlaveLiveGiftLangMapper slaveLiveGiftLangMapper;
	
	@Resource
	private SlaveLiveGiftMapper slaveLiveGiftMapper;
	
	/**
	 * 后台管理查询 礼物
	 */
	@Override
	public List<LiveGiftDto> getList(LiveGiftDto liveGiftDto) {
		liveGiftDto.setLang(LoginInfoUtil.getLang());
		List<LiveGiftDto> list = slaveLiveGiftMapper.getList(liveGiftDto);
		list.forEach(t ->{
			 LambdaQueryWrapper<LiveGiftLang> wrapper = Wrappers.<LiveGiftLang>lambdaQuery()
					 .eq(LiveGiftLang::getGiftId, t.getGiftId());
			 List<LiveGiftLang> selectList = slaveLiveGiftLangMapper.selectList(wrapper);
			 t.setLiveGiftLangList(BeanCopyUtil.copyCollection(selectList, LiveGiftLangDto.class));
			 
			 t.setDynamicImage(AWSS3Util.getAbsoluteUrl(t.getDynamicImage()));
			 t.setImageUrl(AWSS3Util.getAbsoluteUrl(t.getImageUrl()));
		});
		return list;
	}
	
	@Override
	public LiveGift getBarrage() {
		QueryWrapper<LiveGift> queryWrapper = new QueryWrapper<LiveGift>();
		queryWrapper.lambda().eq(LiveGift::getGiftType, 8).last("LIMIT 1");
		return slaveLiveGiftMapper.selectOne(queryWrapper);
	}

}
