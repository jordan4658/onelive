package com.onelive.common.mybatis.mapper.slave.lottery;

import com.onelive.common.model.vo.lottery.LotteryPlayOddsExVo;
import com.onelive.common.mybatis.entity.LotteryPlayOdds;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 赔率配置表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface SlaveLotteryPlayOddsMapper extends BaseMapper<LotteryPlayOdds> {

    @Select(" SELECT" +
            "            odds.id," +
            "            odds.setting_id," +
            "            odds.play_tag_id," +
            "            odds.ex_setting_id," +
            "            odds.name," +
            "            lang.odds_name show_name," +
            "            odds.total_count," +
            "            odds.win_count," +
            "            odds.create_time," +
            "            odds.update_time," +
            "            odds.is_delete," +
            "            odds.easy_import_flag" +
            "        FROM" +
            "            lottery_play_odds odds" +
            "                LEFT JOIN lottery_play_odds_lang lang ON odds.id = lang.odds_id" +
            "        WHERE" +
            "            odds.is_delete = 0" +
            "          AND lang.lang = #{lang} AND lang.odds_name != ''")
    List<LotteryPlayOddsExVo> listAllPlayOddsWithLang(@Param("lang") String lang);
}
