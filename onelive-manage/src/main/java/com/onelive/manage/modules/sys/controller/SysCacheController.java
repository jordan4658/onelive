package com.onelive.manage.modules.sys.controller;


import com.onelive.common.enums.CachTypeEnums;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.sys.SysCacheKeyReq;
import com.onelive.common.model.req.sys.SysCacheReq;
import com.onelive.common.model.req.sys.SysCacheSetReq;
import com.onelive.common.model.req.sys.SysCacheTypeReq;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysCacheBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName SysCacheController
 * @Desc 系统参数缓存管理-迁移
 * @Date 2021/4/6 11:46
 */
@RestController
@RequestMapping(value = "/cache")
@Api(tags = "系统管理-缓存管理")
@Slf4j
public class SysCacheController extends BaseAdminController {

    @Resource
    private SysCacheBusiness sysCacheBusiness;


    @Log("删除指定的缓存，多个以逗号分隔")
    @PostMapping(name = "删除指定的缓存，多个以逗号分隔", value = "/v1/flushCache")
    @ApiOperation("删除指定的缓存，多个以逗号分隔")
    public ResultInfo<Boolean> flushCache(@RequestBody SysCacheKeyReq req) {
        return sysCacheBusiness.flushCache(req.getKey());
    }
    
    @Log("删除缓存,前缀删除")
    @PostMapping(name = "删除缓存,前缀删除", value = "/v1/flushPrefixCache")
    @ApiOperation("删除缓存,前缀删除")
    public ResultInfo<Boolean> flushPrefixCache(@RequestBody SysCacheKeyReq req) {
    	return sysCacheBusiness.flushPrefixCache(req.getKey());
    }

    @PostMapping(name = "查看指定的缓存", value = "/v1/listCache")
    @ApiOperation("查看指定的缓存")
    public ResultInfo listCache(@RequestBody SysCacheReq req) {
        return ResultInfo.ok(sysCacheBusiness.listCache(req));
    }

    @Log("删除指定类型缓存")
    @PostMapping(name = "删除指定类型缓存", value = "/v1/delCacheByType")
    @ApiOperation("删除指定类型缓存")
    public ResultInfo<Boolean> delCacheByType(@RequestBody SysCacheTypeReq req) {
        return sysCacheBusiness.delCacheByType(req.getType());
    }

    @Log("添加字符型缓存")
    @PostMapping(name = "添加字符型缓存", value = "/v1/addStringCache")
    @ApiOperation("添加字符型缓存")
    public ResultInfo<Boolean> addStringCache(@RequestBody SysCacheSetReq req) {
        return sysCacheBusiness.addStringCache(req.getKey(), req.getValue());
    }

    @GetMapping("/v1/cacheType")
    @ApiOperation("缓存类型下拉选项")
    public ResultInfo<List<SelectStringVO>> cacheType() {
        List<SelectStringVO> list = new ArrayList<>();
        for (CachTypeEnums t : CachTypeEnums.values()) {
            SelectStringVO vo = new SelectStringVO();
            vo.setValue(t.name());
            vo.setMsg(t.getMsg());
            list.add(vo);
        }
        return ResultInfo.ok(list);
    }

}    
    