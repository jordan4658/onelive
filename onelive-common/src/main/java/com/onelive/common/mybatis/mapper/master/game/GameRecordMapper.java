package com.onelive.common.mybatis.mapper.master.game;

import com.onelive.common.mybatis.entity.GameRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <p>
 * 第三方游戏记录 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-12
 */
public interface GameRecordMapper extends BaseMapper<GameRecord> {

    @Insert("<script>" +
            "INSERT INTO game_record (id, player_name, game_type_id, game_type_name, round_id, round_no, table_code, platform_name, platform_id, series_id, series_name, " +
            " bet_amount, valid_bet_amount, net_amount, pumping_amount, pay_amount, before_amount, currency, create_at, net_at, recalcu_at, updated_at, match_start_time, " +
            " cancel_at, risk_unlock_at, nick_name, player_id, device_type, login_ip, agent_code, agent_id, agent_name, parent_agent_id, parent_agent_code, bet_status, " +
            " order_type, cancel_status, cancel_type, signature, table_name, is_tester, play_options_id, play_option_name, odds_value, play_id, play_name, play_level_id, " +
            " play_level, settle_score, data_source, bet_flag, judge_result, boot_no, device_id, record_type, game_mode, dealer_name, judge_result_cn, judge_result1, " +
            " room_type, game_room, game_flag, bet_count, bet_result, series_type, series_value, bet_no, match_id, handi_cap, match_name, match_info, match_type, sport_id, sport_name,  " +
            " tournament_id, market_value, market_type, begin_time, score_benchmark, parent_record_id  ) VALUES " +
            "<foreach collection='list' item='record'   separator=','> " +
            "(#{record.id}, #{record.playerName}, #{record.gameTypeId}, #{record.gameTypeName}, #{record.roundId}, #{record.roundNo}, #{record.tableCode}, #{record.platformName}, #{record.platformId}, #{record.seriesId}, #{record.seriesName}, " +
            " #{record.betAmount}, #{record.validBetAmount}, #{record.netAmount}, #{record.pumpingAmount}, #{record.payAmount}, #{record.beforeAmount}, #{record.currency}, #{record.createAt}, #{record.netAt}, #{record.recalcuAt}, #{record.updatedAt}, #{record.matchStartTime}, " +
            " #{record.cancelAt}, #{record.riskUnlockAt}, #{record.nickName}, #{record.playerId}, #{record.deviceType}, #{record.loginIp}, #{record.agentCode}, #{record.agentId}, #{record.agentName}, #{record.parentAgentId}, #{record.parentAgentCode}, #{record.betStatus}, " +
            " #{record.orderType}, #{record.cancelStatus}, #{record.cancelType}, #{record.signature}, #{record.tableName}, #{record.isTester}, #{record.playOptionsId}, #{record.playOptionName}, #{record.oddsValue}, #{record.playId}, #{record.playName}, #{record.playLevelId}, " +
            " #{record.playLevel}, #{record.settleScore}, #{record.dataSource}, #{record.betFlag}, #{record.judgeResult}, #{record.bootNo}, #{record.deviceId}, #{record.recordType}, #{record.gameMode}, #{record.dealerName}, #{record.judgeResultCn}, #{record.judgeResult1}, " +
            " #{record.roomType}, #{record.gameRoom}, #{record.gameFlag}, #{record.betCount}, #{record.betResult}, #{record.seriesType}, #{record.seriesValue}, #{record.betNo}, #{record.matchId}, #{record.handiCap}, #{record.matchName}, #{record.matchInfo}, #{record.matchType}, #{record.sportId}, #{record.sportName}, " +
            " #{record.tournamentId}, #{record.marketValue}, #{record.marketType}, #{record.beginTime}, #{record.scoreBenchmark}, #{record.parentRecordId} )" +
            "</foreach> " +
            "</script>")
    boolean insertBatch(List<GameRecord> list);
}
