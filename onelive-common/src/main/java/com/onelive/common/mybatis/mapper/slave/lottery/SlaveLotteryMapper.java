package com.onelive.common.mybatis.mapper.slave.lottery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.lottery.LotteryQueryDTO;
import com.onelive.common.mybatis.entity.Lottery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩种表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface SlaveLotteryMapper extends BaseMapper<Lottery> {

    List<Lottery> listAllLotteryWithLang(LotteryQueryDTO dto);

    @Select("SELECT " +
            " lo.id, " +
            " IFNULL(la.lottery_name, lo.`name`) `name`, " +
            " lo.lottery_id, " +
            " lo.category_id, " +
            " lo.icon  " +
            "FROM " +
            " lottery lo " +
            " LEFT JOIN lottery_lang la ON lo.lottery_id = la.lottery_id  AND la.lang = #{lang} " +
            "WHERE " +
            " lo.is_delete = 0  ")
    List<Lottery> queryLotteryWithLang(@Param("lang") String lang);

	Lottery getByLotteryId(Long lotteryId, String lang);

}
