package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 第三方游戏记录
 * </p>
 *
 * @author ${author}
 * @since 2022-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 玩家账号,关联第三方数据的依据
     */
    private String playerName;

    /**
     * 游戏ID
     */
    private String gameTypeId;

    /**
     * 游戏名称-棋牌电游
     */
    private String gameTypeName;

    /**
     * 牌局ID-棋牌电游
     */
    private String roundId;

    /**
     * 牌局编号-棋牌电游
     */
    private String roundNo;

    /**
     * 游戏桌号-真人，棋牌，电游
     */
    private String tableCode;

    /**
     * 厅名称-真人
     */
    private String platformName;

    /**
     * 厅ID-真人
     */
    private String platformId;

    /**
     * 彩系ID-彩票
     */
    private Integer seriesId;

    /**
     * 彩系名称-彩票
     */
    private String seriesName;

    /**
     * 投注额
     */
    private BigDecimal betAmount;

    /**
     * 有效投注额
     */
    private BigDecimal validBetAmount;

    /**
     * 输赢金额
     */
    private BigDecimal netAmount;

    /**
     * 抽水额-棋牌，电游
     */
    private BigDecimal pumpingAmount;

    /**
     * 派奖额
     */
    private BigDecimal payAmount;

    /**
     * 下注前余额-真人
     */
    private BigDecimal beforeAmount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 开始时间=投注时间=注单开始时间
     */
    private Long createAt;

    /**
     * 结束时间=结算时间
     */
    private Long netAt;

    /**
     * 注单重新结算时间(真人使用，如果注单被撤销后重算，该字段作为真人注单的结算时间)
     */
    private Long recalcuAt;

    /**
     * 更新时间-真人
     */
    private Long updatedAt;

    /**
     * 比赛开始时间-电竞
     */
    private Long matchStartTime;

    /**
     * 注单撤销时间-彩票
     */
    private Long cancelAt;

    /**
     * 风控解锁时间-彩票
     */
    private Long riskUnlockAt;

    /**
     * 玩家昵称-真人
     */
    private String nickName;

    /**
     * 玩家编号-真人，彩票，电竞
     */
    private String playerId;

    /**
     * 设备类型-棋牌，电游，彩票
     */
    private Integer deviceType;

    /**
     * 玩家IP-真人
     */
    private String loginIp;

    /**
     * 商户代码
     */
    private String agentCode;

    /**
     * 商户ID
     */
    private String agentId;

    /**
     * 商户名称-真人
     */
    private String agentName;

    /**
     * 父商户ID-电竞
     */
    private String parentAgentId;

    /**
     * 父商户代码-电竞
     */
    private String parentAgentCode;

    /**
     * 注单状态
     */
    private Integer betStatus;

    /**
     * 注单类型-彩票，电竞0：普通注单，1：串注注单。
2：局内串注（电竞专用）
     */
    private Integer orderType;

    /**
     * 撤单标识-彩票
     */
    private Integer cancelStatus;

    /**
     * 撤单类型-彩票
     */
    private Integer cancelType;

    /**
     * 签名-真人
     */
    private String signature;

    /**
     * 桌台名称-真人
     */
    private String tableName;

    /**
     * 是否正式账户-彩票，电竞
     */
    private Integer isTester;

    /**
     * 投注项ID
     */
    private String playOptionsId;

    /**
     * 投注项名称
     */
    private String playOptionName;

    /**
     * 赔率-体育，彩票，电竞
     */
    private String oddsValue;

    /**
     * 玩法ID-彩票
     */
    private Long playId;

    /**
     * 玩法名称-体育，彩票
     */
    private String playName;

    /**
     * 玩法级ID-彩票
     */
    private String playLevelId;

    /**
     * 玩法级名称-彩票
     */
    private String playLevel;

    /**
     * 全场比分-体育
     */
    private String settleScore;

    /**
     * 数据来源(真人：zr 棋牌：qp 电游：dy 电竞：dj 彩票：cp 体育：ty)
     */
    private String dataSource;

    /**
     * 重算标识-真人(0:正常结算 1：本局作废2：取消置顶局的结算3：取消该注单的结算4：重算指定局5：重算指定注单)
     */
    private Integer betFlag;

    /**
     * 开牌结果-真人
     */
    private String judgeResult;

    /**
     * 靴号-真人
     */
    private String bootNo;

    /**
     * 设备ID-真人
     */
    private String deviceId;

    /**
     * 注单类别-真人(0、试玩 1、正式 2、内部测试 3、机器人。只有为1记录的才会返回给商户。)
     */
    private Integer recordType;

    /**
     * 游戏模式-真人(0=常规   1=旁注  2=好路 3=多台)
     */
    private Integer gameMode;

    /**
     * 荷官-真人
     */
    private String dealerName;

    /**
     * 扩展字段-真人(翻译后的中文表述)
     */
    private String judgeResultCn;

    /**
     * 扩展字段-真人(本局结果，庄闲的总点数)
     */
    private String judgeResult1;

    /**
     * 房间类型-棋牌,电游(1:初级,2:中级...)
     */
    private Integer roomType;

    /**
     * 游戏房间-棋牌,电游
     */
    private String gameRoom;

    /**
     * 游戏分类标记-棋牌,电游(棋牌: 0:棋牌，1:房卡，100：活动/电游: 0:捕鱼类1电子类)
     */
    private Integer gameFlag;

    /**
     * 该订单下的注单数-体育(单关为：1 串关：n)
     */
    private Integer betCount;

    /**
     * 订单结算结果-体育(主订单:无结果：0 走水：2
输：3 赢：4，赢一半：5，输一半：6/子订单: 投注金额
)
     */
    private Integer betResult;

    /**
     * 串关类型-体育
     */
    private Integer seriesType;

    /**
     * 串关值-体育
     */
    private String seriesValue;

    /**
     * 注单ID-体育
     */
    private String betNo;

    /**
     * 赛事ID-体育
     */
    private String matchId;

    /**
     * 盘口值-体育
     */
    private Double handiCap;

    /**
     * 联赛名称-体育
     */
    private String matchName;

    /**
     * 比赛对阵-体育
     */
    private String matchInfo;

    /**
     * 投注类型-体育(1早盘赛事 2滚球盘赛事 3冠军盘赛事)
     */
    private Integer matchType;

    /**
     * 赛种ID-体育
     */
    private String sportId;

    /**
     * 赛种名称-游戏名称-体育
     */
    private String sportName;

    /**
     * 联赛ID-体育
     */
    private String tournamentId;

    /**
     * 盘口值-体育
     */
    private String marketValue;

    /**
     * 盘口类型-体育
     */
    private String marketType;

    /**
     * 比赛开始时间-体育
     */
    private Long beginTime;

    /**
     * 分数基准-体育
     */
    private String scoreBenchmark;

    /**
     * 父级注单ID-体育
     */
    private String parentRecordId;

    /**
     * 账变状态: 0未处理, 1处理完成 2处理失败
     */
    private Integer accountChangeStatus;


}
