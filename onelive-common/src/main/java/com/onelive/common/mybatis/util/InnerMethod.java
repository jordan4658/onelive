package com.onelive.common.mybatis.util;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.onelive.common.utils.others.SpringUtil;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName InnerMethod
 * @Desc
 * @Date 2021/4/17 12:15
 */
@Component
public class InnerMethod {
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void batchInsert(Class<?> model, List entityList) {
        SqlSessionTemplate sqlSessionTemplate = SpringUtil.getBean(SqlSessionTemplate.class);
        String sqlStatement = SqlHelper.table(model).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
        SqlSession batchSqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        Throwable var5 = null;
        try {
            int i = 0;
            for (Iterator var7 = entityList.iterator(); var7.hasNext(); ++i) {
                Object anEntityList = var7.next();
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % 1000 == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();

        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (batchSqlSession != null) {
                if (var5 != null) {
                    try {
                        batchSqlSession.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    batchSqlSession.close();
                }
            }

        }

    }


    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void batchUpdate(Class<?> model, List entityList) {
        SqlSessionTemplate sqlSessionTemplate = SpringUtil.getBean(SqlSessionTemplate.class);
        String sqlStatement = SqlHelper.table(model).getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod());
        SqlSession batchSqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        Throwable var5 = null;
        try {
            int i = 0;
            for (Iterator var7 = entityList.iterator(); var7.hasNext(); ++i) {
                Object anEntityList = var7.next();
                ParamMap<Object> param = new ParamMap();
                param.put("et", anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % 1000 == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();

        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (batchSqlSession != null) {
                if (var5 != null) {
                    try {
                        batchSqlSession.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    batchSqlSession.close();
                }
            }

        }
    }

}    
    