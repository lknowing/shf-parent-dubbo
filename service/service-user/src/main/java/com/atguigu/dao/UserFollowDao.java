package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/26 21:33
 * @FileName: UserFollowDao
 */
public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer countByUserIdAndHouserId(@Param("userId") Long userId, @Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(Long userId);
}
