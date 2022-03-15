package com.onelive.api.service.sys;

import com.onelive.common.mybatis.entity.SysFileRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface SysFileRecordService extends IService<SysFileRecord> {

    SysFileRecord getFileUrlByMd5Flag(String md5Flag);

    void saveSysFileRecord(SysFileRecord record);

    void saveBatchSysFileRecord(List<SysFileRecord> list);

}
