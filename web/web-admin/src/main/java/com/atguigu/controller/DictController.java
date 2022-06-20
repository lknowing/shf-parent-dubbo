package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/20 15:43
 * @FileName: DictController
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {
    private static final String PAGE_DICT = "dict/index";

    @Reference
    DictService dictService;

    //传递的是节点的id，作为外键(parenId)来使用
    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id", required = false, defaultValue = "0") Long parentId) {//通过节点id的值，获取子节点集合返回
        //每一个树节点都是一个JSON对象，用map集合来表示JSON
        //[ id:1, pId:0, name:"全部分类", isParent:true]
        List<Map<String, Object>> data = dictService.findZnodesByParentId(parentId);
        return Result.ok(data);//将数据封装到Result对象上，返回JSON串 @ResponseBody
    }

    @RequestMapping
    public String index() {
        return PAGE_DICT;
    }

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    @GetMapping("/findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @GetMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

}
