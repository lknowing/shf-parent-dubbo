package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 09:41
 * @FileName: CommunityDao
 */
public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}
