package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 09:07
 * @FileName: PermissionService
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据角色获取授权权限数据
     *
     * @return
     */
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存角色权限
     *
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermissionRelationShip(Long roleId, Long[] permissionIds);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 菜单全部数据
     *
     * @return
     */
    List<Permission> findAllMenu();

    List<String> findCodeListByAdminId(Long id);
}
