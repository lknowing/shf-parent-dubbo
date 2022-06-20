package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private static final String PAGE_FRAME = "frame/index";
    private static final String PAGE_MAIN = "frame/main";

    /**
     * 框架首页
     */
    @GetMapping("/")
    public String index() {
        return PAGE_FRAME;
    }

    /**
     * 框架主页
     */
    @GetMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }

}
