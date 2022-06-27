package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.atguigu.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private final static String PAGE_ASSGIN_SHOW = "role/assignShow";

    @Reference
    RoleService roleService;

    @Reference
    PermissionService permissionService;

    /**
     * 给角色分配权限
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("/assignPermission")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String assignPermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIds") Long[] permissionIds, HttpServletRequest request) {
        //操作中间表数据，通过多对多两端任意服务接口都可以，中间表不提供业务接口
        permissionService.saveRolePermissionRelationShip(roleId, permissionIds);
        return this.successPage(null, request);
    }

    /**
     * 进入分配权限页面
     *
     * @param roleId
     * @return
     */
    @GetMapping("/assignShow/{roleId}")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String assignShow(ModelMap model, @PathVariable Long roleId) {
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        model.addAttribute("zNodes", JSON.toJSONString(zNodes));//数据序列化成字符串
        model.addAttribute("roleId", roleId);
        return PAGE_ASSGIN_SHOW;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('role.delete')")
    public String delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return ACTION_LIST;
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('role.edit')")
    public String update(Role role, Model model, HttpServletRequest request) {
        roleService.update(role);
        return successPage("修改成功！", request);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('role.edit')")
    public String edit(@PathVariable("id") Long id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return PAGE_EDIT;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('role.create')")
    public String save(Role role, Model model, HttpServletRequest request) {
        roleService.insert(role);
        //return ACTION_LIST;
        return successPage("保存成功！", request);
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('role.create')")
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
    @PreAuthorize("hasAuthority('role.show')")//设置权限控制注解 访问此方法之前校验用户权限
    public String index(HttpServletRequest request, Model model) {
        Map<String, Object> filters = getFilters(request);
        //分页对象 包含集合对象 pageNum pageSize pages total等等
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        model.addAttribute("page", pageInfo);
        //数据回显 页面提交的分页参数及搜索条件
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

}
