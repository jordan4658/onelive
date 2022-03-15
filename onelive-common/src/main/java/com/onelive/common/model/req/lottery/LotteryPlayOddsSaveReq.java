package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 彩种赔率信息
 * </p>
 */
@Data
@ApiModel(value = "保存玩法赔率信息请求类")
public class LotteryPlayOddsSaveReq {
    @ApiModelProperty(value = "主键ID,更新时传入")
    private Integer id;
    @ApiModelProperty(value = "玩法设置id[必填]",required = true)
    private Integer settingId;
    @ApiModelProperty(value = "总注数[必填]",required = true)
    private String totalCount;
    @ApiModelProperty(value = "中奖注数[必填]",required = true)
    private String winCount;
    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    private List<LotteryPlayOddsLangReq> langList;
}
