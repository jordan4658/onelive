package com.onelive.anchor.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.MemLoginRecordService;
import com.onelive.common.mybatis.entity.MemLoginRecord;
import com.onelive.common.mybatis.mapper.master.mem.MemLoginRecordMapper;

/**
 * <p>
 * 登录记录表 服务实现类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-07
 */
@Service
public class MemLoginRecordServiceImpl extends ServiceImpl<MemLoginRecordMapper, MemLoginRecord> implements MemLoginRecordService {

}
