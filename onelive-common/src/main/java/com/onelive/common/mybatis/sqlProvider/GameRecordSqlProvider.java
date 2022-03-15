package com.onelive.common.mybatis.sqlProvider;

import com.onelive.common.model.req.game.GameRecordDetailReq;
import com.onelive.common.model.req.mem.MemUserGameRecordListReq;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.DateUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameRecordSqlProvider {

    /**
     * 查询游戏记录数据统计
     * @param req
     * @return
     */
    public String sumUserBetRecordData(MemUserGameRecordListReq req){
        Integer queryDate = req.getQueryDate();
        String now = DateUtils.getTime();
        StringBuilder sb = new StringBuilder();
        String accno = LoginInfoUtil.getAccno();

        sb.append("SELECT 'OBG' platform_code, record.game_type_id game_id, record.data_source game_type, record.game_type_name game_name, count(record.id) bet_count, sum(record.bet_amount) bet_amount, sum(IF(record.net_amount>0,record.net_amount,0)) win_amount  " +
                "FROM game_record record WHERE record.player_name ='").append(accno).append("'");
        if(queryDate==1){
            sb.append("AND FROM_UNIXTIME(record.create_at /1000, '%Y%m%d') = date_format('").append(now).append("','%Y%m%d')");// 查询今天数据
        }else if(queryDate==2){
            sb.append("AND  FROM_UNIXTIME(record.create_at /1000, '%Y%m%d') = date_format(date_sub('").append(now).append("', INTERVAL 1 DAY),'%Y%m%d')"); //查询昨天数据
        }
        sb.append(" GROUP BY record.game_type_id ");
        return sb.toString();
    }


    /**
     * 查询指定游戏记录详情统计
     * @param req
     * @return
     */
    public String sumUserBetRecordDetailData(GameRecordDetailReq req){
        Integer queryDate = req.getQueryDate();
        String now = DateUtils.getTime();
        StringBuilder sb = new StringBuilder();
        String accno = LoginInfoUtil.getAccno();
        sb.append("SELECT " +
                " record.game_type_name game_name, " +
                " record.bet_amount bet_amount, " +
                "IF( record.net_amount > 0, record.net_amount, 0 ) win_amount  " +
                "FROM " +
                " game_record record  " +
                "WHERE " +
                " record.game_type_id = '")
                .append(req.getGameId())
                .append("' AND record.data_source='")
                .append(req.getGameType())
                .append("' AND record.player_name = '")
                .append(accno).append("'");
        if(queryDate==1){
            sb.append("AND FROM_UNIXTIME(record.create_at /1000, '%Y%m%d') = date_format('").append(now).append("','%Y%m%d')");// 查询今天数据
        }else if(queryDate==2){
            sb.append("AND  FROM_UNIXTIME(record.create_at /1000, '%Y%m%d') = date_format(date_sub('").append(now).append("', INTERVAL 1 DAY),'%Y%m%d')"); //查询昨天数据
        }
        return sb.toString();
    }
}
