package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/24 09:50
 * @FileName: HouseController
 */
@RestController//@Controller和@ResponseBody组合：不需要在每一个方法上增加@ResponseBody了
@RequestMapping("/house")
public class HouseController {
    @Reference
    HouseService houseService;

    @Reference
    CommunityService communityService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    HouseImageService houseImageService;

    @Reference
    UserFollowService userFollowService;

    //异步请求处理
    @RequestMapping("/info/{houseId}")
    public Result<Map<String, Object>> list(@PathVariable("houseId") Long houseId, HttpServletRequest request) {
        House house = houseService.findById(houseId);
        Community community = communityService.findById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(houseId);
        List<HouseImage> houseImage1List = houseImageService.findList(houseId, 1);

        //----------补充代码 登录后判断当前用户是否已经关注当前房源 将isFollow变成动态值-------------
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        boolean isFollow = false;
        if (null != userInfo) {
            Long userInfoId = userInfo.getId();
            isFollow = userFollowService.isFollow(userInfoId, houseId);
        }
        //-------------------------------------------------------------------------------
        Map<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("community", community);
        map.put("houseBrokerList", houseBrokerList);
        map.put("houseImage1List", houseImage1List);
        map.put("isFollow", isFollow);
        return Result.ok(map);//将分页数据封装在Result中返回,最后转换为JSON串返回
    }

    //异步请求处理
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result<PageInfo<HouseVo>> list(@PathVariable("pageNum") Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @RequestBody HouseQueryVo houseQueryVo) {//通过@RequestBody注解获取请求体json，转换为vo对象
        PageInfo<HouseVo> page = houseService.findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(page);//将分页数据封装在Result中返回,最后转换为JSON串返回
    }
}
