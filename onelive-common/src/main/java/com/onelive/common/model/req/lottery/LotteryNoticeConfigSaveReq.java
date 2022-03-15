package com.onelive.common.model.req.lottery;

import com.onelive.common.model.dto.lottery.LotteryNoticeConfigDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "彩种中奖公告配置保存请求参数类")
public class LotteryNoticeConfigSaveReq extends LotteryNoticeConfigDTO {


}
