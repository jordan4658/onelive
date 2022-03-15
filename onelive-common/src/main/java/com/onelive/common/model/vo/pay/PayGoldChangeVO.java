package com.onelive.common.model.vo.pay;

import com.onelive.common.model.dto.pay.TotalUserIncomeAndExpensesDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class PayGoldChangeVO {

    @ApiModelProperty("统计信息集合")
    private TotalUserIncomeAndExpensesDTO totalInfo;

    @ApiModelProperty("交易记录集合")
    private List<PayGoldChangeDetailVO> detailList;


}
