package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
import com.onelive.common.model.vo.sys.SysCountryTimeZoneVO;
import com.onelive.common.model.vo.sys.SysLangListVO;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.mybatis.mapper.master.sys.SysCountryMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysCountryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.pay.service.SysCountryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Resource
    private SlaveSysCountryMapper slaveSysCountryMapper;

    /* （非 Javadoc）
     * @see com.onelive.manage.service.sys.SysCountryService#queryCountryList()
     */
    @Override
    public List<SelectVO> queryCountryList() {
        List<SelectVO> voList = new ArrayList<>();
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<SysCountry> list = slaveSysCountryMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(list)) {
            voList = list.stream().map(a -> {
                SelectVO vo = new SelectVO();
                vo.setValue(a.getId());
                vo.setDes(a.getZhName());
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }

    @Override
    public List<SelectStringVO> getCurrencyList() {
        List<SelectStringVO> voList = new ArrayList<>();
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<SysCountry> list = slaveSysCountryMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(list)) {
            voList = list.stream().map(a -> {
                SelectStringVO vo = new SelectStringVO();
                vo.setValue(a.getLocalCurrency());
                vo.setMsg(a.getZhName());
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }

    @Override
    public List<SysCountry> getAllCountry() {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        return this.baseMapper.selectList(queryWrapper);
    }

    /* （非 Javadoc）
     * @see com.onelive.manage.service.sys.SysCountryService#queryLangList()
     */
    @Override
    public List<SysLangListVO> queryLangList() {
        // TODO
        List<SysLangListVO> voList = new ArrayList<>();
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<SysCountry> list = slaveSysCountryMapper.selectList(queryWrapper);
        Map<String, SysLangListVO> voMap = new HashMap<String, SysLangListVO>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysCountry country : list) {
                if (!voMap.containsKey(country.getLang())) {
                    SysLangListVO vo = new SysLangListVO();
                    vo.setLang(country.getLang());
                    vo.setZhName(country.getZhName());
                    vo.setLangZh(country.getLangZh());
                    voMap.put(country.getLang(), vo);
                }
            }
        }
        voList.addAll(voMap.values());
        return voList;
    }

    @Override
    public SysCountry getCountryByAreaCode(String areaCode) {
        if (StringUtils.isNotBlank(areaCode)) {
            areaCode = areaCode.replace("+", "");
        }
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountry::getAreaCode, areaCode);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        return this.baseMapper.selectOne(queryWrapper);
    }


    @Override
    public List<SysCountryAreaCodeVO> getAreaCodeList() {
        List<SysCountryAreaCodeVO> voList = new ArrayList<>();
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<SysCountry> list = slaveSysCountryMapper.selectList(queryWrapper);

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
    public List<SysCountryTimeZoneVO> getCountryWithTimeZoneList() {
        List<SysCountryTimeZoneVO> voList = new ArrayList<>();
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getMerchantCode, LoginInfoUtil.getMerchantCode());
        List<SysCountry> list = slaveSysCountryMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(list)) {
            voList = list.stream().map(a -> {
                SysCountryTimeZoneVO vo = new SysCountryTimeZoneVO();
                vo.setId(a.getId());
                vo.setCountry(a.getZhName());
                vo.setTimeZone(a.getTimeZone());
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }

    @Override
    public SysCountry getCountryByLocalCurrency(String currencyCode) {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getLocalCurrency, currencyCode);
        return slaveSysCountryMapper.selectOne(queryWrapper);
    }

    @Override
    public SysCountry getByLang(String lang) {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getLang, lang);
        return slaveSysCountryMapper.selectOne(queryWrapper);
    }

    @Override
    public SysCountry getByCountryCode(String code) {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<SysCountry>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        queryWrapper.lambda().eq(SysCountry::getCountryCode, code);
        return slaveSysCountryMapper.selectOne(queryWrapper);
    }
}
