package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
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
 * @Date 2022/06/21 09:36
 * @FileName: CommunityController
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String ACTION_LIST = "redirect:/community";

    @Reference
    CommunityService communityService;

    @Reference
    DictService dictService;

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        return ACTION_LIST;
    }

    @RequestMapping("/update")
    public String update(Community community, HttpServletRequest request) {
        communityService.update(community);
        return this.successPage(null, request);
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        Community community = communityService.findById(id);
        model.addAttribute("areaList", areaList);
        model.addAttribute("community", community);
        return PAGE_EDIT;
    }

    @RequestMapping("/save")
    public String save(Community community, HttpServletRequest request) {
        communityService.insert(community);
        return this.successPage(null, request);
    }


    @RequestMapping("/create")
    public String create(Model model) {
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        model.addAttribute("areaList", areaList);
        return PAGE_CREATE;
    }

    @RequestMapping
    public String index(HttpServletRequest request, Model model) {
        Map<String, Object> filters = getFilters(request);//获取请求参数
        if (!filters.containsKey("areaId")) {
            filters.put("areaId", "");
        }
        if (!filters.containsKey("plateId")) {
            filters.put("plateId", "");
        }
        //1.分页数据查询
        PageInfo<Community> page = communityService.findPage(filters);
        //2.获取下拉列选（区域）
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        //3.返回请求参数，回显
        model.addAttribute("areaList", areaList);
        model.addAttribute("page", page);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

}
