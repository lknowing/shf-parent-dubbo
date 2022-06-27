package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.AdminRole;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 00:23
 * @FileName: AdminRoleDao
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> findRoleIdByAdminId(Long id);

    void deleteByAdminId(Long adminId);
}
