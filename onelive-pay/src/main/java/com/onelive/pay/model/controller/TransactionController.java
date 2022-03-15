package com.onelive.pay.model.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.onelive.common.annotation.SupperAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.model.req.pay.TransactionReq;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.PayGoldChangeVO;
import com.onelive.common.model.vo.pay.TransactionTypeVO;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.redis.GameRedisUtils;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.pay.common.utils.ApiBusinessRedisUtils;
import com.onelive.pay.model.business.MemGoldchangeBusiness;
import com.onelive.pay.service.LiveGiftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemGoldchangeController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/15 17:10
 */
@RestController
@RequestMapping("/transaction")
@Api(tags = "交易记录-api")
@Slf4j
public class TransactionController extends BaseController {

    @Resource
    private MemGoldchangeBusiness memGoldchangeBusiness;



    @ApiOperation("查询交易记录")
    @PostMapping("/app/v1/getListPage")
    public ResultInfo< List<PayGoldChangeVO>> getListPage(@RequestBody TransactionReq req) {
        AppLoginUser user = getLoginUserAPP();
        return ResultInfo.ok(memGoldchangeBusiness.getListPage(req, user));
    }

    @ApiOperation("查询交易记录-类型")
    @PostMapping("/app/v1/transactionTypeList")
    public ResultInfo<List<TransactionTypeVO>> transactionTypeList() {
        AppLoginUser user = getLoginUserAPP();
        return ResultInfo.ok(memGoldchangeBusiness.transactionTypeList(user));
    }

    @ApiOperation("test")
    @PostMapping("/app/v1/test")
    @SupperAccess
    public ResultInfo<Object> test() {
//        List<LiveGiftForIndexDto> list2=ApiBusinessRedisUtils.get("gift_list::lang_zh_CN_useCountry_null");
        Object str=RedisUtil.get(RedisKeys.SYS_LANG_LIST);
        LinkedList<String> linkedList = GameRedisUtils.getLangList2();
//        List<String> langList = GameRedisUtils.getLangList();
//        String lang = StringUtils.isEmpty(LoginInfoUtil.getLang()) ? "zh_CN" : LoginInfoUtil.getLang();
//        List<LiveGiftForIndexDto> list=liveGiftService.getList(LoginInfoUtil.getCountryId(),lang);
        log.info("======list:" + JSONObject.toJSONString(linkedList));
        return ResultInfo.ok();
    }




}
