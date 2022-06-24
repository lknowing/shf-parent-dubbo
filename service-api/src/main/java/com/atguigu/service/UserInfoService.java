package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/25 02:02
 * @FileName: UserInfoService
 */
public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);
}
