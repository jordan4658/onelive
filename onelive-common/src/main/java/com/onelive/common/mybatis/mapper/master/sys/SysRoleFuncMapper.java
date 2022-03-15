package com.onelive.common.mybatis.mapper.master.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.SysRoleFunc;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 角色拥有功能 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysRoleFuncMapper extends BaseMapper<SysRoleFunc> {

    @SelectProvider(type = SqlProvider.class, method = "getRoleFuncIdList")
    List<Long> getRoleFuncIdList(Long roleId);

    class SqlProvider {
        public String getRoleFuncIdList(Long roleId) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT DISTINCT srf.func_id as funcId ");
            sql.append(" FROM sys_role_func srf ");
            sql.append(" WHERE srf.role_id = #{roleId} ");
            sql.append(" AND srf.is_delete = b'0' ");
            sql.append(" AND srf.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            return sql.toString();
        }
    }

}
