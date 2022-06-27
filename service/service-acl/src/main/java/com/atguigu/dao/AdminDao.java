package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 16:02
 * @FileName: AdminDao
 */
public interface AdminDao extends BaseDao<Admin> {
    List<Admin> findAll();

    Admin getByUsername(String username);
}
