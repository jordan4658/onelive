package com.onelive.manage.modules.advertising.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.sys.SysAdvAreaListVO;
import com.onelive.manage.modules.advertising.business.SysAdvAreaBusiness;
import com.onelive.manage.modules.base.BaseAdminController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @Description: 广告设置
 */
@RestController
@RequestMapping(value = "/advArea")
@Api(tags = "平台管理-广告设置")
public class SysAdvAreaController extends BaseAdminController {

    @Resource
    private SysAdvAreaBusiness sysAdvAreaBusiness;

    @GetMapping("/v1/list")
    @ApiOperation("获取列表")
    public ResultInfo<List<SysAdvAreaListVO>> getList() {
        List<SysAdvAreaListVO> list = sysAdvAreaBusiness.getList();
        return ResultInfo.ok(list);
    }

}
