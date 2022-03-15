package com.onelive.common.model.dto.game;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("第三方游戏-真人游戏修改限红参数")
public class GameZRModifyBetLimitDTO {
    /**
     * 用户账号,关联mem_user.accno
     */
    private String accno;
    /**
     * API基础接口
     */
    private String infoHost;
    /**
     * md5密钥
     */
    private String md5Key;

    /**
     * AesKey加密参数
     */
    private String aesKey;
    /**
     * 真人游戏限红ID
     */
    private Integer oddType;
    /**
     * 商户号
     */
    private String merchantCode;

}
