package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.service.PermissionService;
import com.atguigu.util.PermissionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 09:09
 * @FileName: PermissionServiceImpl
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //查询全部权限节点
        List<Permission> permissionList = permissionDao.findAll();

        List<Map<String, Object>> zNodes = new ArrayList<>();
        //指定哪些节点需要勾选 查询当前角色所对应的许可
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);
        for (Permission permission : permissionList) {
            if (StringUtils.isEmpty(permission)) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            //checked表示复选框是否打钩，表示拥有的权限 进行回显
            if (permissionIdList.contains(permission.getId())) {
                map.put("checked", true);
            }
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public void saveRolePermissionRelationShip(Long roleId, Long[] permissionIds) {
        //1.先删除旧关系数据
        rolePermissionDao.deleteByRoleId(roleId);
        //2.再增加一批新的数据关系
        if (permissionIds != null && permissionIds.length > 0) {//整棵树一个复选框都没有被勾选，数组是null
            List<RolePermission> list = new ArrayList();
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                list.add(rolePermission);
            }
            rolePermissionDao.insertBatch(list);
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        //admin账号id为：1 超级管理员
        if (adminId.longValue() == 1) {
            //如果是超级管理员，获取所有菜单
            permissionList = permissionDao.findAll();//可以不查询type=2 按钮 三级节点的permission
        } else {
            //如果是普通管理员，根据用户获取角色，再获取相应的权限，需要进行条件关联查询
            permissionList = permissionDao.findListByAdminId(adminId);//按照类型type=1查询 只查询菜单 按钮不查询
        }
        //把权限数据构建成树形结构数据 组装父子关系
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> findCodeListByAdminId(Long adminId) {
        //超级管理员admin账号id为：1
        if(adminId.longValue() == 1) {
            return permissionDao.findAllCodeList();
        }
        return permissionDao.findCodeListByAdminId(adminId);
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if (CollectionUtils.isEmpty(permissionList)) {
            return null;
        }

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }
}
