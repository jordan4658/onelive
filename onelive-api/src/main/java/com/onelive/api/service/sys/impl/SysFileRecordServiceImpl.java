package com.onelive.api.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysFileRecordService;
import com.onelive.common.mybatis.entity.SysFileRecord;
import com.onelive.common.mybatis.mapper.master.demo.TestUsersMapper;
import com.onelive.common.mybatis.mapper.master.sys.SysFileRecordMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysFileRecordMapper;
import com.onelive.common.mybatis.util.MysqlMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class SysFileRecordServiceImpl extends ServiceImpl<SysFileRecordMapper, SysFileRecord> implements SysFileRecordService {

    @Resource
    private SlaveSysFileRecordMapper slaveSysFileRecordMapper;

    @Override
    public SysFileRecord getFileUrlByMd5Flag(String md5Flag) {
        QueryWrapper<SysFileRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysFileRecord::getMd5Flag,md5Flag);
        List<SysFileRecord> list = slaveSysFileRecordMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void saveSysFileRecord(SysFileRecord record) {
        try {
            slaveSysFileRecordMapper.insert(record);
        }catch (Exception e){
            log.error("保存文件记录信息错误",e);
        }
    }

    @Override
    public void saveBatchSysFileRecord(List<SysFileRecord> list) {
        try {
            MysqlMethod.batchStatment(list, SysFileRecordMapper.class, (item, mapper) -> mapper.insert(item));
        }catch (Exception e){
            log.error("批量保存文件记录信息错误",e);
        }
    }
}
