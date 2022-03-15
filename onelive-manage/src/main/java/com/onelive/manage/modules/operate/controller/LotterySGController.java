package com.onelive.manage.modules.operate.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.operate.LotterySGRecordReq;
import com.onelive.common.model.vo.operate.LotterySGListVO;
import com.onelive.common.model.vo.operate.LotterySGReocrdVO;
import com.onelive.manage.modules.operate.business.LotterySGBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/operate/lotterysg")
@Api(tags = "运营管理-开奖记录")
@Slf4j
public class LotterySGController {

    @Resource
    private LotterySGBusiness lotterySGBusiness;

    @PostMapping("/queryLotterySGList")
    @ApiOperation("查询开奖记录")
    public ResultInfo<PageInfo<LotterySGReocrdVO>> queryLotteryList(@RequestBody LotterySGRecordReq req) {
        return ResultInfo.ok(lotterySGBusiness.getSgRecordList(req));
    }

    @PostMapping("/getSgLotteryList")
    @ApiOperation("查询有开奖记录的彩票")
    public ResultInfo<List<LotterySGListVO>> getSgLotteryList() {
        return ResultInfo.ok(lotterySGBusiness.getSgLotteryList());
    }

}
