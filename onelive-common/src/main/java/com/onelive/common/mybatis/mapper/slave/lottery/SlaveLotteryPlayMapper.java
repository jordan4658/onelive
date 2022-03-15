package com.onelive.common.mybatis.mapper.slave.lottery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.vo.lottery.LotteryPlayExVo;
import com.onelive.common.mybatis.entity.LotteryPlay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩种玩法 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface SlaveLotteryPlayMapper extends BaseMapper<LotteryPlay> {

    @Select("<script>" +
            "SELECT" +
            "            play.id," +
            "            lang.play_name show_name," +
            "            play.name," +
            "            play.category_id," +
            "            play.parent_id," +
            "            play.sort," +
            "            play.`level`," +
            "            play.section," +
            "            play.tree," +
            "            play.create_time," +
            "            play.update_time," +
            "            play.is_delete," +
            "            play.lottery_id," +
            "            play.play_tag_id" +
            "        FROM" +
            "            lottery_play play" +
            "                LEFT JOIN lottery_play_lang lang ON play.id = lang.play_id" +
            "        WHERE" +
            "            play.is_delete = 0" +
            "          AND lang.lang = #{lang} AND lang.play_name != '' " +
            "<if test='lotteryId != null'>AND play.lottery_id = #{lotteryId}</if>" +
            "</script>")
    List<LotteryPlayExVo> listLotteryPlayWithLang(@Param("lang") String lang, @Param("lotteryId")Integer lotteryId);

}
