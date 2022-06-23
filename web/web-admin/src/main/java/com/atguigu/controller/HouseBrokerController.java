package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/22 09:42
 * @FileName: HouseBrokerController
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {
    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String LIST_ACTION = "redirect:/house/detail/";

    @Reference
    HouseBrokerService houseBrokerService;

    @Reference
    AdminService adminService;

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseBrokerService.delete(id);
        return LIST_ACTION + houseId;
    }

    @RequestMapping("/update/{id}")
    /*                              houseBroker.id       brokerId-->admin.id*/
    public String update(@PathVariable("id") Long id, HouseBroker houseBroker, HttpServletRequest request) {
        //修改经纪人，名称和头像也需要更新
        Long adminId = houseBroker.getBrokerId();//获取houseBroker里的经纪人id，即admin里的主键id
        Admin admin = adminService.findById(adminId);//获取admin表的人员对象

        HouseBroker currentHouseBroker = houseBrokerService.findById(id);//获取当前未修改的经纪人对象
        //BeanUtils.copyProperties(houseBroker, currentHouseBroker);//只要属性名称一致，将前一个bean对象属性拷贝到后一个对象中
        currentHouseBroker.setBrokerId(adminId);//将接收表单的数据一个一个拷贝到当前对象中，修改经纪人对象，比较麻烦，可以采用工具方法批量拷贝。

        currentHouseBroker.setBrokerName(admin.getName());
        currentHouseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(currentHouseBroker);

        return this.successPage(null, request);
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        HouseBroker houseBroker = houseBrokerService.findById(id);
        List<Admin> adminList = adminService.findAll();

        model.addAttribute("houseBroker", houseBroker);
        model.addAttribute("adminList", adminList);
        return PAGE_EDIT;
    }

    @RequestMapping("/save")
    public String save(HouseBroker houseBroker, HttpServletRequest request) {
        Long adminId = houseBroker.getBrokerId();
        Admin admin = adminService.findById(adminId);
        //给冗余字段赋值，值来自Admin对象
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return this.successPage(null, request);
    }

    @RequestMapping("/create")
//    public String create(Long houseId){}
    public String create(HouseBroker houseBroker, Model model) {
        List<Admin> adminList = adminService.findAll();

        model.addAttribute("houseBroker", houseBroker);
        model.addAttribute("adminList", adminList);
        return PAGE_CREATE;
    }


}
