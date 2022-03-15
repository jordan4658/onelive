package com.onelive.common.client;


import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.demo.TestUserReq;
import com.onelive.common.model.vo.demo.TestUserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = ServerConstants.SPORT_APP, fallback = DemoClientFallBack.class)
public interface DemoClient {

    @GetMapping(name = "demo", value = "/getUser")
    ResultInfo getPcDemo(@RequestParam("info") String info);

    @GetMapping(value = "/demo/getUser")
    ResultInfo<TestUserVO> getDemo(@RequestParam("userId") String userId, @RequestParam("age") Integer age);

    @PostMapping(value = "/demo/insertUser")
    ResultInfo<String> insertDemo(@RequestBody TestUserReq req);
}

