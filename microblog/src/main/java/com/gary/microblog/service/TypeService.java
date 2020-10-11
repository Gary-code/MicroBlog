package com.gary.microblog.service;

import com.gary.microblog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    //分页查询
    Page<Type> listType(Pageable pageable);

    //先查后修改
    Type updateType(Long id, Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();
    List<Type> findTopType(Integer size);
}
