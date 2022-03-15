package com.onelive.common.mybatis.sqlProvider;

import com.onelive.common.model.req.mem.usercenter.ActivityListDTO;

/**
 * 活动管理
 */
public class SysAdvActivitySqlProvider {

    /**
     * 根据国家和语言查询活动列表
     * @param dto
     * @return
     */
   public String listWithCountryAndLang(ActivityListDTO dto){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT " +
                " act.id, " +
                " IF( ISNULL( lang.activity_name )| LENGTH( TRIM( lang.activity_name ))< 1, act.activity_name, lang.activity_name ) activity_name, " +
                " act.activity_type, " +
                " act.start_date, " +
                " act.end_date, " +
                " act.country_code_list, " +
                " act.skip_model, " +
                " act.skip_url, " +
                " act.config_id, " +
                " act.lottery_category_id, " +
                " act.lottery_id, " +
                " act.img_url, " +
                " act.create_time  " +
                "FROM " +
                " sys_adv_activity act " +
                " LEFT JOIN sys_adv_activity_lang lang ON lang.activity_id = act.id  " +
                " AND lang.lang = '").append(dto.getLang()).append(
                "WHERE " +
                " act.is_frozen = 0  " +
                " AND act.is_delete = 0  ");
        if(dto.getActivityType()!=null){
            sql.append(" AND act.activity_type = '").append(dto.getActivityType()).append("'");
        }
        sql.append(" AND FIND_IN_SET('").append(dto.getCountryCode()).append("',act.country_code_list)");
        return sql.toString();
    }

}
