package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 18:56
 * @FileName: AdminService
 */
public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin getByUsername(String username);
}
