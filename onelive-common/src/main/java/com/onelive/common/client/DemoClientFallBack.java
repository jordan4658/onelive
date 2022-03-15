package com.onelive.common.client;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.demo.TestUserReq;
import com.onelive.common.model.vo.demo.TestUserVO;
import org.springframework.stereotype.Component;

/**
 * @ClassName DemoClientFallBack
 * @Desc TODO
 * @Date 2021/3/15 14:41
 */
@Component
public class DemoClientFallBack implements DemoClient {

    @Override
    public ResultInfo getPcDemo(String info) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo<TestUserVO> getDemo(String userId, Integer age) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo<String> insertDemo(TestUserReq req) {
        return ResultInfo.error();
    }
}
    