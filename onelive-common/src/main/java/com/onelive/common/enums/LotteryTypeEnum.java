package com.onelive.common.enums;

public enum LotteryTypeEnum {

    LOTTERY("LOTTERY", "彩票类"),
    QIPAI("QIPAI", "棋牌类"),
    ZRSX("ZRSX", "真人视讯类"),
    ZUCAI("ZUCAI", "足彩类");

    private String type;
    private String desc;

    LotteryTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
