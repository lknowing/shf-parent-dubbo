package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 18:33
 * @FileName: HouseImageDao
 */
public interface HouseImageDao extends BaseDao<HouseImage> {
    List<HouseImage> findList(@Param("houseId") Long houseId, @Param("type") Integer type);
}
