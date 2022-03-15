package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 彩种玩法
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@ApiModel(value = "保存玩法设置多语言请求类")
public class LotteryPlaySettingLangReq {

    @ApiModelProperty(value = "主键id, 更新时传入")
    private Long id;
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;
    @ApiModelProperty(value = "投注示例[必填]",required = true)
    private String example;
    @ApiModelProperty(value = "示例号码[必填]",required = true)
    private String exampleNum;
    @ApiModelProperty(value = "玩法说明[必填]",required = true)
    private String playRemark;
    @ApiModelProperty(value = "玩法简要说明[必填]",required = true)
    private String playRemarkSx;
}
