package com.onelive.manage.modules.sys.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.model.vo.sys.CountryCodeVO;
import com.onelive.common.model.vo.sys.SysCountryAreaCodeVO;
import com.onelive.common.model.vo.sys.SysCountryTimeZoneVO;
import com.onelive.common.model.vo.sys.SysLangListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysCountryBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @Description: 国家地区
 * @date 2021/10/16
 */
@RestController
@RequestMapping(value = "/sysCountry")
@Slf4j
@Api(tags = "系统管理-国家地区")
public class SysCountryController extends BaseAdminController {

    @Resource
    private SysCountryBusiness sysCountryBusiness;

    @GetMapping("/v1/list")
    @ApiOperation("获取所有地区列表")
    public ResultInfo<List<SelectVO>> queryCountryList() {
        List<SelectVO> list = sysCountryBusiness.queryCountryList();
        return ResultInfo.ok(list);
    }

    @GetMapping("/v1/countryCodeList")
    @ApiOperation("获取所有地区列表")
    public ResultInfo<List<CountryCodeVO>> countryCodeList() {
        List<CountryCodeVO> list = sysCountryBusiness.countryCodeList();
        return ResultInfo.ok(list);
    }

    @GetMapping("/v1/getCurrencyList")
    @ApiOperation("获取所有国家列表(返回国家的local_currency和zh_name)")
    public ResultInfo<List<SelectStringVO>> getCurrencyList() {
        List<SelectStringVO> list = sysCountryBusiness.getCurrencyList();
        return ResultInfo.ok(list);
    }

    @GetMapping("/v1/getAreaCodeList")
    @ApiOperation("获取所有地区区号列表")
    public ResultInfo<List<SysCountryAreaCodeVO>> getAreaCodeList() {
        List<SysCountryAreaCodeVO> list = sysCountryBusiness.getAreaCodeList();
        return ResultInfo.ok(list);
    }


    @GetMapping("/v1/getCountryWithTimeZoneList")
    @ApiOperation("获取所有国家和时区列表")
    public ResultInfo<List<SysCountryTimeZoneVO>> getCountryWithTimeZoneList() {
        List<SysCountryTimeZoneVO> list = sysCountryBusiness.getCountryWithTimeZoneList();
        return ResultInfo.ok(list);
    }

    @GetMapping("/v1/langList")
    @ApiOperation("获取所有语种列表")
    public ResultInfo<List<SysLangListVO>> queryLangList() {
        List<SysLangListVO> list = sysCountryBusiness.queryLangList();
        return ResultInfo.ok(list);
    }

    @Log("刷新国家信息")
    @PostMapping("/v1/refresh")
    @ApiOperation("刷新国家信息")
    public ResultInfo refresh() {
        sysCountryBusiness.refreshCountry();
        return ResultInfo.ok();
    }


    @Log("生成国家地址数据")
    @PostMapping("/v1/createCountyAddr")
    @ApiOperation("生成国家地址数据")
    @ApiIgnore
    public ResultInfo createCountyAddr(Long id) {
        sysCountryBusiness.createCountyAddr(id);
        return ResultInfo.ok();
    }

}
