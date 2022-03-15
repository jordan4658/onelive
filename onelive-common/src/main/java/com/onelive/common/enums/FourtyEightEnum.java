package com.onelive.common.enums;

/**
 * WEB界面六合彩心水推荐列表类型枚舉
 *
 */
public enum FourtyEightEnum {

    ONE("01", "1020"),

    TWO("02", "1040"),

    THREE("03", "1100"),

    FOUR("04", "1120"),

    FIVE("05", "1140"),

    SIX("06", "1200"),

    SEVEN("07", "1220"),

    EIGHT("08", "1240"),

    NINE("09", "1300"),

    TEN("10", "1320"),

    ELEVEN("11", "1340"),

    TWELVE("12", "1400"),

    THIRTEEN("13", "1420"),

    FOURTEEN("14", "1440"),

    FIFTEEN("15", "1500"),

    SIXTEEN("16", "1520"),

    seventeen("17", "1540"),

    EIGHTEEN("18", "1600"),

    NINETEEN("19", "1620"),

    TWENTY("20", "1640"),

    TWENTYONE("21", "1700"),

    TWENTYTWO("22", "1720"),

    TWENTYTHREE("23", "1740"),

    TWENTYFOUR("24", "1800"),

    TWENTYFIVE("25", "1820"),

    TWENTYSIX("26", "1840"),

    TWENTYSEVEN("27", "1900"),

    TWENTYEIGHT("28", "1920"),

    TWENTYNINE("29", "1940"),

    THIRTY("30", "2000"),

    THIRTYONE("31", "2020"),

    THIRTYTWO("32", "2040"),

    THIRTYTHREE("33", "2100"),

    THIRTYFOUR("34", "2120"),

    THIRTYFIVE("35", "2140"),

    THIRTYSIX("36", "2200"),

    THIRTYSEVEN("37", "2220"),

    THIRTYEIGHT("38", "2240"),

    THIRTYNINE("39", "2300"),

    FORTY("40", "2320"),

    FORTYONE("41", "2340"),

    FORTYTWO("42", "0000"),

    FORTYTHREE("43", "0020"),

    FORTYFOUR("44", "0040"),

    FORTYFIVE("45", "0100"),

    FORTYSIX("46", "0120"),

    FORTYSEVEN("47", "0140"),

    FORTYEIGHT("48", "0200");

    private String issueId;
    private String issueTime;

    private FourtyEightEnum(String issueId, String issueTime) {
        this.issueId = issueId;
        this.issueTime = issueTime;
    }

    public static String getIssueId(String issueTime) {
        for (FourtyEightEnum fourtyEightEnum : FourtyEightEnum.values()) {
            if(fourtyEightEnum.getIssueTime().equals(issueTime)) {
                return fourtyEightEnum.getIssueId();
            }
        }
        return null;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

}
