package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:32
 * @FileName: RoleService
 */
public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

}
