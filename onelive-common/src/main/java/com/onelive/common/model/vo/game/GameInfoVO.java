package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class GameInfoVO implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * logo图片地址
     */
    @ApiModelProperty("图片地址")
    private String imgUrl;

}
