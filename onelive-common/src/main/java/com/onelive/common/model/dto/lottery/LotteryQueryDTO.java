package com.onelive.common.model.dto.lottery;

import lombok.Data;

/**
 * 彩票查询参数传输类
 */
@Data
public class LotteryQueryDTO {
    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 语言
     */
    private String lang;
}
