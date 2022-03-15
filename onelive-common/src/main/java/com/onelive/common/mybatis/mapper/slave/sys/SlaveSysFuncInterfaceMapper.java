package com.onelive.common.mybatis.mapper.slave.sys;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.SysFuncInterface;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 功能接口关系 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SlaveSysFuncInterfaceMapper extends BaseMapper<SysFuncInterface> {

    @SelectProvider(type = SqlProvider.class, method = "getInterfaceUrlsByRole")
    List<String> getInterfaceUrlsByRole(@Param("param") List<Long> param);

    class SqlProvider {
        public String getInterfaceUrlsByRole(List<Long> param) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT sfi.itf_url ");
            sql.append(" FROM sys_interface sfi ");
            sql.append(" INNER JOIN sys_func_interface sr ON sfi.itf_id = sr.itf_id AND sr.is_delete = b'0' ");
            sql.append(" INNER JOIN sys_function sf ON sr.func_id = sf.func_id AND sf.is_delete = b'0' ");
            sql.append(" WHERE sfi.is_delete = b'0' ");
            if (!param.isEmpty()) {
                sql.append(" AND sf.func_id in (");
                String ids = CollectionUtil.join(param, ",");
                sql.append(ids);
                sql.append(") ");
            }
            sql.append(" AND sfi.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" order by itf_url desc ");
            return sql.toString();
        }
    }
}
