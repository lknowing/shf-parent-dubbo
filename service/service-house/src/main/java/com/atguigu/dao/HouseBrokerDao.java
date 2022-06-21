package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:27
 * @FileName: HouseBrokerDao
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long houseId);
}
