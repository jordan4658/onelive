package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "彩种玩法赔率设置返回类")
public class LotteryPlayOddsVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "玩法设置id")
    private Integer settingId;
    @ApiModelProperty(value = "总注数")
    private String totalCount;
    @ApiModelProperty(value = "中奖注数")
    private String winCount;
    @ApiModelProperty(value = "多语言列表")
    private List<LotteryPlayOddsLangVO> langList;

}
