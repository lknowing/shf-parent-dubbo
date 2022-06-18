package com.atguigu.service.impl;

import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    //@Autowired
    // Spring框架提供的依赖注入注解 先byType再byName
    @Resource
    //JDK提供的依赖注入注解 先byName再byType
            RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }


    @Override
    protected BaseDao getEntityDao() {
        return roleDao;
    }
}
