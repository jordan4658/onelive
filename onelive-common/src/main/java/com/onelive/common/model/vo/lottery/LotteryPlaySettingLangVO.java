package com.onelive.common.model.vo.lottery;

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
@ApiModel(value = "玩法设置多语言实体类")
public class LotteryPlaySettingLangVO {

    @ApiModelProperty(value = "主键id, 更新时传入")
    private Long id;
    @ApiModelProperty(value = "语言")
    private String lang;
    @ApiModelProperty(value = "投注示例")
    private String example;
    @ApiModelProperty(value = "示例号码")
    private String exampleNum;
    @ApiModelProperty(value = "玩法说明")
    private String playRemark;
    @ApiModelProperty(value = "玩法简要说明")
    private String playRemarkSx;
}
