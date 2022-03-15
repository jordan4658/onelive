package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.model.vo.sys.CountryCodeVO;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
import com.onelive.common.model.vo.sys.SysCountryTimeZoneVO;
import com.onelive.common.model.vo.sys.SysLangListVO;
import com.onelive.common.mybatis.entity.SysCountry;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
public interface SysCountryService extends IService<SysCountry> {

	 public List<SelectVO> queryCountryList();

	 public List<SelectStringVO> getCurrencyList();
	 /**
	  * 获取语种
	  */
	 public List<SysLangListVO> queryLangList();
    /**
     * 获取所有可用的语言
     * @return
     */
    List<SysCountry> getAllCountry();


	/**
	 * 通过区号标识获取所在国家信息
	 * @param areaCode
	 * @return
	 */
	SysCountry getCountryByAreaCode(String areaCode);

	/**
	 * 查询国家区号列表
	 * @return
	 */
    List<SysCountryAreaCodeVO> getAreaCodeList();

	/**
	 * 查询国家和时区
	 * @return
	 */
	List<SysCountryTimeZoneVO> getCountryWithTimeZoneList();

	/**
	 * 根据国家唯一标识查询国家信息
	 * @param currencyCode
	 * @return
	 */
	SysCountry getCountryByLocalCurrency(String currencyCode);

	/**
	 * 查询国家code列表
	 * @return
	 */
	List<CountryCodeVO> countryCodeList();

	/**
	 * 根据国家code查询国家信息
	 * @param countryCode
	 * @return
	 */
	SysCountry getByCountryCode(String countryCode);
}
