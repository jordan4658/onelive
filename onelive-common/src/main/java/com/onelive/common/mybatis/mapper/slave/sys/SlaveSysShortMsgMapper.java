package com.onelive.common.mybatis.mapper.slave.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.sys.SmsCodeDTO;
import com.onelive.common.mybatis.entity.SysShortMsg;

/**
 * <p>
 * 短信记录表 Mapper 接口
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-05
 */
public interface SlaveSysShortMsgMapper extends BaseMapper<SysShortMsg> {

    /**
     * 获取短信验证码
     *
     * @param dto
     * @return
     */
    SysShortMsg selectByCode(SmsCodeDTO dto);

}
