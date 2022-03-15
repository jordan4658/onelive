package com.onelive.common.mybatis.sqlProvider;

import com.onelive.common.model.req.operate.GameRiskReq;
import org.apache.commons.lang3.StringUtils;

/**
 * 游戏风控数据查询
 */
public class GameRiskSqlProvider {
    /**
     * 统计游戏风控数据
     *
     * @param req
     * @return
     */
    public String countGameRiskListData(GameRiskReq req) {
        StringBuilder sql = new StringBuilder();
        String issue = req.getIssue();
        Long lotteryId = req.getLotteryId();
        String startTime = req.getStartTime();
        String endTime = req.getEndTime();
        sql.append("SELECT " +
                " lo.`name`, " +
                " t.lottery_id, " +
                " t.issue, " +
                " COUNT( DISTINCT t.user_id ) bet_user_count, " +
                " SUM( IF ( t.tb_status = 'WIN', 1, 0 )) win_user_count, " +
                " sum( t.bet_count ) bet_count, " +
                " sum( t.win_count ) win_count, " +
                " sum( t.bet_amount ) bet_amount, " +
                " sum( t.win_amount ) win_amount  " +
                "FROM " +
                " ( " +
                " SELECT " +
                "  record.user_id, " +
                "  record.lottery_id, " +
                "  record.issue, " +
                "  record.tb_status, " +
                "  record.create_time, " +
                "  sum( record.bet_count ) bet_count, " +
                "  sum( record.win_count ) win_count, " +
                "  sum( record.bet_amount ) bet_amount, " +
                "  sum( record.win_amount ) win_amount  " +
                " FROM " +
                "  lottery_order_bet_record record  " +
                " WHERE " +
                "  record.tb_status != 'BACK'  ");

        if (lotteryId != null) {
            sql.append("  AND record.lottery_id = '").append(lotteryId).append("'");

        }
        if (StringUtils.isNotBlank(issue)) {
            sql.append("  AND record.issue = '").append(issue).append("'");
        }
        if (StringUtils.isNotBlank(startTime)) {
            sql.append("  AND record.create_time > '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)) {
            sql.append("  AND record.create_time < '").append(endTime).append("'");
        }
        sql.append(
                " GROUP BY " +
                        "  record.lottery_id, " +
                        "  record.issue, " +
                        "  record.user_id  " +
                        " ORDER BY NULL  " +
                        " ) AS t " +
                        " LEFT JOIN lottery lo ON t.lottery_id = lo.lottery_id  " +
                        "GROUP BY " +
                        " t.lottery_id, " +
                        " t.issue  " +
                        "ORDER BY " +
                        " t.create_time DESC");
        return sql.toString();
    }
}
