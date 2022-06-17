package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:43
 * @FileName: AdminController
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final String PAGE_INDEX = "admin/index";
    private static final String PAGE_CREATE = "admin/create";
    private static final String ACTION_LIST = "redirect:/admin";
    private static final String PAGE_EDIT = "admin/edit";

    @Autowired
    AdminService adminService;

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return ACTION_LIST;
    }

    @PostMapping("/update")
    public String update(Admin admin, Model model, HttpServletRequest request) {
        adminService.update(admin);
        return successPage("修改成功！", request);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Admin admin = adminService.findById(id);
        model.addAttribute("admin", admin);
        return PAGE_EDIT;
    }

    @PostMapping("/save")
    public String save(Admin admin, Model model, HttpServletRequest request) {
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        adminService.insert(admin);
        //return ACTION_LIST;
        return successPage("保存成功！", request);
    }

    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    /**
     * 分页查询
     * 根据分页条件进行查询
     * adminName=''
     * pageNum=1 隐藏域
     * pageSize=10 隐藏域
     *
     * @param model
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest request, Model model) {
        Map<String, Object> filters = getFilters(request);
        //分页对象 包含集合对象 pageNum pageSize pages total等等
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        model.addAttribute("page", pageInfo);
        //数据回显 页面提交的分页参数及搜索条件
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

}
