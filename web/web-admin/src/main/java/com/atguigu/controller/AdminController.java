package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.atguigu.service.AdminService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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
    private final static String PAGE_UPLOAD_SHOW = "admin/upload";
    private static final String PAGE_ASSIGN_ROLE = "admin/assignRole";

    @Reference
    AdminService adminService;

    @Reference
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 根据用户分配角色
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")//框架自动分解 roleIds 字符串
    public String assignRole(@RequestParam("adminId") Long adminId, @RequestParam("roleIds") Long[] roleIds, HttpServletRequest request) {
        roleService.saveUserRoleRelationShip(adminId, roleIds);
        return this.successPage(null, request);
    }

    /**
     * @param id 用户表主键
     * @return 分配角色页面 准备两个下拉列选
     */
    @RequestMapping("/assignRole/{id}")
    public String assignRole(@PathVariable("id") Long id, Map map) {
        Map assginMap = roleService.findRoleByAdminId(id);
        map.putAll(assginMap);
        map.put("adminId", id);
        return PAGE_ASSIGN_ROLE;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id,
                         @RequestParam(value = "file") MultipartFile file,
                         HttpServletRequest request) throws IOException {
        String newFileName = UUID.randomUUID().toString();
        // 上传图片
        QiniuUtils.upload2Qiniu(file.getBytes(), newFileName);
        String url = "http://rdv5dnxaq.hn-bkt.clouddn.com/" + newFileName;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);
        adminService.update(admin);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(ModelMap model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOAD_SHOW;
    }

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
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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
    @PreAuthorize("hasAuthority('admin.show')")
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
