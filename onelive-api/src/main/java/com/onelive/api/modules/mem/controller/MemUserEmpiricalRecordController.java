package com.onelive.api.modules.mem.controller;


import com.onelive.api.modules.mem.business.MemUserEmpiricalRecordBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.mem.MemUserEmpiricalRecordAddReq;
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
@Api(tags = "用户经验值接口")
@Slf4j
public class MemUserEmpiricalRecordController extends BaseController {

    @Resource
    private MemUserEmpiricalRecordBusiness memUserEmpiricalLogBusiness;

    @ApiOperation("新增经验值记录")
    @PostMapping(value = {"/app/v1/addEmpiricalRecord", "/pc/v1/addEmpiricalRecord"})
    public ResultInfo<String> addEmpiricalRecord(@RequestBody MemUserEmpiricalRecordAddReq req) {
        memUserEmpiricalLogBusiness.addEmpiricalRecord(req);
       return ResultInfo.ok();
    }


}
