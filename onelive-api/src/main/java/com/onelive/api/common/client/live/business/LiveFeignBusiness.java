package com.onelive.api.common.client.live.business;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.live.LiveStudioLogService;
import com.onelive.api.service.mem.MemUserAnchorService;
import com.onelive.api.service.mem.MemUserExpenseRecordService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.common.client.WebSocketFeignClient;

@Component
public class LiveFeignBusiness {

	@Resource
	private WebSocketFeignClient wbeSocketFeignClient;

	@Resource
	private MemUserService memUserService;

	@Resource
	private MemWalletService memWalletService;

	@Resource
	private MemUserAnchorService memUserAnchorService;

	@Resource
	private LiveStudioListService liveStudioListService;

	@Resource
	private LiveStudioLogService liveStudioLogService;

	@Resource
	private MemUserExpenseRecordService memUserExpenseRecordService;

	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	

	/**
	 * 
	 * @return
	 */
	public Set<Long> liveChargeUser() {
		Set<Long> failUserIds = new HashSet<Long>();
		Set<String> studioNumList = liveStudioListService.getChargeRoom();
		for (String studioNum : studioNumList) {
			Future<Set<Long>> result = taskExecutor.submit(new Callable<Set<Long>>() {
					@Override
					public Set<Long> call() throws Exception {
						return chargeForRoomUser(studioNum);
					}
				});
			try {
				failUserIds.addAll(result.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return failUserIds;
	}
	
	private Set<Long> chargeForRoomUser(String studioNum) {
		Set<Long> roomUsers = wbeSocketFeignClient.getRoomUsers(studioNum);
		// TODO 扣钱，扣钱余额不足的返回id
		
		return roomUsers;
	}

}
