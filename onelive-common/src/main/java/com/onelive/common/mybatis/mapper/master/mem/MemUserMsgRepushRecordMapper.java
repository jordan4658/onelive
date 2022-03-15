package com.onelive.common.mybatis.mapper.master.mem;

import com.onelive.common.mybatis.entity.MemUserMsgRepushRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <p>
 * 用户消息重新推送记录 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-19
 */
public interface MemUserMsgRepushRecordMapper extends BaseMapper<MemUserMsgRepushRecord> {

    @Insert("<script>" +
            "INSERT INTO  `mem_user_msg_repush_record` ( `user_id`) VALUES " +
            "<foreach collection='list' item='record'   separator=','> " +
            "(#{record.userId})" +
            "</foreach> " +
            "</script>")
    void saveAll(List<MemUserMsgRepushRecord> list);
}
