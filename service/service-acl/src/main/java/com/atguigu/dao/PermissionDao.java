package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 09:10
 * @FileName: PermissionDao
 */
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> findListByAdminId(Long adminId);

    List<String> findCodeListByAdminId(Long adminId);

    List<String> findAllCodeList();
}
