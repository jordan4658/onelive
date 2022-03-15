package com.onelive.common.mybatis.mapper.slave.game;

import com.onelive.common.mybatis.entity.GameSyncRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-13
 */
public interface SlaveGameSyncRecordMapper extends BaseMapper<GameSyncRecord> {


    @Insert("<script>" +
                "INSERT INTO game_sync_record(category_id,start_time,end_time,create_time)VALUES" +
                "<foreach collection='list' item='record'   separator=','> " +
                    "(#{record.categoryId},#{record.startTime},#{record.endTime},#{record.createTime})" +
                "</foreach> " +
            "</script>")
    Boolean insertBatch(List<GameSyncRecord> list);

}
