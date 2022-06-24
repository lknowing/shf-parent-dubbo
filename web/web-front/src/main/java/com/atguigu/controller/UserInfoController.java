package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/25 00:59
 * @FileName: UserInfoController
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    UserInfoService userInfoService;

    @RequestMapping("/getCode/{phone}")
    public Result getCode(@PathVariable("phone") String phone, HttpServletRequest request) {
        //模拟发送手机验证码 后续电商系统正式使用
        String code = "1234";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request) {
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //验证码
        String currentCode = (String) request.getSession().getAttribute("CODE");
        if (currentCode == null || !code.equals(currentCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        //验证手机号是否注册
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (null != userInfo) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        userInfo = new UserInfo();
        //userInfo.setNickName(nickName);
        //userInfo.setPhone(phone);
        BeanUtils.copyProperties(registerVo, userInfo);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }
}