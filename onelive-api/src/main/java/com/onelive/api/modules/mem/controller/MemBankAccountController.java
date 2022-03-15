package com.onelive.api.modules.mem.controller;

import com.onelive.api.modules.mem.business.MemBankAccountBusiness;
import com.onelive.api.modules.sys.business.SysBusParameterBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.enums.SysBusParamEnum;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.req.mem.MemAccountBankDeleteReq;
import com.onelive.common.model.req.mem.MemBankAccountAddReq;
import com.onelive.common.model.req.mem.MemBankAccountUpdateReq;
import com.onelive.common.model.vo.common.SelectBankStringVO;
import com.onelive.common.model.vo.mem.MemBankAccountVO;
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
 * @ClassName: MemBankAccountController
 * @date 创建时间：2021/4/6 11:27
 */
@Slf4j
@RestController
@RequestMapping("/memBank")
@Api(tags = "会员银行卡管理")
public class MemBankAccountController extends BaseController {

    @Resource
    private MemBankAccountBusiness memBankAccountBusiness;

    @Resource
    private SysBusParameterBusiness sysBusParameterBusiness;

    @ApiOperation("查询会员的绑定的银行卡列表-未分页")
    @PostMapping("/app/V1/list")
    public ResultInfo<List<MemBankAccountVO>> list() {
        AppLoginUser user = getLoginUserAPP();
        List<MemBankAccountVO> list = memBankAccountBusiness.list(user);
        return ResultInfo.ok(list);
    }

    @ApiOperation("会员新增银行卡信息")
    @PostMapping("/app/V1/add")
    public ResultInfo add(@ApiParam("会员新增银行卡信息") @RequestBody MemBankAccountAddReq memBankAccountAddReq) throws Exception {
        AppLoginUser user = getLoginUserAPP();
        memBankAccountBusiness.add(memBankAccountAddReq, user);
        return ResultInfo.ok();
    }

    @ApiOperation("会员更新银行卡信息")
    @PostMapping("/app/V1/update")
    public ResultInfo update(@ApiParam("会员更新银行卡信息") @RequestBody MemBankAccountUpdateReq memBankAccountUpdateReq) throws Exception {
        AppLoginUser user = getLoginUserAPP();
        memBankAccountBusiness.update(memBankAccountUpdateReq, user);
        return ResultInfo.ok();
    }

    @ApiOperation("用户删除银行卡信息")
    @PostMapping("/app/V1/delete")
    public ResultInfo delete(@ApiParam("删除银行卡信息") @RequestBody MemAccountBankDeleteReq memAccountBankDeleteReq) throws Exception {
        AppLoginUser user = getLoginUserAPP();
        memBankAccountBusiness.delete(memAccountBankDeleteReq, user);
        return ResultInfo.ok();
    }

    @ApiOperation("获取银行下拉信息-未分页")
    @PostMapping("/app/V1/getBankList")
    public ResultInfo<List<SelectBankStringVO>> getByBankList() {
        List<SelectBankStringVO> list = sysBusParameterBusiness.getByBankList(SysBusParamEnum.BANKLIST.getCode());
        return ResultInfo.ok(list);
    }

}
