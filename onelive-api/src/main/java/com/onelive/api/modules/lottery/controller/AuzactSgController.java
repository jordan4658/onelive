package com.onelive.api.modules.lottery.controller;

import com.onelive.common.client.OneliveOrderClient;
import com.onelive.common.model.common.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 澳洲ACT资讯
 *
 * @author
 * @create 2019-01-13 15:20
 **/
@Api(tags = "澳洲ACT资讯")
@Slf4j
@RestController
@RequestMapping("/ausactSg")
public class AuzactSgController {

    private static final Logger logger = LoggerFactory.getLogger(AuzactSgController.class);

    @Resource
    private OneliveOrderClient oneliveOrderClient;

    /**
     * 澳洲ACT大小
     *
     * @param
     * @return
     */
    @ApiOperation("澳洲ACT大小")
    @PostMapping("/bigOrSmall.json")
    public ResultInfo<List<Map<String, Object>>> bigOrSmall() {
        try {
            return oneliveOrderClient.bigOrSmall();
        } catch (Exception e) {
            logger.error("/bigOrSmall.json has error!", e);
            return ResultInfo.error("澳洲ACT大小出错");
        }

    }

    /**
     * 澳洲ACT单双
     *
     * @return
     */
    @ApiOperation("澳洲ACT单双")
    @PostMapping("/singleAndDouble.json")
    public ResultInfo<List<Map<String, Object>>> singleAndDouble() {
        try {
            return oneliveOrderClient.singleAndDouble();
        } catch (Exception e) {
            logger.error("/singleAndDouble.json has error!", e);
            return ResultInfo.error("澳洲ACT单双出错");
        }
    }

    /**
     * 澳洲ACT五行
     *
     * @param
     * @return
     */
    @ApiOperation("澳洲ACT五行")
    @PostMapping("/fiveElements.json")
    public ResultInfo<List<Map<String, Object>>> fiveElements() {
        try {
            return oneliveOrderClient.fiveElements();
        } catch (Exception e) {
            logger.error("/fiveElements.json has error!", e);
            return ResultInfo.error("澳洲ACT五行出错");
        }

    }

}
