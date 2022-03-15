package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户消费类型
 */
@Getter
@AllArgsConstructor
public enum UserExpenseTypeEnum {

    GIFTS(1,"送礼物"),
    GAME(2,"玩游戏");

    /**
     * 经验值类型
     */
    private Integer type;

    /**
     * 类型描述
     */
    private String desc;

    public static UserExpenseTypeEnum getByType(Integer type){
        UserExpenseTypeEnum[] values = UserExpenseTypeEnum.values();
        for (UserExpenseTypeEnum em:values){
            if(em.getType().equals(type)){
                return em;
            }
        }
        return null;
    }

}
