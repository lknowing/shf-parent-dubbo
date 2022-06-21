package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/21 14:25
 * @FileName: HouseController
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_DETAIL = "house/detail";

    @Reference
    HouseService houseService;

    @Reference
    CommunityService communityService;

    @Reference
    DictService dictService;

    @Reference
    HouseImageService houseImageService;

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    HouseUserService houseUserService;

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,Model model) {
        //1.房源实体对象数据
        House house = houseService.findById(id);//在业务层，重写findById方法，关联数据字典的名称
        //2.小区对象数据
        Community community = communityService.findById(house.getCommunityId());//在业务层，重写findById方法，关联数据字典的名称
        //3.房源图片数据
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        //4.房产图片数据
        List<HouseImage> houseImage2List = houseImageService.findList(id, 2);
        //5.房产经纪人数据
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        //6.房东数据
        List<HouseUser> houseUserList = houseUserService.findListByHouseId(id);

        model.addAttribute("house", house);
        model.addAttribute("community", community);
        model.addAttribute("houseImage1List", houseImage1List);
        model.addAttribute("houseImage2List", houseImage2List);
        model.addAttribute("houseBrokerList", houseBrokerList);
        model.addAttribute("houseUserList", houseUserList);

        return PAGE_DETAIL;
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        houseService.publish(id,status);
        return LIST_ACTION;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        houseService.delete(id);
        return LIST_ACTION;
    }

    @RequestMapping("/update")
    public String update(House house, HttpServletRequest request) {
        houseService.update(house);
        return this.successPage(null, request);
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        //1.获取房源实体对象
        House house = houseService.findById(id);
        //2.小区数据
        List<Community> communityList = communityService.findALl();
        //3.六个数据字段集合
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        List<Dict> floorList = dictService.findListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        List<Dict> directionList = dictService.findListByDictCode("direction");
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        model.addAttribute("house", house);

        model.addAttribute("communityList", communityList);

        model.addAttribute("houseTypeList", houseTypeList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("buildStructureList", buildStructureList);
        model.addAttribute("directionList", directionList);
        model.addAttribute("decorationList", decorationList);
        model.addAttribute("houseUseList", houseUseList);
        return PAGE_EDIT;
    }

    @RequestMapping("/save")
    public String save(House house, HttpServletRequest request) {
        houseService.insert(house);
        return this.successPage(null, request);
    }

    @RequestMapping("/create")
    public String create(Model model) {
        //2.小区数据
        List<Community> communityList = communityService.findALl();
        //3.六个数据字段集合
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        List<Dict> floorList = dictService.findListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        List<Dict> directionList = dictService.findListByDictCode("direction");
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        model.addAttribute("communityList", communityList);

        model.addAttribute("houseTypeList", houseTypeList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("buildStructureList", buildStructureList);
        model.addAttribute("directionList", directionList);
        model.addAttribute("decorationList", decorationList);
        model.addAttribute("houseUseList", houseUseList);
        return PAGE_CREATE;
    }

    @RequestMapping
    public String index(Model model, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        //1.分页数据
        PageInfo<House> page = houseService.findPage(filters);
        //2.小区数据
        List<Community> communityList = communityService.findALl();
        //3.六个数据字段集合
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        List<Dict> floorList = dictService.findListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        List<Dict> directionList = dictService.findListByDictCode("direction");
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        //4.搜索数据用于回显

        model.addAttribute("page", page);

        model.addAttribute("communityList", communityList);

        model.addAttribute("houseTypeList", houseTypeList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("buildStructureList", buildStructureList);
        model.addAttribute("directionList", directionList);
        model.addAttribute("decorationList", decorationList);
        model.addAttribute("houseUseList", houseUseList);

        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }
}
