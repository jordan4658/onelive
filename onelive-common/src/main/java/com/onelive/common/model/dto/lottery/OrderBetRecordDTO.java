package com.onelive.common.model.dto.lottery;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderBetRecordDTO {
    private Integer id;
    private Integer userId;
    private Integer orderId;
    private Integer cateId;
    private Integer lotteryId;
    private Integer playId;
    private Integer settingId;
    private String playName;
    private String issue;
    private String orderSn;
    private String betNumber;
    private Integer betCount;
    private BigDecimal betAmount;
    private BigDecimal winAmount;
    private BigDecimal backAmount;
    private Integer godOrderId;
    private String tbStatus;
    private Boolean isDelete;
    private String winCount;
    private Integer isPush;
    private Date createTime;
    private Date updateTime;
    private String source;
    private Long familyid;
    private Long roomId;
}
