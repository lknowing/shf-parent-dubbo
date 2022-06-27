package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.atguigu.service.RoleService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:33
 * @FileName: RoleServiceImpl
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    //JDK提供的依赖注入注解 先byName再byType
    //@Resource
    // Spring框架提供的依赖注入注解 先byType再byName
    @Autowired
    RoleDao roleDao;

    @Autowired
    AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map findRoleByAdminId(Long id) {
        Map maps = new HashMap();
        List noAssignRoleList = new ArrayList();//存放未分配角色
        List assignRoleList = new ArrayList();//存放已分配角色
        //1.查询所有角色
        List<Role> roleList = roleDao.findAll();
        //2.查询该用户所拥有的角色id集合
        List<Long> roleIdList = adminRoleDao.findRoleIdByAdminId(id);
        //3.将所有角色划分到两个集合中返回
        for (Role role : roleList) {
            Long roleId = role.getId();
            if (roleIdList.contains(roleId)) {//迭代角色id在已拥有角色id集合中 说明此角色已经分配的
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }
        maps.put("noAssignRoleList", noAssignRoleList);
        maps.put("assignRoleList", assignRoleList);
        return maps;
    }

    @Override
    public void saveUserRoleRelationShip(Long adminId, Long[] roleIds) {
        //1.先删除已有的角色
        adminRoleDao.deleteByAdminId(adminId);
        //2.重新分配新的角色
        List<AdminRole> userRoleList = new ArrayList<>();
        if (roleIds != null && roleIds.length > 0) {
            for (Long roleId : roleIds) {
                if (StringUtils.isEmpty(roleId)) {//框架自动分解 roleIds 字符串 会出现逗号后面是是null 逻辑判断并且跳过它
                    continue;
                }
                AdminRole userRole = new AdminRole();
                userRole.setAdminId(adminId);
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);
            }
            adminRoleDao.insertBatch(userRoleList);
        }
    }


    @Override
    protected BaseDao getEntityDao() {
        return roleDao;
    }
}
