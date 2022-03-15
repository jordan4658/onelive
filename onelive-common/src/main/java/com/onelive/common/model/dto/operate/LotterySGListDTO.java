package com.onelive.common.model.dto.operate;

import lombok.Data;

/**
 * 查询有赛果的彩票
 */
@Data
public class LotterySGListDTO {

    /**
     * 彩票ID
     */
    private Integer lotteryId;

    /**
     * 对应的数据表
     */
    private String tableName;

}
