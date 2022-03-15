package com.onelive.common.model.dto.lottery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryPlayAllOddsDTO {

    private Integer id;

    /** 说明: 玩法配置id */
    private Integer settingId;

    /** 说明: 名称 */
    private String name;

    /** 说明: 显示名称 */
    private String showName;

    private String odds;

    public static LotteryPlayAllOddsDTO newInstance(Integer id, Integer settingId, String name,String showName, String odds) {
        return new LotteryPlayAllOddsDTO(id, settingId, name, showName,odds);
    }



}
