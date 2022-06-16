package com.atguigu.dao;

import com.atguigu.entity.Role;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:30
 * @FileName: RoleDao
 */
public interface RoleDao {
    List<Role> findAll();

    Integer insert(Role role);
}
