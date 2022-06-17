package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 15:35
 * @FileName: BaseService
 */
public interface BaseService<T> {
    Integer insert(T role);

    T findById(Serializable id);

    void update(T role);

    void delete(Serializable id);

    PageInfo findPage(Map<String, Object> filters);
}
