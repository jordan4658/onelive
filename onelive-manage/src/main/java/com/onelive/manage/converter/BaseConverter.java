package com.onelive.manage.converter;


import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseConverter<Q, D, E, V> {

    E toE(Q q);

    D toDTO(E e);

    V toVO(D d);

    List<D> toDTOList(List<E> eList);

    List<V> toVOList(List<D> d);

    PageInfo<D> toDTOPage(PageInfo<E> pageInfo);

    PageInfo<V> toVOPage(PageInfo<D> pageInfo);
}
