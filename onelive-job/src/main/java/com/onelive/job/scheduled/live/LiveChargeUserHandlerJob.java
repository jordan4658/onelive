package com.onelive.job.scheduled.live;

import org.springframework.beans.factory.annotation.Autowired;

import com.onelive.job.scheduled.live.business.LiveChargeUserBusiness;

/**
 * @author mao
 * 		对所有所有收费直播间用户扣费
 * 		改由用户端发起请求，后台被动扣款
 *
 */
//@Component
public class LiveChargeUserHandlerJob {

    @Autowired
    private LiveChargeUserBusiness liveChargeUserBusiness;

//    /**
//     * 	获取所有收费房间的用户，根据不同房间的收费额度对用户进行扣钱
//     *
//     * @return
//     */
//    //BEAN模式（方法形式）
//    @XxlJob("liveChargeUserHandler")
//    public ReturnT<String> liveChargeUser(String param) {
//    	XxlJobLogger.log("===LiveChargeUserHandlerJob 对所有所有收费直播间用户扣费开始执行===");
//    	liveChargeUserBusiness.liveChargeUser();
//        return ReturnT.SUCCESS;
//    }

}
