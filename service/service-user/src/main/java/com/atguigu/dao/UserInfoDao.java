package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserInfo;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/25 02:04
 * @FileName: UserInfoDao
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);
}
