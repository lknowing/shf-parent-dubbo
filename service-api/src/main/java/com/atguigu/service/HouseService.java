package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 15:54
 * @FileName: HouseService
 */
public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo);

    void deletePage(Long houseId);
}
