package com.onelive.common.model.vo.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户游戏记录实体类
 */
@Data
@ApiModel
public class GameRecordVO implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("平台代码")
    private String platformCode;

    @ApiModelProperty("游戏ID")
    private String gameId;

    @ApiModelProperty("游戏分类")
    private String gameType;

    @ApiModelProperty("游戏名称")
    private String gameName;

    @ApiModelProperty("图片地址")
    private String iconUrl;

    @ApiModelProperty("下单注量")
    private Integer betCount;

    @ApiModelProperty("下单金额")
    private BigDecimal betAmount;

    @ApiModelProperty("中奖金额")
    private BigDecimal winAmount;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }
}
