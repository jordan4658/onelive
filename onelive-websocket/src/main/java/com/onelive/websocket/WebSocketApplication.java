package com.onelive.websocket;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.onelive.websocket.service.NettyServer;
import com.onelive.websocket.util.SpringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableCreateCacheAnnotation
public class WebSocketApplication {

	private static Integer nettyPort;

	@Value("${netty.server.port}")
	public void setNettyPort(Integer value) {
		nettyPort = value;
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = SpringApplication.run(WebSocketApplication.class, args);
		SpringUtils.setApplicationContexts(applicationContext);
		new NettyServer(nettyPort).start();
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}
}