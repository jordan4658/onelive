package com.onelive.api.service.mem.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemLoginRecordService;
import com.onelive.common.mybatis.entity.MemLoginRecord;
import com.onelive.common.mybatis.mapper.master.mem.MemLoginRecordMapper;
import org.springframework.stereotype.Service;

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
