package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 09:39
 * @FileName: CommunityService
 */
public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
