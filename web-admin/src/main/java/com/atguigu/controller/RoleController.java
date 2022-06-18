package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
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
 * @FileName: RoleController
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final String PAGE_INDEX = "role/index";
    private static final String PAGE_CREATE = "role/create";
    private static final String ACTION_LIST = "redirect:/role";
    private static final String PAGE_EDIT = "role/edit";

    @Autowired
    RoleService roleService;

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return ACTION_LIST;
    }

    @PostMapping("/update")
    public String update(Role role, Model model,HttpServletRequest request) {
        roleService.update(role);
        return successPage("修改成功！", request);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return PAGE_EDIT;
    }

    @PostMapping("/save")
    public String save(Role role, Model model,HttpServletRequest request) {
        roleService.insert(role);
        //return ACTION_LIST;
        return successPage("保存成功！", request);
    }

    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }

    /*@RequestMapping
    public String index(Model model) {
        List<Role> list = roleService.findAll();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }*/

    /**
     * 分页查询
     * 根据分页条件进行查询
     * roleName=''
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
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        model.addAttribute("page",pageInfo);
        //数据回显 页面提交的分页参数及搜索条件
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }

}
