package com.onelive.manage.modules.finance.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayHandleFinanceAmtBackReq;
import com.onelive.common.model.vo.pay.PayHandleFinanceAmtBackVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayHandleFinanceAmtBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayHandleFinanceAmtController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/19 11:00
 */
@Slf4j
@RestController
@RequestMapping("/handleFinanceAmt")
@Api(tags = "财务管理-人工加减款")
public class PayHandleFinanceAmtController extends BaseAdminController {

    @Resource
    private PayHandleFinanceAmtBusiness payHandleFinanceAmtBusiness;

    @ApiOperation("查询-人工加减款列表 分页")
    @GetMapping("/v1/listPage")
    public ResultInfo<PageInfo<PayHandleFinanceAmtBackVO>> listPage(
            @ApiParam("页码默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("条数默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("会员昵称") @RequestParam(value = "nickname", required = false) String nickname,
            @ApiParam("会员唯一标识") @RequestParam(value = "accno", required = false) String accno,
            @ApiParam("会员账号") @RequestParam(value = "account", required = false) String account) {
        LoginUser user = getLoginAdmin();
        return ResultInfo.ok(payHandleFinanceAmtBusiness.listPage(pageNum, pageSize, nickname, account, user,accno));
    }

    @Log("人工-加减款/加减码")
    @ApiOperation("人工-加减款/加减码")
    @PostMapping("/v1/addHandleFinanceAmt")
    public ResultInfo addHandleFinanceAmt(@ApiParam("加减款/加减码信息") @RequestBody PayHandleFinanceAmtBackReq payHandleFinanceAmtBackReq) {
        LoginUser user = getLoginAdmin();
        payHandleFinanceAmtBusiness.addHandleFinanceAmt(payHandleFinanceAmtBackReq, user);
        return ResultInfo.ok();
    }


}
