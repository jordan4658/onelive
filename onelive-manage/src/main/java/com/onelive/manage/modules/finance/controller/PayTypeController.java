package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayTypeBackAddReq;
import com.onelive.common.model.req.pay.PayTypeBackUpReq;
import com.onelive.common.model.vo.pay.PayTypeBackVO;
import com.onelive.common.model.vo.pay.PayTypeSelectVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayTypeBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayTypeController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 18:59
 */
@Slf4j
@RestController
@RequestMapping("/payType")
@Api(tags = "财务管理-支付类型管理")
public class PayTypeController extends BaseAdminController {

    @Resource
    private PayTypeBusiness payTypeBusiness;

    @ApiOperation("查询-支付类型列表-分页")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PayTypeBackVO>> listPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("支付类型名称") @RequestParam(value = "payTypeName", required = false) String payTypeName) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payTypeBusiness.listPage(user, pageNum, pageSize, payTypeName));
    }

    @Log("新增-支付类型")
    @ApiOperation("新增-支付类型")
    @PostMapping("/v1/add")
    public ResultInfo add(@ApiParam("支付类型新增信息") @RequestBody PayTypeBackAddReq payTypeBackAddReq) {
        LoginUser user = getLoginAdmin();
        payTypeBusiness.add(payTypeBackAddReq, user);
        return ResultInfo.ok();
    }

    @Log("更新-支付类型")
    @ApiOperation("更新-支付类型")
    @PostMapping("/v1/update")
    public ResultInfo update(@ApiParam("支付类型更新信息") @RequestBody PayTypeBackUpReq payTypeBackUpReq) {
        LoginUser user = getLoginAdmin();
        payTypeBusiness.update(payTypeBackUpReq, user);
        return ResultInfo.ok();
    }


    @Log("查询-支付类型")
    @ApiOperation("查询-支付类型")
    @PostMapping("/v1/search")
    public ResultInfo<List<PayTypeSelectVO>> select() {
        return ResultInfo.ok( payTypeBusiness.select());
    }
}
