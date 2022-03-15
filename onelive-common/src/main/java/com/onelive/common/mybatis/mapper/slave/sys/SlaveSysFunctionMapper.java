package com.onelive.common.mybatis.mapper.slave.sys;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.sys.SysFunctionByRollAllDTO;
import com.onelive.common.model.dto.sys.SysFunctionDTO;
import com.onelive.common.model.dto.sys.SysFunctionForRoleDTO;
import com.onelive.common.mybatis.entity.SysFunction;
import com.onelive.common.utils.Login.LoginInfoUtil;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 后台系统(运营后台)功能 Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SlaveSysFunctionMapper extends BaseMapper<SysFunction> {

    @SelectProvider(type = SqlProvider.class, method = "getParentFuncIdListNode")
    List<Long> getParentFuncIdListNode(Long id);

    @SelectProvider(type = SqlProvider.class, method = "getSysFunctionList")
    List<SysFunctionForRoleDTO> getSysFunctionList(@Param("allFunId") List<Long> allFunId);

    @SelectProvider(type = SqlProvider.class, method = "getSysFunctionTreeByRoleAll")
    List<SysFunctionByRollAllDTO> getSysFunctionTreeByRoleAll(@Param("roleId") Long roleId);

    @SelectProvider(type = SqlProvider.class, method = "getSysFunctionTreeByFunId")
    List<SysFunctionDTO> getSysFunctionTreeByFunId(Long funId);

    public static class SqlProvider {

        public String getParentFuncIdListNode(Long funcId) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT T2.func_id ");
            sql.append("         FROM ( ");
            sql.append("                  SELECT @r AS _id, ");
            sql.append("                     (SELECT @r := parent_func_id FROM sys_function WHERE func_id = _id) AS parent_id, ");
            sql.append("                     @l := @l + 1 AS lvl ");
            sql.append("                  FROM ");
            sql.append("                      (SELECT @r := " + funcId + ", @l := 0) vars, sys_function h ");
            sql.append("                  WHERE @r <> 0) T1 ");
            sql.append("  ");
            sql.append("                  INNER JOIN sys_function T2 ");
            sql.append("                             ON T1._id = T2.func_id ");
            sql.append("             AND T2.is_delete = 0 ");
            sql.append("             AND T2.func_status = 0 ");
            sql.append(" AND T2.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append("         ORDER BY T1.lvl DESC ");
            return sql.toString();
        }

        public String getSysFunctionList(List<Long> allFunId) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * from sys_function s ");
            sql.append(" where s.is_delete = b'0' ");
            if (!allFunId.isEmpty()) {
                sql.append(" and s.func_id in ");
                sql.append(" ( ");
                String ids = CollectionUtil.join(allFunId, ",");
                sql.append(ids);
                sql.append(" ) ");
            }
            sql.append(" AND s.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");

            return sql.toString();
        }

        public String getSysFunctionTreeByRoleAll(Long roleId) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append(" 	sf.func_id, ");
            sql.append(" 	sf.parent_func_id, ");
            sql.append(" 	sf.of_system, ");
            sql.append(" 	sf.func_type, ");
            sql.append(" 	sf.func_name, ");
            sql.append(" 	sf.func_url, ");
            sql.append(" 	1 AS roleId, ");
            sql.append(" 	#{roleId} AS roleId, ");
            sql.append(" 	( ");
            sql.append(" 	IF ");
            sql.append(" 		(( ");
            sql.append(" 			SELECT ");
            sql.append(" 				sr.func_id ");
            sql.append(" 			FROM ");
            sql.append(" 				sys_role_func sr  ");
            sql.append(" 			WHERE ");
            sql.append(" 				sr.func_id = sf.func_id  ");
            sql.append(" 				AND sr.role_id = #{roleId}  ");
            sql.append(" 				AND sr.is_delete = b'0'  ");
            sql.append(" 				LIMIT 1  ");
            sql.append(" 				) IS NOT NULL, ");
            sql.append(" 			1, ");
            sql.append(" 			0  ");
            sql.append(" 		)  ");
            sql.append(" 	) AS checkbox  ");
            sql.append(" FROM ");
            sql.append(" 	sys_function sf  ");
            sql.append(" WHERE ");
            sql.append(" 	sf.is_delete = b'0'  ");
            sql.append(" 	AND sf.func_status = 0  ");
            sql.append(" AND sf.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" ORDER BY ");
            sql.append(" 	sf.create_time ASC ");
            return sql.toString();
        }

        public String getSysFunctionTreeByFunId(Long funId) {
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            sql.append(" 	c.func_id, ");
            sql.append(" 	c.parent_func_id, ");
            sql.append(" 	c.func_name, ");
            sql.append(" 	c.func_type, ");
            sql.append(" 	c.func_url , ");
            sql.append(" 	c.func_status ");
            sql.append(" FROM ");
            sql.append(" 	( ");
            sql.append(" 	SELECT ");
            sql.append(" 		p.func_id, ");
            sql.append(" 		p.parent_func_id, ");
            sql.append(" 		p.func_name, ");
            sql.append(" 		p.func_type, ");
            sql.append(" 		p.func_url, ");
            sql.append(" 		p.func_status, ");
            sql.append(" 		p.update_time  ");
            sql.append(" 	FROM ");
            sql.append(" 		sys_function p  ");
            sql.append(" 	WHERE ");
            sql.append(" 		p.func_id = #{funId}  ");
            sql.append(" AND p.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" 	UNION ");
            sql.append(" 	SELECT ");
            sql.append(" 		s.func_id, ");
            sql.append(" 		s.parent_func_id, ");
            sql.append(" 		s.func_name, ");
            sql.append(" 		s.func_type, ");
            sql.append(" 		s.func_url, ");
            sql.append(" 		s.func_status, ");
            sql.append(" 		s.update_time  ");
            sql.append(" 	FROM ");
            sql.append(" 		sys_function s  ");
            sql.append(" 	WHERE ");
            sql.append(" 		s.is_delete = 0  ");
            sql.append(" 		AND s.parent_func_id = #{funId}  ");
            sql.append(" AND s.merchant_code = '"+LoginInfoUtil.getMerchantCode()+"' ");
            sql.append(" 	) c  ");
            sql.append(" ORDER BY ");
            sql.append(" 	c.update_time DESC ");
            return sql.toString();
        }
    }
}
