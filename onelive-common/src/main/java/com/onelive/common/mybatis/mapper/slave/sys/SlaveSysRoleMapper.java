package com.onelive.common.mybatis.mapper.slave.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.sys.SysUserRoleDTO;
import com.onelive.common.mybatis.entity.SysRole;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


/**
 * <p>
 * 后台系统角色 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SlaveSysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户标识号获取角色信息
     *
     * @param userId
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "getRoleByUserId")
    SysRole getRoleByUserId(Long userId);


    @SelectProvider(type = SqlProvider.class, method = "getUserRoleList")
    List<SysUserRoleDTO> getUserRoleList();

    public class SqlProvider {
        public String getRoleByUserId(Long userId) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT sr.* ");
            sql.append(" FROM sys_role sr ");
            sql.append(" INNER JOIN sys_user_role sur ON sr.role_id = sur.role_id ");
            sql.append(" WHERE sur.is_delete = b'0' ");
            sql.append(" AND sur.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" AND sur.user_id = #{userId} LIMIT 1 ");
            return sql.toString();
        }

        public String getUserRoleList() {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append(" 	u.user_id, ");
            sql.append(" 	u.user_name, ");
            sql.append(" 	r.role_id, ");
            sql.append(" 	r.role_name ");
            sql.append(" FROM ");
            sql.append(" 	sys_user u ");
            sql.append(" LEFT JOIN ");
            sql.append(" 	(SELECT sur.user_id,sur.role_id FROM sys_user_role sur WHERE sur.is_delete = 0) ur ON u.user_id = ur.user_id ");
            sql.append(" LEFT JOIN  ");
            sql.append(" 	sys_role r ON ur.role_id = r.role_id ");
            sql.append(" WHERE u.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            return sql.toString();
        }
    }

}
