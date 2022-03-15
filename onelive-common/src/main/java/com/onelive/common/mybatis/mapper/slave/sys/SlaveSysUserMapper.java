package com.onelive.common.mybatis.mapper.slave.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.sys.SysUserDTO;
import com.onelive.common.mybatis.entity.SysUser;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


/**
 * <p>
 * 后台系统用户信息 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SlaveSysUserMapper extends BaseMapper<SysUser> {

    @SelectProvider(type = SqlProvider.class, method = "getDtoList")
    List<SysUserDTO> getDtoList(@Param("userName") String userName);

    class SqlProvider {
        public String getDtoList(String userName) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append(" 	u.user_id, ");
            sql.append(" 	u.user_name, ");
            sql.append(" 	u.acc_login, ");
            sql.append(" 	u.phone, ");
            sql.append(" 	u.email, ");
            sql.append(" 	u.acc_status, ");
            sql.append(" 	u.country_code, ");
            sql.append("    r.role_id, ");
            sql.append(" 	r.role_name  ");
            sql.append(" FROM ");
            sql.append(" 	sys_user u ");
            sql.append(" 	LEFT JOIN (SELECT sur.user_id,sur.role_id FROM sys_user_role sur WHERE sur.is_delete = 0) ur ON u.user_id = ur.user_id ");
            sql.append(" 	LEFT JOIN sys_role r ON ur.role_id = r.role_id ");
            sql.append("    WHERE u.is_delete = 0 ");
            if (StrUtil.isNotBlank(userName)) {
                sql.append(" AND u.user_name like concat('%',#{userName},'%')");
            }
            sql.append(" AND u.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            return sql.toString();
        }
    }
}
