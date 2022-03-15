package com.onelive.mongodb.base;


import cn.hutool.core.bean.BeanUtil;
import com.onelive.mongodb.util.MongoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public abstract class BaseMongoService {

    @Resource
    protected MongoTemplate template;


    protected <T> List<T> find(Query query, Class<T> clazz) {
        return template.find(query, clazz);
    }

    protected <T> T findOne(Query query, Class<T> clazz) {
        return template.findOne(query, clazz);
    }


    protected <T> List<T> selectList(Criteria criteria, Sort sort, Class<T> clazz, Integer limit) {
        Query query = Query.query(criteria);
        if (sort != null) {
            query.with(sort);
        }
        query.limit(limit);
        return template.find(query, clazz);
    }

    protected <T> List<T> selectList(Criteria criteria, Class<T> clazz, Integer limit) {
        return selectList(criteria, null, clazz, limit);
    }

    protected <T> void save(T t) {
        template.save(t);
    }

    protected <T> void update(T t) {
        Object id = BeanUtil.getFieldValue(t, "id");
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = MongoUtil.updateOf(t);

        template.updateFirst(query, update, t.getClass());
    }

    protected <T> void batchInsert(List<T> insertList, Class<T> clazz) {
        try {
            BulkOperations bulkOps = template.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz);
            bulkOps.insert(insertList);
            bulkOps.execute();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    protected <T> void batchUpdate(List<T> updateList, Class<T> clazz) {
        try {
            BulkOperations bulkOps = template.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz);
            for (T data: updateList) {
                Object id = BeanUtil.getFieldValue(data, "id");
                Query query = Query.query(Criteria.where("_id").is(id));
                Update update = MongoUtil.updateOf(data);
                bulkOps.updateOne(query, update);
            }
            bulkOps.execute();
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
