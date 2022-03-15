package com.onelive.common.model.dto.lottery;



import com.onelive.common.mybatis.entity.LotteryPlayOdds;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LotteryPlaySettingDTO {
    private Integer id;
    private Integer cateId; // 分类id
    private Integer playId; // 玩法id
    private Integer totalCount; // 总注数
    private Integer winCount; // 中奖注数
    private Double singleMoney; // 单注金额
    private String example; // 投注示例
    private String exampleNum; // 示例号码
    private String playRemark; // 玩法说明
    private String playRemarkSx; // 玩法简要说明
    private String reward; // 奖级
    private Integer winCountBak; // 中奖注数（后端）
    private Integer totalCountBak; // 总注数（后端）
    private String rewardLevel; // 中奖等级
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Boolean isDelete; // 是否删除
    private String cateName; // 分类名称
    private String type; // 玩法类型1
    private String plan; // 玩法分类2
    private String planName; // 玩法分类3
    private String matchtype;
    private Integer lotteryId;//彩种编号
    private Integer playTagId;//玩法规则TagId
    private String lotteryName;
}
