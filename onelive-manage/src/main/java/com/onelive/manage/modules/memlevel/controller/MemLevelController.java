package com.onelive.manage.modules.memlevel.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.finance.memlevel.MemLevelListReq;
import com.onelive.common.model.req.finance.memlevel.MemLevelSaveReq;
import com.onelive.common.model.req.finance.memlevel.MemLevelUpdateReq;
import com.onelive.common.model.vo.finance.memlevel.MemLevelVO;
import com.onelive.manage.modules.memlevel.business.MemLevelBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * MemLevelController
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月26日 下午11:23:27
 */
@Slf4j
@Api(tags = "会员层级")
@RestController
@RequestMapping("/pc/v1/finance/memLevel")
public class MemLevelController {

    @Resource
    private MemLevelBusiness memLevelBusiness;

    /**
     * list
     *
     * @param memLevelListReq
     * @return ResultInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:31:23
     */
    @AllowAccess
    @GetMapping(value = "/list")
    @ApiOperation("获取会员层级列表")
    public ResultInfo<PageInfo<MemLevelVO>> list(MemLevelListReq memLevelListReq) {
        return ResultInfo.ok(memLevelBusiness.getList(memLevelListReq));
    }

    /**
     * get
     *
     * @param id
     * @return ResultInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:31:23
     */
    @AllowAccess
    @GetMapping(value = "/info/{id}")
    @ApiOperation("获取会员层级详情")
    public ResultInfo<MemLevelVO> get(@PathVariable final Long id) {
        return ResultInfo.ok(memLevelBusiness.getInfo(id));
    }

    /**
     * save
     *
     * @param memLevelSaveReq
     * @return ResultInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:31:23
     */
    @AllowAccess
    @PostMapping
    @ApiOperation("新增会员层级")
    public ResultInfo<String> save(@RequestBody @Valid MemLevelSaveReq memLevelSaveReq) {
        if (memLevelBusiness.save(memLevelSaveReq)) {
            return ResultInfo.ok("成功");
        } else {
            return ResultInfo.error("失败,请重试");
        }
    }

    /**
     * update
     *
     * @param id
     * @param memLevelUpdateReq
     * @return ResultInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:31:23
     */
    @AllowAccess
    @PutMapping(value = "/{id}")
    @ApiOperation("修改会员层级")
    public ResultInfo<String> update(@PathVariable final Long id, @RequestBody @Valid MemLevelUpdateReq memLevelUpdateReq) {
        memLevelUpdateReq.setId(id);
        if (memLevelBusiness.update(memLevelUpdateReq)) {
            return ResultInfo.ok("成功");
        } else {
            return ResultInfo.error("失败,请重试");
        }
    }

    /**
     * delete
     *
     * @param ids
     * @return ResultInfo
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午7:31:23
     */
    @AllowAccess
    @DeleteMapping(value = "/{ids}")
    @ApiOperation("删除会员层级")
    public ResultInfo<String> delete(@PathVariable final Long[] ids) {
        if (memLevelBusiness.delete(ids)) {
            return ResultInfo.ok("成功");
        } else {
            return ResultInfo.error("失败,请重试");
        }
    }
}
