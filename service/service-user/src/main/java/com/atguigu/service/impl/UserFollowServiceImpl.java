package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.DictService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/26 21:31
 * @FileName: UserFollowServiceImpl
 */
@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    UserFollowDao userFollowDao;

    @Reference(check = false)//不去检查字典服务是否先启动
            DictService dictService;//远程调用字典服务 不能使用DictDao 不在一个服务器

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public Boolean follow(Long userId, Long houseId) {
        Integer count = userFollowDao.countByUserIdAndHouserId(userId, houseId);
        if (count.intValue() == 0) {
            UserFollow userFollow = new UserFollow();
            userFollow.setUserId(userId);
            userFollow.setHouseId(houseId);
            userFollowDao.insert(userFollow);
        }
        return true;
    }

    @Override
    public boolean isFollow(Long userInfoId, Long houseId) {
        Integer count = userFollowDao.countByUserIdAndHouserId(userInfoId, houseId);
        if (count > 0) {//count.intValue() 自动拆箱
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowDao.findListPage(userId);
        List<UserFollowVo> list = page.getResult();
        for (UserFollowVo userFollowVo : list) {
            //户型：
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            //楼层
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            //朝向：
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<UserFollowVo>(page, 10);
    }

    @Override
    public boolean cancelFollow(Long id) {
        userFollowDao.delete(id);
        return true;
    }

}
