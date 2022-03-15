package com.onelive.gateway.config;

import com.onelive.gateway.factory.JSONRequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

/**
 * 路由限流配置
 * 
 * @author abu
 */
@Configuration
public class RateLimiterConfig {

	private final static  String AUTHORIZATION =  "Authorization";

	@Bean
	public AbstractGatewayFilterFactory jsonRequestRateLimiterGatewayFilterFactory(
			RateLimiter rateLimiter, KeyResolver resolver) {
		return new JSONRequestRateLimiterGatewayFilterFactory(rateLimiter, resolver);
	}

	/**
	 * ip地址限流
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public KeyResolver ipKeyResolver() {
		// nginx代理后真实地址
		return exchange -> Mono.just(getIpAddress(exchange.getRequest()));
	}

	/**
	 * 根据请求参数限流
	 * 
	 * @return
	 */
	@Bean
    KeyResolver userKeyResolver() {
		//return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("acctoken"));

		return exchange -> {
			String userKey = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
			if (null == userKey || "".equals(userKey.trim())) {
				userKey = getIpAddress(exchange.getRequest());
			}
			return Mono.just(userKey);
		};
	}



	/**
	 * 接口限流
	 * 
	 * @return
	 */
	@Bean
    KeyResolver apiKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}

	/**
	 * 客户端地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		String ip = headers.getFirst("x-forwarded-for");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
		}
		return ip;
	}
}
