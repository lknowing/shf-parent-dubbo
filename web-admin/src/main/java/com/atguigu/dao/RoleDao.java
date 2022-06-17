package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Role;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:30
 * @FileName: RoleDao
 */
public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();

}
