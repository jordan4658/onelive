package com.onelive.common.mybatis.util;


@FunctionalInterface
public interface BatchDbOperationInterface<A, B> {
    // A为要操作的javabean类型，B为相应的Mybatis Mapper
    void apply(A type, B mapper);
}
