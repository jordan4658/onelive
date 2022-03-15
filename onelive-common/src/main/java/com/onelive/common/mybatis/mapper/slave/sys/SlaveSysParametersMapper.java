package com.onelive.common.mybatis.mapper.slave.sys;

/**
 * @ClassName SlaveSysParametersMapper
 * @Date 2021/4/10 17:35
 */


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 系统参数 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-31
 */
public interface SlaveSysParametersMapper extends BaseMapper<SysParameter> {

    @SelectProvider(type = SqlProvider.class, method = "selectByCode")
    SysParameter selectByCode(String paramCode);

    @SelectProvider(type = SqlProvider.class, method = "getPageList")
    List<SysParameter> getPageList();

    @Select("select param_code from sys_parameter")
    List<String> queryAllKey();


    public static class SqlProvider {

        public String selectByCode(String paramCode) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * ");
            sql.append(" from sys_parameter ");
            sql.append(" where param_code = #{paramCode,jdbcType=VARCHAR} ");
            sql.append(" and is_delete = b'0' ");
            sql.append(" AND merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" limit 1");
            return sql.toString();
        }

        public String getPageList() {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * ");
            sql.append(" from sys_parameter ");
            sql.append(" where merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" order by param_id asc ");
            return sql.toString();
        }
    }
}
