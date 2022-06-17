package com.atguigu.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 15:27
 * @FileName: BaseDao
 */
public interface BaseDao<T> {
    Integer insert(T t);

    T findById(Serializable id);

    void update(T t);

    void delete(Serializable id);

    Page<T> findPage(Map<String, Object> filters);
}
