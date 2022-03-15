package com.onelive.manage.modules.sys.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.sys.SysDateTimeVO;
import com.onelive.manage.modules.base.BaseAdminController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description: 系统时间
 * @date 2021/10/16
 */
@RestController
@RequestMapping(value = "/sysTime")
@Slf4j
@Api(tags = "系统管理-系统时间")
public class SysTimeController extends BaseAdminController {

    @GetMapping("/v1/getServerTime")
    @ApiOperation("获取服务器时间")
    public ResultInfo<SysDateTimeVO> getServerTime() {
        SysDateTimeVO vo = new SysDateTimeVO();
        vo.setTime(System.currentTimeMillis());
        return ResultInfo.ok(vo);
    }
}
