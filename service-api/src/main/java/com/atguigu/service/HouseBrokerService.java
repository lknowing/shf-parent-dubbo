package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:40
 * @FileName: HouseBrokerService
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long houseId);
}
