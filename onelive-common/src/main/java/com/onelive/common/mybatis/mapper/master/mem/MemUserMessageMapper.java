package com.onelive.common.mybatis.mapper.master.mem;

import com.onelive.common.mybatis.entity.MemUserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <p>
 * 用户消息表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-17
 */
public interface MemUserMessageMapper extends BaseMapper<MemUserMessage> {

    @Insert("<script>" +
            "INSERT INTO  `mem_user_message` ( `user_id`, `msg_id`, `msg_type`, `create_time`) VALUES " +
            "<foreach collection='msgList' item='msg'   separator=','> " +
            "(#{msg.userId},#{msg.msgId},#{msg.msgType},#{msg.createTime})" +
            "</foreach> " +
            "</script>")
    void saveAll(List<MemUserMessage> msgList);
}
