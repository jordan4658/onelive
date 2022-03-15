package com.onelive.common.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserMsgRepushRecord;

import java.util.List;

/**
 * <p>
 * 用户消息重新推送记录 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-19
 */
public interface MemUserMsgRepushRecordService extends IService<MemUserMsgRepushRecord> {

    /**
     * 保存一个用户的记录
     * @param user
     */
    void addRecordByUser(MemUser user);

    /**
     * 保存多个用户的记录(异步)
     * @param userIds
     */
    void addRecordByUserIdList(List<Long> userIds);
    /**
     * 保存多个用户的记录(异步)
     * @param userList
     */
    void addRecordByUserList(List<MemUser> userList);

    /**
     * 保存多条记录(批量插入)
     * @param list
     */
    void saveAll(List<MemUserMsgRepushRecord> list);
}
