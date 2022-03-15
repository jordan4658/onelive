package com.onelive.gateway.factory;

import com.alibaba.fastjson.JSON;
import com.onelive.gateway.constants.ServerConstants;
import com.onelive.gateway.model.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.HttpStatusHolder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;


/**
 * @author Lorenzo
 * @version 1.0
 * @Description 重写网关限流响应逻辑
 * @Date 2020/10/23 14:06
 */
@Slf4j
public class JSONRequestRateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<JSONConfig> {

    private static final String EMPTY_KEY = "____EMPTY_KEY__";

    private final RateLimiter defaultRateLimiter;

    private final KeyResolver defaultKeyResolver;

    /**
     * Switch to deny requests if the Key Resolver returns an empty key, defaults to true.
     */
    private boolean denyEmptyKey = true;

    /** HttpStatus to return when denyEmptyKey is true, defaults to FORBIDDEN. */
    private String emptyKeyStatusCode = HttpStatus.FORBIDDEN.name();

    public KeyResolver getDefaultKeyResolver() {
        return defaultKeyResolver;
    }

    public RateLimiter getDefaultRateLimiter() {
        return defaultRateLimiter;
    }

    public boolean isDenyEmptyKey() {
        return denyEmptyKey;
    }

    public void setDenyEmptyKey(boolean denyEmptyKey) {
        this.denyEmptyKey = denyEmptyKey;
    }

    public String getEmptyKeyStatusCode() {
        return emptyKeyStatusCode;
    }

    public void setEmptyKeyStatusCode(String emptyKeyStatusCode) {
        this.emptyKeyStatusCode = emptyKeyStatusCode;
    }

    public JSONRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter, KeyResolver defaultKeyResolver) {
        super(JSONConfig.class);
        this.defaultRateLimiter = defaultRateLimiter;
        this.defaultKeyResolver = defaultKeyResolver;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GatewayFilter apply(JSONConfig config) {
        KeyResolver resolver = getOrDefault(config.keyResolver, defaultKeyResolver);
        RateLimiter<Object> limiter = getOrDefault(config.rateLimiter,
                defaultRateLimiter);
        boolean denyEmpty = getOrDefault(config.denyEmptyKey, this.denyEmptyKey);
        HttpStatusHolder emptyKeyStatus = HttpStatusHolder
                .parse(getOrDefault(config.emptyKeyStatus, this.emptyKeyStatusCode));


        return (exchange, chain) -> resolver.resolve(exchange).defaultIfEmpty(EMPTY_KEY)
                .flatMap(key -> {
                    if (ServerConstants.ONELIVE_API.equals(config.getRouteId())) {
                        String uri = exchange.getRequest().getURI().getPath();
                        if (appCacheUri().contains(uri)) {
                            CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.SECONDS);
                            exchange.getResponse().getHeaders().set("Cache-Control", cacheControl.getHeaderValue());
                        }
                    }


                    if (EMPTY_KEY.equals(key)) {
                        if (denyEmpty) {
                            setResponseStatus(exchange, emptyKeyStatus);
                            return exchange.getResponse().setComplete();
                        }
                        return chain.filter(exchange);
                    }
                    String routeId = config.getRouteId();
                    if (routeId == null) {
                        Route route = exchange
                                .getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                        routeId = route.getId();
                    }
                    return limiter.isAllowed(routeId, key).flatMap(response -> {

                        for (Map.Entry<String, String> header : response.getHeaders()
                                .entrySet()) {
                            exchange.getResponse().getHeaders().add(header.getKey(),
                                    header.getValue());
                        }
                        // 通过
                        if (response.isAllowed()) {
                            return chain.filter(exchange);
                        }

                        // 不通过，响应JSON数据
                        String jsonResult = JSON.toJSONString(new JsonResponse(config.getStatusCode().value(), "当前访问总人数太多啦，请稍后再试"));
                        ServerHttpResponse rs = exchange.getResponse();
                        DataBuffer buffer = rs.bufferFactory().wrap(jsonResult.getBytes());
                        rs.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                        rs.setStatusCode(config.getStatusCode());

                        return rs.writeWith(Mono.just(buffer));
                    });
                });
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return (configValue != null) ? configValue : defaultValue;
    }

    private final static List<String> appCacheUri() {
        List<String> uriList = new ArrayList<>();
        uriList.add("/memBank/app/V1/getBankList");
        uriList.add("/pay/app/v1/getPayWayList");
        uriList.add("/banner/app/v1/getHomeList");
        uriList.add("/kefu/app/v1/getLinkList");
        return uriList;
    }

}
