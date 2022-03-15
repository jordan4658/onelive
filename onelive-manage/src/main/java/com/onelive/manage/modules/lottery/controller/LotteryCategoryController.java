package com.onelive.manage.modules.lottery.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.lottery.LotteryCategoryDelReq;
import com.onelive.common.model.req.lottery.LotteryCategorySaveReq;
import com.onelive.common.model.vo.lottery.LotteryCategoryEditVO;
import com.onelive.common.model.vo.lottery.LotteryCategoryVO;
import com.onelive.manage.modules.lottery.business.LotteryCategoryBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/lottery/category")
@Api(tags = "游戏管理-彩种分类")
@Slf4j
public class LotteryCategoryController {

    @Resource
    private LotteryCategoryBusiness lotteryCategoryBusiness;

    @PostMapping("/page")
    @ApiOperation("查询彩票分类所有信息")
    public ResultInfo<PageInfo<LotteryCategoryVO>> queryLotteryCategory(@RequestBody(required = false) PageReq req) {
        if(req == null){
            req = new PageReq();
        }
        return ResultInfo.ok(lotteryCategoryBusiness.queryLotteryCategory(req));
    }


    @GetMapping("/info/{id}")
    @ApiOperation("根据主键获取彩种分类信息")
    public ResultInfo<LotteryCategoryEditVO> getLotteryCategoryById(@PathVariable(value = "id") Integer id) {
        return ResultInfo.ok(lotteryCategoryBusiness.getLotteryCategoryById(id));
    }

    @PostMapping("/save")
    @ApiOperation("保存彩种分类信息")
    public ResultInfo<Boolean> save(@RequestBody LotteryCategorySaveReq req) {
        lotteryCategoryBusiness.save(req);
        return ResultInfo.ok();
    }

    @PostMapping("/del")
    @ApiOperation("删除彩种分类信息")
    public ResultInfo<Boolean> del(@RequestBody LotteryCategoryDelReq req) {
        lotteryCategoryBusiness.del(req.getId());
        return ResultInfo.ok();
    }

    @PostMapping("/ZIP")
    @ApiOperation("生成压缩包")
//    @AllowAccess
    public ResultInfo<String> generateZIP() throws Exception{
        lotteryCategoryBusiness.generateZIP();
        return ResultInfo.ok();
    }


}
