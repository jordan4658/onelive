package com.onelive.api.modules.lottery.controller;

import com.onelive.common.client.OneliveOrderClient;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.lottery.LotteryIdsReq;
import com.onelive.common.model.req.lottery.LotteryLiShiPageReq;
import com.onelive.common.utils.others.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 赛果查询控制类
 **/
@RestController
@RequestMapping("/sg")
@Api(tags = "彩票开奖历史接口")
@Slf4j
public class SgController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OneliveOrderClient oneliveOrderClient;


    /**
     * APP开奖模块首页彩种ID获取信息（官彩）首页
     *
     * @return
     */
    @ApiOperation("获取下一期彩种信息")
    @PostMapping("/getNewestSgInfoByIds.json")
    public ResultInfo<Map<String, Object>> getNewestSgInfobyids(@RequestBody LotteryIdsReq req) {
        try {
            //检查参数
            if (req == null || StringUtils.isBlank(req.getIds())) {
                return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
            }
            ResultInfo<Map<String, Object>> resultInfo = this.oneliveOrderClient.getNewestSgInfobyids(req);
            logger.info("SgController getNewestSgInfobyids is end.  requestData:{},resultInfo:{}", req, resultInfo);
            return resultInfo;
        } catch (Exception e) {
            logger.error("SgController getNewestSgInfobyids.json has error! param:[{}]", req, e);
            return ResultInfo.ok(null);
        }
    }


    /**
     * 查询app获取多个彩种的开奖历史信息
     */
    @ApiOperation("根据ID查询彩种的开奖历史信息")
    @PostMapping("/lishiSg.json")
    public ResultInfo<Map<String, Object>> lishiSg(@RequestBody LotteryLiShiPageReq req){
        Integer id = req.getId();
        if (null == id) {
            return ResultInfo.error("参数错误");
        }
        try {
            return oneliveOrderClient.lishiSg(req.getPageNo(),req.getPageSize(), id);
        }catch (Exception e){
            logger.error("查询app获取多个彩种的开奖历史信息出错,params:{}", id.toString(), e);
            return ResultInfo.error("查询app获取多个彩种的开奖历史信息出错");
        }
    }

    /**
     * APP开奖模块长龙
     */
    @ApiOperation("查询长龙")
    @PostMapping("/getSgLongDragons.json")
    public ResultInfo<List<Map<String, Object>>> getSgLongDragons() {
        try {
            return oneliveOrderClient.getSgLongDragons();
        } catch (Exception e) {
            logger.error("SgController getSgLongDragons.json has error! exception:{}", e);
            return ResultInfo.error("查询长龙出错");
        }
    }

}
