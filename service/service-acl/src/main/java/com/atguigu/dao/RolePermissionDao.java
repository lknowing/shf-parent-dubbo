package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.RolePermission;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 09:25
 * @FileName: RolePermissionDao
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {
    //根据角色id查询许可id集合
    List<Long> findPermissionIdListByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}
