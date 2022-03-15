package com.onelive.common.model.dto.lottery;

import lombok.Data;

import java.util.List;
@Data
public class OrderDTO {
    private Long familymemid;
    private Long roomId;
    private Long familyid;
    private int reOrderNum;
    private List<OrderBetRecordDTO> orderBetList;
}
