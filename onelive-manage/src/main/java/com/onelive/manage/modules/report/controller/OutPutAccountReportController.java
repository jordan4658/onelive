package com.onelive.manage.modules.report.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.report.OutPutAccountVO;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.manage.modules.report.business.OutPutAccountReportBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: OutPutAccountReportController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/5/3 15:57
 */
@Slf4j
@RestController
@Api(tags = "统计-出入账统计")
@RequestMapping("/outPutAccount")
public class OutPutAccountReportController {

    @Resource
    private OutPutAccountReportBusiness outPutAccountReportBusiness;


    @ApiOperation("查询-平台出入账信息")
    @GetMapping("/getInfo")
    public ResultInfo<OutPutAccountVO> getInfo(@ApiParam("开始时间") @RequestParam(value = "startTime", required = false) String startTime,
                                               @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) String endTime) {
        OutPutAccountVO outPutAccountVO=outPutAccountReportBusiness.getInfo(DateInnerUtil.parseDate(startTime),DateInnerUtil.parseDate(endTime));
        return ResultInfo.ok(outPutAccountVO);
    }

}
