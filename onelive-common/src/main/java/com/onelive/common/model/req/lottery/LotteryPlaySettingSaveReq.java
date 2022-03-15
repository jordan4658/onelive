package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 彩种玩法
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@ApiModel(value = "保存玩法设置请求类")
public class LotteryPlaySettingSaveReq {

    @ApiModelProperty(value = "玩法设置id")
    private Integer id;

    @ApiModelProperty(value = "彩种分类Id[必填]",required = true)
    private Integer cateId;

    @ApiModelProperty(value = "玩法ID[必填]",required = true)
    private Integer playId;

    @ApiModelProperty(value = "玩法规则Tag编号[必填]",required = true)
    private Integer playTagId;

    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    private List<LotteryPlaySettingLangReq> langList;
}
