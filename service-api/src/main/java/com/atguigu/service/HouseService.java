package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 15:54
 * @FileName: HouseService
 */
public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);
}
