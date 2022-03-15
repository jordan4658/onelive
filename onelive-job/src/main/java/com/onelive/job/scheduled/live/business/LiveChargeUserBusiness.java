package com.onelive.job.scheduled.live.business;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.onelive.common.client.OneliveApiFeignClient;
import com.xxl.job.core.log.XxlJobLogger;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LiveChargeUserBusiness {

	@Resource
	private OneliveApiFeignClient oneliveApiFeignClient;

	public void liveChargeUser() {
		Set<Long> liveChargeUser = oneliveApiFeignClient.liveChargeUser();
		XxlJobLogger.log("======用户:{} 扣钱失败，被踢出房间", liveChargeUser);
	}

}
