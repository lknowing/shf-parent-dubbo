package com.atguigu.base;

import com.github.pagehelper.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/17 15:54
 * @FileName: BaseController
 */
public class BaseController {
    private final static String PAGE_SUCCESS = "common/successPage";
    //提示信息
    public final static String MESSAGE_SUCCESS = "操作成功！";

    /**
     * 成功页
     *
     * @param message
     * @param request
     */
    protected String successPage(String message, HttpServletRequest request) {
        request.setAttribute("messagePage", StringUtil.isEmpty(message) ? MESSAGE_SUCCESS : message);
        return PAGE_SUCCESS;
    }

    /**
     * 封装页面提交的分页参数及搜索条件
     * 给子类用 使用protected 受保护的
     *
     * @param request
     * @return
     */
    protected Map<String, Object> getFilters(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 3);
        }
        return filters;
    }
}
