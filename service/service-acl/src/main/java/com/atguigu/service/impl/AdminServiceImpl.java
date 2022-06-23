package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminDao;
import com.atguigu.entity.Admin;
import org.springframework.transaction.annotation.Transactional;
import com.atguigu.service.AdminService;
import javax.annotation.Resource;
import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 18:57
 * @FileName: AdminServiceImpl
 */
@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Resource
    AdminDao adminDao;

    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }
}
