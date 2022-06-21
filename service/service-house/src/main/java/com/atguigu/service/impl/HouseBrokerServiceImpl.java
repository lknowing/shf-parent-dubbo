package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.HouseBrokerDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:41
 * @FileName: HouseBrokerServiceImpl
 */
@Transactional
@Service(interfaceClass = HouseBrokerService.class)
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    HouseBrokerDao houseBrokerDao;

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }

    @Override
    public List<HouseBroker> findListByHouseId(Long houseId) {
        return houseBrokerDao.findListByHouseId(houseId);
    }
}
