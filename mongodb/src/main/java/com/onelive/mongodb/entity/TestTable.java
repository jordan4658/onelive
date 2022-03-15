package com.onelive.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "test_table")
public class TestTable {

    //mongodb需要指定ID
    @Id
    private Long seqid;

    private String name;

    private String password;

    private long roleId;
}
