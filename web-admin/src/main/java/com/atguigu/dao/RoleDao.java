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
    //模块特有的方法 声明到子接口中 公共的声明到父接口中
    List<Role> findAll();

}
