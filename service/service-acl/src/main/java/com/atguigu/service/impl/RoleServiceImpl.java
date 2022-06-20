package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import org.springframework.transaction.annotation.Transactional;
import service.RoleService;
import javax.annotation.Resource;
import java.util.List;

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
