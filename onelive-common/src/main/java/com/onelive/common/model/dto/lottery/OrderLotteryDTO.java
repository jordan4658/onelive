package com.onelive.common.model.dto.lottery;

import lombok.Data;

/**
 * @ClassName OrderLotteryDTO
 * @Desc 订单彩票DTO
 * @Date 2021/3/16 10:26
 */
@Data
public class OrderLotteryDTO {
    /**
     * 房间id
     */
    private String studioNum;
    /**
     * 用户id
     */
    private Long userId;

}    
    