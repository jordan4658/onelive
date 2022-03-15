package com.onelive.anchor.modules.account.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.anchor.modules.account.business.FamilyBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.enums.SysBusParamEnum;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.req.login.FrozenAnchorReq;
import com.onelive.common.model.req.login.ResetAnchorPasswordReq;
import com.onelive.common.model.req.mem.FamilSearchUserAnchorReq;
import com.onelive.common.model.req.mem.FamilyCreateAnchorReq;
import com.onelive.common.model.req.mem.FamilyFinancialRecordReq;
import com.onelive.common.model.req.mem.FamilyWithdrawAnchor;
import com.onelive.common.model.req.mem.FamilyWithdrawAnchorVo;
import com.onelive.common.model.req.mem.MemAccountBankDeleteReq;
import com.onelive.common.model.req.mem.MemBankAccountAddReq;
import com.onelive.common.model.req.mem.MemBankAccountUpdateReq;
import com.onelive.common.model.req.pay.WithdrawReq;
import com.onelive.common.model.vo.common.SelectBankStringVO;
import com.onelive.common.model.vo.login.AppLoginTokenVo;
import com.onelive.common.model.vo.mem.AnchorForFamilyVO;
import com.onelive.common.model.vo.mem.FamilyBankAccountVO;
import com.onelive.common.model.vo.mem.FamilyCanWithdrawVO;
import com.onelive.common.model.vo.mem.FamilyFinancialRecord;
import com.onelive.common.model.vo.mem.WithdrawIndexVO;
import com.onelive.common.model.vo.pay.PayWithdrawResultVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/family")
@Api(tags = "家族长相关接口")
public class FamilyController extends BaseController {

	@Resource
	private FamilyBusiness familyBusiness;
	
	@ResponseBody
	@ApiOperation("家长修改主播密码")
	@PostMapping("/app/v1/resetAnchorPassword")
	public ResultInfo<String> resetAnchorPassword(@RequestBody ResetAnchorPasswordReq req) {
		return familyBusiness.resetAnchorPassword(req);
	}
	
	@ResponseBody
	@ApiOperation("家长冻结/解冻主播账号")
	@PostMapping("/app/v1/frozenAnchor")
	public ResultInfo<AppLoginTokenVo> frozenAnchor(@RequestBody FrozenAnchorReq req) {
		return familyBusiness.frozenAnchor(req);
	}

	@PostMapping("/app/v1/createAnchor")
	@ApiOperation("新增主播信息")
	@ResponseBody
	public ResultInfo<String> createAnchor(@RequestBody FamilyCreateAnchorReq familyCreateAnchorReq) throws Exception {
		familyBusiness.createAnchor(familyCreateAnchorReq);
		return ResultInfo.ok();
	}

	@ApiOperation("当前家族下的主播列表")
	@ResponseBody
	@PostMapping("/app/v1/anchorList")
	public ResultInfo<PageInfo<AnchorForFamilyVO>> anchorList(@RequestBody FamilSearchUserAnchorReq userAnchorReq) {
		return ResultInfo.ok(familyBusiness.anchorList(userAnchorReq));
	}
	
	@ApiOperation("查询可以提现额，主播可提现额")
	@ResponseBody
	@PostMapping("/app/v1/canWithdraw")
	public ResultInfo<FamilyCanWithdrawVO> canWithdraw() {
		return ResultInfo.ok(familyBusiness.canWithdraw());
	}
	
	@ApiOperation("查询名下主播的可提现列表")
	@ResponseBody
	@PostMapping("/app/v1/canWithdrawAnchor")
	public ResultInfo<FamilyWithdrawAnchorVo> canWithdrawAnchor() {
		return ResultInfo.ok(familyBusiness.canWithdrawAnchor());
	}
	
	@ResponseBody
    @ApiOperation("提现主播金额，不传userid 即全部")
    @PostMapping("/app/V1/withdrawAnchor")
    public ResultInfo<Boolean> withdrawAnchor(@RequestBody FamilyWithdrawAnchor familyWithdrawAnchor) {
        return ResultInfo.ok(familyBusiness.withdrawAnchor(familyWithdrawAnchor));
    }
	
	@ResponseBody
    @ApiOperation("家族长提现余额到银行卡")
    @PostMapping("/app/V1/withdrawCash")
    public ResultInfo<PayWithdrawResultVO> withdrawCash(@RequestBody WithdrawReq withdrawReq) {
        return familyBusiness.withdrawCash(withdrawReq);
    }
	
	@ResponseBody
	@ApiOperation("家族长的提现index页面")
	@PostMapping("/app/V1/withdrawIndex")
	public ResultInfo<WithdrawIndexVO> withdrawIndex() {
		return ResultInfo.ok(familyBusiness.withdrawIndex());
	}

	@ResponseBody
	@ApiOperation("家族长的银行卡列表")
    @PostMapping("/app/V1/bankList")
    public ResultInfo<FamilyBankAccountVO> bankList() {
        return ResultInfo.ok(familyBusiness.bankList());
    }
	
	@ResponseBody
	@ApiOperation("家族长的财务记录")
	@PostMapping("/app/V1/financialRecord")
	public ResultInfo<PageInfo<FamilyFinancialRecord>> financialRecord(@RequestBody FamilyFinancialRecordReq familyFinancialRecordReq) {
		return ResultInfo.ok(familyBusiness.financialRecord(familyFinancialRecordReq));
	}

	@ResponseBody
	@ApiOperation("新增银行卡信息")
    @PostMapping("/app/V1/addBank")
    public ResultInfo<?> addBank(@ApiParam("会员新增银行卡信息") @RequestBody MemBankAccountAddReq memBankAccountAddReq) {
		AppLoginUser user = getLoginUserAPP();
		familyBusiness.addBank(memBankAccountAddReq, user);
        return ResultInfo.ok();
    }

	@ResponseBody
    @ApiOperation("更新银行卡信息")
    @PostMapping("/app/V1/updateBank")
    public ResultInfo<?> updateBank(@ApiParam("更新银行卡信息") @RequestBody MemBankAccountUpdateReq memBankAccountUpdateReq) {
    	familyBusiness.updateBank(memBankAccountUpdateReq);
        return ResultInfo.ok();
    }

	@ResponseBody
    @ApiOperation("删除银行卡信息")
    @PostMapping("/app/V1/deleteBank")
    public ResultInfo<?> deleteBank(@ApiParam("删除银行卡信息") @RequestBody MemAccountBankDeleteReq memAccountBankDeleteReq) {
    	familyBusiness.deleteBank(memAccountBankDeleteReq);
        return ResultInfo.ok();
    }

	@ResponseBody
    @ApiOperation("获取银行下拉信息-未分页")
    @PostMapping("/app/V1/getBankList")
    public ResultInfo<List<SelectBankStringVO>> getByBankList() {
        List<SelectBankStringVO> list = familyBusiness.getByBankList(SysBusParamEnum.BANKLIST.getCode());
        return ResultInfo.ok(list);
    }
}
