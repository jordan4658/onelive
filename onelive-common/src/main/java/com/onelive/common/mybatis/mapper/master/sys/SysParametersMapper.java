package com.onelive.common.mybatis.mapper.master.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>
 * 系统参数 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-31
 */
public interface SysParametersMapper extends BaseMapper<SysParameter> {

    @SelectProvider(type = SqlProvider.class, method = "selectByCode")
    SysParameter selectByCode(String paramCode);

    class SqlProvider {
        public String selectByCode(String paramCode) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * ");
            sql.append(" from sys_parameter ");
            sql.append(" where param_code = #{paramCode,jdbcType=VARCHAR} ");
            sql.append(" and param_status = 0 ");
            sql.append(" and is_delete = b'0' ");
            sql.append(" AND merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" limit 1");
            return sql.toString();
        }
    }
}
