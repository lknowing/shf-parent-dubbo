package com.atguigu.service.impl;

import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:33
 * @FileName: RoleServiceImpl
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    //@Autowired //Spring框架提供的依赖注入注解 先byType再byName
    @Resource //JDK提供的依赖注入注解 先byName再byType
            RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Integer insert(Role role) {
        return roleDao.insert(role);
    }
}
