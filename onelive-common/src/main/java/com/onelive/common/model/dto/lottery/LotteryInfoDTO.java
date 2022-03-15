package com.onelive.common.model.dto.lottery;

import lombok.Data;

import java.util.List;

@Data
public class LotteryInfoDTO {

    private String cateName;
    private String cateID;
    private List<LotteryAllDTO> lotterys;

    private String intro;
    /**
     * 说明: 是否开售
     */
    private Integer isWork;
    /**
     * 排序字段
     */
    private Integer sort;
}
