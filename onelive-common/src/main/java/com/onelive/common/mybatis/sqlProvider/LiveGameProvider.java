package com.onelive.common.mybatis.sqlProvider;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.onelive.common.model.req.live.AppLiveGameListReq;

/**
 * 直播间游戏查询
 */
public class LiveGameProvider {

    /**
     * 直播间游戏列表查询
     * @return
     */
    public String listWithLang(AppLiveGameListReq req){
        StrBuilder sb = new StrBuilder();
        sb.append("SELECT " +
                " game.id, " +
                " IF( ISNULL( lang.`name` )|| LENGTH( TRIM( lang.`name` ))< 1, game.`name`, lang.`name` ) `name`, " +
                " IF( ISNULL( lang.icon_url )|| LENGTH( TRIM( lang.icon_url ))< 1, game.icon_url, lang.icon_url ) icon_url, " +
                " game.category_id, " +
                " game.game_code, " +
                " game.is_third  " +
                "FROM " +
                " live_game game " +
                " LEFT JOIN live_game_lang lang ON game.id = lang.game_id AND lang.lang = '")
                .append(req.getLang()).append("' WHERE game.is_delete=0 AND game.is_show=1 ");
            if(StrUtil.isNotBlank(req.getCode())) {
                sb.append(" AND game.code = '")
                        .append(req.getCode()).append("' ");
            }
        return sb.toString();
    }

}
