package com.onelive.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
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

    /**
     * 通过国家标识获取所在国家信息
     * @param countryCode
     * @return
     */
    SysCountry getCountryByCountryCode(String countryCode);

    /**
     * 获取所有可用的语言
     * @return
     */
    List<SysCountry> getAllAvailableCountry();

    /**
     * 获取所有可用的语言
     * @return
     */
    List<SysCountry> getAllCountry();

    /**
     * 根据enName查询国家
     * @param enName
     * @return
     */
    SysCountry getCountryByEnName(String enName);

    /**
     * 查询国家和区号
     * @return
     */
    List<SysCountryAreaCodeVO> getAreaCodeList();

    /**
     * 根据国家code编码查询国家信息
     * @param registerCountryCode
     * @return
     */
    SysCountry getByCountryCode(String registerCountryCode);

    SysCountry getCountryByAreaCode(String areaCode);
}
