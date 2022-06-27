package com.atguigu.interceptor;

import com.alibaba.fastjson.JSON;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/26 21:50
 * @FileName: LoginInterceptor
 */

/**
 * 前端登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        if (null == userInfo) {
            Result result = Result.build("未登录，请先登录！", ResultCodeEnum.LOGIN_AUTH);
            //String resultStr = JSON.toJSONString(result);
            //response.getWriter().print(resultStr);
            WebUtil.writeJSON(response, result);//将数据转换为JSON返回给客户端浏览器
            return false;
        }
        return true;
    }

}
