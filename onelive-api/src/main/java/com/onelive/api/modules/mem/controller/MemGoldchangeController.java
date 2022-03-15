package com.onelive.api.modules.mem.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.mem.business.MemGoldchangeBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.mem.ChangeSilverBeanVo;
import com.onelive.common.model.vo.mem.MemWalletBalanceVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/silverBean")
@Api(tags = "银豆相关接口")
public class MemGoldchangeController extends BaseController {

    @Resource
    private MemGoldchangeBusiness memGoldchangeBusiness;

    @ApiOperation("用户金币兑换银豆")
    @PostMapping("/app/V1/changeSilverBean")
    public ResultInfo<MemWalletBalanceVO> changeSilverBean(@RequestBody ChangeSilverBeanVo changeSilverBeanVo) {
        return ResultInfo.ok(memGoldchangeBusiness.changeSilverBean(changeSilverBeanVo));

    }

}
