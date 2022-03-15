package com.onelive.common.mybatis.mapper.slave.live;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.req.live.LiveBagListReq;
import com.onelive.common.model.vo.live.LiveBagListVO;
import com.onelive.common.mybatis.entity.LiveBag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-23
 */
public interface SlaveLiveBagMapper extends BaseMapper<LiveBag> {

    @Select("<script>" +
            " SELECT " +
            "        bag.id, " +
            "        bag.bag_name, " +
            "        bag.img, " +
            "        bag.price, " +
            "        bag.country_name_list, " +
            "        bag.is_frozen " +
            "        FROM " +
            "        live_bag bag " +
            "        <where> " +
            "            bag.is_delete = 0 " +
            "            <if test=\"bagName != null and bagName != ''\"> " +
            "                AND bag.bag_name LIKE CONCAT('%', #{bagName}, '%') " +
            "            </if> " +
            "            <if test=\"countryId != null and countryId != ''\"> " +
            "                AND FIND_IN_SET(#{countryId},bag.country_id_list) " +
            "            </if> " +
            "        </where> " +
            "        ORDER BY " +
            "        bag.type"+
            "</script>")
    List<LiveBagListVO> getList(LiveBagListReq param);

    @Select("SELECT " +
            "            bag.id, " +
            "            bag.type, " +
            "            bag.animation, " +
            "            bag.animation_type, " +
            "            bag.animation_status, " +
            "            bag.retention_time, " +
            "            lang.bag_name, " +
            "            bag.img, " +
            "            bag.price " +
            "        FROM " +
            "            live_bag bag " +
            "                LEFT JOIN live_bag_lang lang ON lang.bag_id = bag.id " +
            "        WHERE " +
            "            bag.is_delete = 0 AND lang.lang = #{lang} " +
            "        ORDER BY " +
            "            bag.type")
    List<LiveBag> getListWithLang(@Param("lang") String lang);
}
