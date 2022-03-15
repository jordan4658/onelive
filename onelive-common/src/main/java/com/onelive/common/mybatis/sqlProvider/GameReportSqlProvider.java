package com.onelive.common.mybatis.sqlProvider;

import com.onelive.common.enums.GameReportColumnEnums;
import com.onelive.common.model.req.report.GameDetailReportReq;
import com.onelive.common.model.req.report.GameReportReq;
import com.onelive.common.model.req.report.GameSelectReq;
import com.onelive.common.utils.others.StringUtils;

/**
 * 游戏报表相关sql
 */
public class GameReportSqlProvider {

    /**
     * 统计报表数据
     * @param req
     * @return
     */
    public String queryGameReport(GameReportReq req){
        StringBuilder sql = new StringBuilder();
        String startDate = req.getStartDate();
        String endDate = req.getEndDate();

        sql.append("SELECT " +
                " 'Lottery' name, " +
                " 'Lottery' code, " +
                " IFNULL( count( DISTINCT user_id ), 0 ) user_count, " +
                " IFNULL( SUM( bet_count ), 0 ) bet_count, " +
                " IFNULL( SUM( bet_amount ), 0 ) bet_amount, " +
                " IFNULL( SUM( win_amount ), 0 ) win_amount  " +
                "FROM " +
                " lottery_order_bet_record  " +
                "WHERE " +
                " tb_status NOT IN ( 'BACK' )  ");

        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND create_time >= '").append(startDate).append("' ");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND create_time <= '").append(endDate).append("' ");
        }
        sql.append(" UNION SELECT " +
                " 'OBG' name, " +
                " 'OBG' code, " +
                " IFNULL( count( DISTINCT player_name ), 0 ) user_count, " +
                " IFNULL( count( id ), 0 ) bet_count, " +
                " IFNULL( SUM( bet_amount ), 0 ) bet_amount, " +
                " IFNULL( SUM( net_amount ), 0 ) win_amount  " +
                "FROM " +
                " game_record  " +
                "WHERE " +
                " bet_status NOT IN ( 2, 14, 15 )  ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND FROM_UNIXTIME( create_at / 1000 ) >= '").append(startDate).append("' ");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND FROM_UNIXTIME( create_at / 1000 ) <= '").append(endDate).append("' ");
        }

        return sql.toString();
    }


    /**
     * 查询指定平台游戏数据详情
     * @param req
     * @return
     */
    public String queryGameDetailReport(GameDetailReportReq req){
        StringBuilder sql = new StringBuilder();
        String startDate = req.getStartDate();
        String endDate = req.getEndDate();
        Integer gameId = req.getGameId();
        String column = req.getColumn();

        if("Lottery".equals(req.getCode())) {
            sql.append("SELECT game.* FROM( SELECT " +
                    " lottery.`name`, " +
                    " IFNULL( count( DISTINCT record.user_id ), 0 ) user_count, " +
                    " IFNULL( SUM( record.bet_count ), 0 ) bet_count, " +
                    " IFNULL( SUM( record.bet_amount ), 0 ) bet_amount, " +
                    " IFNULL( SUM( record.win_amount ), 0 ) win_amount  " +
                    "FROM " +
                    " lottery_order_bet_record record LEFT JOIN lottery lottery ON record.lottery_id=lottery.lottery_id " +
                    "WHERE " +
                    " record.tb_status NOT IN ( 'BACK' )");
            if(gameId!=null){
                sql.append(" AND record.lottery_id = '").append(gameId).append("' ");
            }
            if (StringUtils.isNotBlank(startDate)) {
                sql.append(" AND record.create_time >= '").append(startDate).append("' ");
            }
            if (StringUtils.isNotBlank(endDate)) {
                sql.append(" AND record.create_time <= '").append(endDate).append("' ");
            }
            sql.append(" GROUP BY record.lottery_id ) as game WHERE 1=1 ");

            if(StringUtils.isNotBlank(column)){
                if(GameReportColumnEnums.USER_COUNT.getColumn().equals(column)){
                    sql.append(" AND game.user_count > 0 ");
                }else if(GameReportColumnEnums.BET_AMOUNT.getColumn().equals(column)){
                    sql.append(" AND game.bet_amount > 0 ");
                }else if(GameReportColumnEnums.BET_COUNT.getColumn().equals(column)){
                    sql.append(" AND game.bet_count > 0 ");
                }else if(GameReportColumnEnums.WIN_AMOUNT.getColumn().equals(column)){
                    sql.append(" AND game.win_amount > 0 ");
                }else if(GameReportColumnEnums.PLATFORM_WIN_AMOUNT.getColumn().equals(column) || GameReportColumnEnums.RATIO.getColumn().equals(column)){
                    sql.append(" AND game.bet_count > game.win_amount ");
                }else if(GameReportColumnEnums.USER_WIN_AMOUNT.getColumn().equals(column)){
                    sql.append(" AND game.win_amount > game.bet_count ");
                }
            }

        }else if("OBG".equals(req.getCode())) {
            sql.append("SELECT game.* FROM(  SELECT " +
                    " game_type_name name," +
                    " IFNULL( count( DISTINCT player_name ), 0 ) user_count, " +
                    " IFNULL( count( id ), 0 ) bet_count, " +
                    " IFNULL( SUM( bet_amount ), 0 ) bet_amount, " +
                    " IFNULL( SUM( net_amount ), 0 ) win_amount  " +
                    "FROM " +
                    " game_record  " +
                    "WHERE " +
                    " bet_status NOT IN ( 2, 14, 15 )  ");
            if(gameId!=null){
                sql.append(" AND game_type_id = '").append(gameId).append("' ");
            }
            if (StringUtils.isNotBlank(startDate)) {
                sql.append(" AND FROM_UNIXTIME( create_at / 1000 ) >= '").append(startDate).append("' ");
            }
            if (StringUtils.isNotBlank(endDate)) {
                sql.append(" AND FROM_UNIXTIME( create_at / 1000 ) <= '").append(endDate).append("' ");
            }
            sql.append(" GROUP BY game_type_id ) as game WHERE 1=1 ");
            if(StringUtils.isNotBlank(column)){
                if(GameReportColumnEnums.USER_COUNT.getColumn().equals(column)){
                    sql.append(" AND game.user_count > 0 ");
                }else if(GameReportColumnEnums.BET_AMOUNT.getColumn().equals(column)){
                    sql.append(" AND game.bet_amount > 0 ");
                }else if(GameReportColumnEnums.BET_COUNT.getColumn().equals(column)){
                    sql.append(" AND game.bet_count > 0 ");
                }else if(GameReportColumnEnums.WIN_AMOUNT.getColumn().equals(column)){
                    sql.append(" AND game.win_amount > 0 ");
                }else if(GameReportColumnEnums.PLATFORM_WIN_AMOUNT.getColumn().equals(column) || GameReportColumnEnums.RATIO.getColumn().equals(column)){
                    sql.append(" AND game.bet_count > game.win_amount ");
                }else if(GameReportColumnEnums.USER_WIN_AMOUNT.getColumn().equals(column)){
                    sql.append(" AND game.win_amount > game.bet_count ");
                }
            }
        }

        return sql.toString();
    }


    /**
     * 查询下拉选项
     * @param req
     * @return
     */
    public String queryGameSelect(GameSelectReq req){
        StringBuilder sql = new StringBuilder();
        String code = req.getCode();
        if("Lottery".equals(code)) {
            sql.append("SELECT lo.`name`, lo.lottery_id game_id  FROM lottery lo  WHERE lo.is_delete = 0");
        }else if("OBG".equals(code)) {
            sql.append("SELECT `name`, game_id FROM game_third WHERE is_delete = 0");
        }
        return sql.toString();
    }

}
