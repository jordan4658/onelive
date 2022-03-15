package com.onelive.gateway.factory;

import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.http.HttpStatus;

/**
 * @author Lorenzo
 * @version 1.0
 * @Date 2020/10/23 14:39
 */
public class JSONConfig extends RequestRateLimiterGatewayFilterFactory.Config{
    KeyResolver keyResolver;

    RateLimiter rateLimiter;

    HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

    Boolean denyEmptyKey;

    String emptyKeyStatus;

    private String routeId;

    public KeyResolver getKeyResolver() {
        return keyResolver;
    }

    public RequestRateLimiterGatewayFilterFactory.Config setKeyResolver(KeyResolver keyResolver) {
        this.keyResolver = keyResolver;
        return this;
    }

    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public RequestRateLimiterGatewayFilterFactory.Config setRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
        return this;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public RequestRateLimiterGatewayFilterFactory.Config setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Boolean getDenyEmptyKey() {
        return denyEmptyKey;
    }

    public RequestRateLimiterGatewayFilterFactory.Config setDenyEmptyKey(Boolean denyEmptyKey) {
        this.denyEmptyKey = denyEmptyKey;
        return this;
    }

    public String getEmptyKeyStatus() {
        return emptyKeyStatus;
    }

    public RequestRateLimiterGatewayFilterFactory.Config setEmptyKeyStatus(String emptyKeyStatus) {
        this.emptyKeyStatus = emptyKeyStatus;
        return this;
    }

    @Override
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @Override
    public String getRouteId() {
        return this.routeId;
    }
}
