package com.onelive.api.service.index.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.index.LiveColumnService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.model.vo.index.LiveColumnVO;
import com.onelive.common.mybatis.entity.LiveColumn;
import com.onelive.common.mybatis.mapper.master.index.LiveColumnMapper;
import com.onelive.common.mybatis.mapper.slave.index.SlaveLiveColumnMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;

/**
 * <p>
 * 首页栏目服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-20
 */
@Service
public class LiveColumnServiceImpl extends ServiceImpl<LiveColumnMapper, LiveColumn> implements LiveColumnService {

	@Autowired
	private SlaveLiveColumnMapper slaveLiveColumnMapper;

	@Override
	public List<LiveColumnVO> getAll(String lang) {
		List<LiveColumnVO> cacheData = ApiBusinessRedisUtils.get(RedisCacheableKeys.LIVE_COLUMN + "lang_" + lang);
		if (!CollectionUtils.isEmpty(cacheData)) {
			return cacheData;
		}
		QueryWrapper<LiveColumn> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveColumn::getMerchantCode, LoginInfoUtil.getMerchantCode());
		queryWrapper.lambda().eq(LiveColumn::getLang, lang);
		queryWrapper.lambda().eq(LiveColumn::getIsOpen, 1);
		queryWrapper.lambda().orderByDesc(LiveColumn::getSortNo);
		List<LiveColumn> selectList = slaveLiveColumnMapper.selectList(queryWrapper);

		List<LiveColumnVO> result = BeanCopyUtil.copyCollection(selectList, LiveColumnVO.class);
		ApiBusinessRedisUtils.set(RedisCacheableKeys.LIVE_COLUMN + "lang_" + lang, result);
		return result;
	}

}
