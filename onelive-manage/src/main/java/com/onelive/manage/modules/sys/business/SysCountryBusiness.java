package com.onelive.manage.modules.sys.business;

import com.alibaba.fastjson.JSON;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.sys.CreateCountryAddrDTO;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.model.vo.sys.CountryCodeVO;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
import com.onelive.common.model.vo.sys.SysCountryTimeZoneVO;
import com.onelive.common.model.vo.sys.SysLangListVO;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.manage.common.config.RabbitConfig;
import com.onelive.manage.service.sys.SysCountryAddrService;
import com.onelive.manage.service.sys.SysCountryService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName SysCountryBusiness
 * @Desc 国家地区业务类
 * @Date 2021/10/16 17:08
 */
@Component
public class SysCountryBusiness {

    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private SysCountryAddrService sysCountryAddrService;
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     *获取地区列表
     */
    public List<SelectVO> queryCountryList() {
        //代码中排除了中国
    	return sysCountryService.queryCountryList();
    }
    /**
     *获取地区列表
     */
    public List<SysLangListVO> queryLangList() {
    	return sysCountryService.queryLangList();
    }
    /**
     * 重新刷新国家信息
     */
    public void refreshCountry(){
        //缓存国家信息
        List<SysCountry> list =sysCountryService.getAllCountry();
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<SysCountry> iterator = list.iterator();
            while (iterator.hasNext()){
                SysCountry country = iterator.next();
                SysBusinessRedisUtils.setCountryInfo(country.getCountryCode(), JSON.toJSONString(country));
            }
        }
    }

    /**
     * 查询国家区号列表
     * @return
     */
    public List<SysCountryAreaCodeVO> getAreaCodeList() {
        return sysCountryService.getAreaCodeList();
    }

    /**
     * 生成国家地址数据
     * @param id 国家表ID
     */
    public void createCountyAddr(Long id) {
        //查询国家表
        SysCountry country = sysCountryService.getById(id);
        if(country==null){
            throw new BusinessException("数据不存在");
        }

        String lang = country.getLang();

        //异步生成数据
        CreateCountryAddrDTO dto = new CreateCountryAddrDTO();
        dto.setLang(lang);
        rabbitTemplate.convertAndSend(RabbitConfig.COUNTRY_EXCHANGE_TOPIC, "create.country", dto);
    }


    /**
     * 查询国家和时区
     * @return
     */
    public List<SysCountryTimeZoneVO> getCountryWithTimeZoneList() {
        return sysCountryService.getCountryWithTimeZoneList();
    }

    public List<SelectStringVO> getCurrencyList() {
        //代码中排除了中国
        return sysCountryService.getCurrencyList();
    }


    /**
     * 查询国家地区code列表
     * @return
     */
    public List<CountryCodeVO> countryCodeList() {
        //代码中排除了中国
        return sysCountryService.countryCodeList();
    }
}