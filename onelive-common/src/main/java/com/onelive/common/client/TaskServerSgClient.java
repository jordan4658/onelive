package com.onelive.common.client;

import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.other.ServerConstants;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.operate.LotterySGListDTO;
import com.onelive.common.model.req.operate.LotterySGRecordReq;
import com.onelive.common.model.vo.operate.LotterySGReocrdVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 开奖服务远程调用
 */
@FeignClient(value = ServerConstants.TASK_SERVER_SG, fallback = TaskServerSgClientFallBack.class)
public interface TaskServerSgClient {

    @RequestMapping(name = "查询开奖记录",value = "/sgrecord/getSgRecordList.json", method = RequestMethod.POST)
    ResultInfo<PageInfo<LotterySGReocrdVO>> getSgRecordList(@RequestBody LotterySGRecordReq req);

    @RequestMapping(name = "查询有开奖记录的彩票",value = "/sgrecord/getSgLotteryList", method = RequestMethod.POST)
    ResultInfo<List<LotterySGListDTO>> getSgLotteryList();

}
