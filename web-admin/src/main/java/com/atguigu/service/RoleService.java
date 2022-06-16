package com.atguigu.service;

import com.atguigu.entity.Role;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:32
 * @FileName: RoleService
 */
public interface RoleService {
    List<Role> findAll();

    Integer insert(Role role);
}
