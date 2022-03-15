package com.onelive.common.model.dto.lottery;

import lombok.Data;

import java.util.List;

@Data
public class LotteryPlayAllDTO {

    private List<LotteryPlayAllDTO> playChildren;

    private Integer id;
    /**
     * 用于展示的名称
     */
    private String showName;
    /**
     * 说明: 玩法名称
     */
    private String name;

    /**
     * 说明: 彩种分类id
     */
    private Integer categoryId;

    /**
     * 说明: 父级id
     */
    private Integer parentId;

    /**
     * 说明: 彩种ID
     */
    private Integer lotteryId;

    /**
     * 说明: 玩法规则Tag编号
     */
    private Integer playTagId;

    private Integer sort;

    private LotteryPlaySettingDTO setting; // 玩法配置信息

    private List<LotteryPlayAllOddsDTO> oddsList; // 具体赔率信息

}
