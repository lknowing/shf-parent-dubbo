package com.atguigu.controller;

import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/15 14:43
 * @FileName: RoleController
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    private static final String PAGE_INDEX = "role/index";
    private static final String PAGE_CREATE = "role/create";
    private static final String ACTION_LIST = "redirect:/role";
    private static final String PAGE_SUCCESS = "common/successPage";
    @Autowired
    RoleService roleService;

    @PostMapping("/save")
    public String save(Role role, Model model) {
        roleService.insert(role);
        model.addAttribute("messagePage", "操作成功！");
        //return ACTION_LIST;
        return PAGE_SUCCESS;
    }

    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    /**
     * 列表
     *
     * @param model
     * @return
     */
    @RequestMapping
    public String index(Model model) {
        List<Role> list = roleService.findAll();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }

}
