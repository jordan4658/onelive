package com.onelive.common.model.dto.pay;

import lombok.Data;

@Data
public class BalanceChangeDTO {

    /**
     * 是否账变成功
     */
    private Boolean flag;

    /**
     * 账变记录的单号
     */
    private String changeOrderNo;
}
