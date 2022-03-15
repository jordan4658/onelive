package com.onelive.api.modules.mem.controller;


import com.onelive.api.modules.mem.business.MemUserExpenseRecordBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.mem.MemUserExpenseRecordAddReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:  用户经验值接口
 */
@RestController
@RequestMapping("/userEmpirical")
@Api(tags = "用户消费记录接口")
@Slf4j
public class MemUserExpenseRecordController extends BaseController {

    @Resource
    private MemUserExpenseRecordBusiness memUserExpenseRecordBusiness;

    @ApiOperation("新增消费记录")
    @PostMapping(value = {"/app/v1/addExpenseRecord", "/pc/v1/addExpenseRecord"})
    public ResultInfo<String> addExpenseRecord(@RequestBody MemUserExpenseRecordAddReq req) {
        memUserExpenseRecordBusiness.addExpenseRecord(req);
       return ResultInfo.ok();
    }


}
