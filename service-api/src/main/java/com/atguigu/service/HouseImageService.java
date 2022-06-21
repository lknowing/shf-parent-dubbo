package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:43
 * @FileName: HouseImageService
 */
public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findList(Long houseId,Integer type);
}
