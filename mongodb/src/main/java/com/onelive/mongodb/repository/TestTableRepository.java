package com.onelive.mongodb.repository;

import com.onelive.mongodb.entity.TestTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface TestTableRepository extends MongoRepository<TestTable, Long> {
}