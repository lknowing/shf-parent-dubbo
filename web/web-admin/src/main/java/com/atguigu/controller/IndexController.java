package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 15:31
 * @FileName: IndexController
 */
@Controller
@RequestMapping
public class IndexController {
    private static final String PAGE_INDEX = "frame/index";
    private static final String PAGE_MAIN = "frame/main";
    private static final String PAGE_LOGIN = "frame/login";

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    /**
     * 框架首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap model) {
        //准备两个数据返回前端页面
        //从session域中获取登录的用户信息 登录还没做 后续替换为当前登录用户id

        //Long adminId = 1L;//假设用户id为1为登录的人
        //Admin admin = adminService.findById(adminId);
        //通过SecurityContextHolder获取认证对象 （框架过滤器会将session域里的用户对象存放在当前线程上ThreadLocal）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//从线程上获取数据
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.getByUsername(user.getUsername());

        //左侧菜单树 自己通过双层for循环迭代生成的 当前集合只存放父节点 子节点通过父节点的children属性获取
//        Collection<GrantedAuthority> authorities = user.getAuthorities();
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList", permissionList);
        return PAGE_INDEX;
    }

    /**
     * 框架主页
     */
    @GetMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }

    @RequestMapping("/login")
    public String login() {
        return PAGE_LOGIN;
    }
}
