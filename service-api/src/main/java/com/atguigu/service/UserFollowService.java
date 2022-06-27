package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/26 21:28
 * @FileName: UserFollowService
 */
public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 关注房源
     *
     * @param userId
     * @param houseId
     */
    Boolean follow(Long userId, Long houseId);

    boolean isFollow(Long userInfoId, Long houseId);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    boolean cancelFollow(Long id);
}
