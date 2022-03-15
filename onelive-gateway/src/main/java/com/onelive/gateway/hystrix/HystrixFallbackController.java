package com.onelive.gateway.hystrix;


import com.onelive.gateway.model.JsonResponse;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

/**
 * 请求超时降级
 */
@RestController
@Slf4j
public class HystrixFallbackController {

    @RequestMapping(value = "/fallback", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponse fallback(ServerWebExchange exchange) {
        Exception exception = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
        log.error("接口调用失败，URL={}", delegate.getRequest().getURI(), exception);
        if (exception instanceof HystrixTimeoutException) {
            log.info("msg: {}", "接口调用超时");
        } else if (exception instanceof NotFoundException) {
            log.info("msg: {}", "找不到服务:" + exception.getMessage());
        } else if (exception != null && exception.getMessage() != null) {
            log.info("msg: {}", "接口调用失败: " + exception.getMessage());
        } else {
            log.info("msg: {}", "接口调用失败");
        }
        return JsonResponse.fail();
    }


}
