package com.onelive.common.client;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.operate.LotterySGListDTO;
import com.onelive.common.model.req.operate.LotterySGRecordReq;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 开奖服务回调
 */
@Component
public class TaskServerSgClientFallBack implements TaskServerSgClient{
    @Override
    public ResultInfo getSgRecordList(LotterySGRecordReq req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo<List<LotterySGListDTO>> getSgLotteryList() {
        return ResultInfo.error();
    }
}
