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
@ApiModel(value = "编辑彩种玩法请求类")
public class LotteryPlaySaveReq {

    @ApiModelProperty(value = "玩法id, 更新时传入")
    private Integer id;

    @ApiModelProperty(value = "彩种分类Id[必填]",required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "彩种编号[必填]",required = true)
    private Integer lotteryId;

    @ApiModelProperty(value = "父级id[必填]",required = true)
    private Integer parentId;

    @ApiModelProperty(value = "层级[必填]",required = true)
    private Integer level;

    @ApiModelProperty(value = "玩法规则Tag编号[必填]",required = true)
    private Integer playTagId;

    @ApiModelProperty(value = "排序[必填]",required = true)
    private Integer sort;

    @ApiModelProperty(value = "取值区间[必填]",required = true)
    private String section;

    @ApiModelProperty(value = "玩法节点[必填]",required = true)
    private String tree;

    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    List<LotteryPlayLangReq> langList;

}
