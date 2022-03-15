package com.onelive.anchor.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.SysCountryService;
import com.onelive.anchor.util.AnchorBusinessRedisUtils;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.mybatis.mapper.master.sys.SysCountryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.redis.SysBusinessRedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
@Service
public class SysCountryServiceImpl extends ServiceImpl<SysCountryMapper, SysCountry> implements SysCountryService {

    @Override
    public SysCountry getCountryByCountryCode(String countryCode) {
        SysCountry country = AnchorBusinessRedisUtils.getCountryInfo(countryCode);
        if (country == null) {
            QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysCountry::getCountryCode, countryCode);
            queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
            country = this.baseMapper.selectOne(queryWrapper);
            if (country != null) {	
                AnchorBusinessRedisUtils.setCountryInfo(countryCode, JSON.toJSONString(country));
            }
        }
        return country;
    }

    @Override
    public List<SysCountry> getAllAvailableCountry() {
        List<SysCountry> countryList = SysBusinessRedisUtils.getCountryList();
        if (CollectionUtils.isEmpty(countryList)) {
            QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
            queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
            queryWrapper.lambda().orderByDesc(SysCountry::getSort);
            countryList = this.baseMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(countryList)) {
                SysBusinessRedisUtils.setCountryList(countryList);
            }
        }
        return countryList;
    }

    @Override
    public List<SysCountry> getAllCountry() {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public SysCountry getCountryByEnName(String enName) {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountry::getEnName, enName).last("limit 1");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysCountryAreaCodeVO> getAreaCodeList() {
        List<SysCountryAreaCodeVO> voList = new ArrayList<>();
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<SysCountry> list = this.baseMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(list)) {
            voList = list.stream().map(a -> {
                SysCountryAreaCodeVO vo = new SysCountryAreaCodeVO();
                vo.setCountry(a.getZhName());
                vo.setAreaCode(a.getAreaCode());
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }

    @Override
    public SysCountry getByCountryCode(String registerCountryCode) {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountry::getCountryCode, registerCountryCode)
                .eq(SysCountry::getIsFrozen, false)
                .last("limit 1");
        return this.baseMapper.selectOne(queryWrapper);
    }
}
