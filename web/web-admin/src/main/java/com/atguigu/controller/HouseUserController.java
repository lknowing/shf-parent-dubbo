package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/24 00:00
 * @FileName: HouseUserController
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {
    private static final String PAGE_CREATE = "houseUser/create";
    private static final String PAGE_EDIT = "houseUser/edit";
    private static final String LIST_ACTION = "redirect:/house/detail/";

    @Reference
    HouseUserService houseUserService;

    /**
     * 进入新增
     *
     * @param model
     * @param houseUser
     * @return
     */
    @GetMapping("/create")
    public String create(ModelMap model, HouseUser houseUser) {
        model.addAttribute("houseUser", houseUser);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     *
     * @param houseUser
     * @param request
     * @return
     */
    @PostMapping("/save")
    public String save(HouseUser houseUser, HttpServletRequest request) {
        //SysUser user = this.currentSysUser(request);
        houseUserService.insert(houseUser);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    /**
     * 编辑
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") Long id) {
        HouseUser houseUser = houseUserService.findById(id);
        model.addAttribute("houseUser", houseUser);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     *
     * @param id
     * @param houseUser
     * @param request
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable("id") Long id, HouseUser houseUser, HttpServletRequest request) {
        HouseUser currentHouseUser = houseUserService.findById(id);
        BeanUtils.copyProperties(houseUser, currentHouseUser);

        houseUserService.update(currentHouseUser);
        return this.successPage(MESSAGE_SUCCESS, request);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseUserService.delete(id);
        return LIST_ACTION + houseId;
    }

}
