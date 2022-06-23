package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 09:40
 * @FileName: CommunityServiceImpl
 */
@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    CommunityDao communityDao;

    @Autowired
    DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 3);
        //开启分页功能 将这两个参数与当前线程进行绑定，传递给dao层
        // select 语句最后 自动增加 limit ?,?  limit startIndex,pageSize
        // startIndex = (pageNum-1)*pageSize
        PageHelper.startPage(pageNum, pageSize);// ThreadLoca 线程的本地变量 将这两个参数与当前线程进行绑定
        Page<Community> page = getEntityDao().findPage(filters);

        for (Community community : page) {
            //处理关联字段名称数据
            community.setAreaName(dictDao.getNameById(community.getAreaId()));
            community.setPlateName(dictDao.getNameById(community.getPlateId()));
        }
        return new PageInfo(page, 5);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public Community findById(Serializable id) {
        Community community = communityDao.findById(id);
        if (community != null) {
            //处理关联字段名称数据
            community.setAreaName(dictDao.getNameById(community.getAreaId()));
            community.setPlateName(dictDao.getNameById(community.getPlateId()));
        }
        return community;
    }
}
