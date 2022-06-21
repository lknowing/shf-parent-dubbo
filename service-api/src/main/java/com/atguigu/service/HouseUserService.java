package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:44
 * @FileName: HouseUserService
 */
public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findListByHouseId(Long houseId);
}
