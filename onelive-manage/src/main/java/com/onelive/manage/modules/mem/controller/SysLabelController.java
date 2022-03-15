package com.onelive.manage.modules.mem.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.SysLabelBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SysLabelController
 * @Desc 主播标签接口管理
 * @Date 2021/4/5 17:06
 */
@RestController
@RequestMapping("/label")
@Api(tags = "主播管理-标签接口")
@Slf4j
public class SysLabelController extends BaseAdminController {

    @Resource
    private SysLabelBusiness sysLabelBusiness;

    @GetMapping("/v1/list")
    @ApiOperation("获取标签列表接口")
    public ResultInfo<List<SelectStringVO>> queryLabelList() {
        List<SelectStringVO> list = sysLabelBusiness.queryLabelList();
        return ResultInfo.ok(list);
    }
}    
    