package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:38
 * @FileName: HouseUserDao
 */
public interface HouseUserDao extends BaseDao<HouseUser> {
    List<HouseUser> findListByHouseId(Long houseId);
}
