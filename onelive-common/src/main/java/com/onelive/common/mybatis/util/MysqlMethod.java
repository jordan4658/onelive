package com.onelive.common.mybatis.util;


import com.onelive.common.utils.others.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * @ClassName MysqlMethod
 * @Desc 多数据源下，mybatisplys自带批量方法不生效，所以手写批量方法
 * @Date 2021/4/17 10:08
 */

@Slf4j
public class MysqlMethod {

    public static void batchInsert(Class<?> model, List entityList) {
        InnerMethod innerMethod = SpringUtil.getBean(InnerMethod.class);
        innerMethod.batchInsert(model, entityList);
    }

    public static void batchUpdate(Class<?> model, List entityList) {
        InnerMethod innerMethod = SpringUtil.getBean(InnerMethod.class);
        innerMethod.batchUpdate(model, entityList);
    }

    public static <A, B> int batchStatment(List<A> list, Class<B> mapperClass, BatchDbOperationInterface<A, B> function) {
        SqlSessionTemplate sqlSessionTemplate = SpringUtil.getBean(SqlSessionTemplate.class);
        if (null == list || list.isEmpty()) return 0;
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        B mapper = session.getMapper(mapperClass);
        try {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                A item = list.get(i);
                function.apply(item, mapper);
                if (i % 1000 == 999 || i == size - 1) {
                    session.commit();
                    session.clearCache();
                }
            }

            return size;
        } catch (Exception e) {
            log.error("批量插入或更新数据出错", e);
            session.rollback();
        } finally {
            session.close();
        }
        return 0;
    }


}
    