package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session!=null){
            session.removeAttribute("USER");
//            session.invalidate();
        }
        return Result.ok();
    }

    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //校验账号
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //校验密码
        if (!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //校验是否被禁用
        if (userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        request.getSession().setAttribute("USER", userInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }

    @RequestMapping("/getCode/{phone}")
    public Result getCode(@PathVariable("phone") String phone, HttpServletRequest request) {
        //模拟发送手机验证码 后续电商系统正式使用
        String code = "1234";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }

    //注意：ajax提交 post 请求 ，通过请求体获取json数据，转换为vo对象。
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request) {
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //1.数据校验（数据合法性）
        if (StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //2.手机验证码是否有效（校验码5分钟过期）
        //3.手机验证码是否一致
        String currentCode = (String) request.getSession().getAttribute("CODE");
        if (currentCode == null || !code.equals(currentCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        //4.手机号码是否被占用(表中字段unique)
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (null != userInfo) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //5.封装数据，保存数据（密码加密存储）
        userInfo = new UserInfo();
        //userInfo.setNickName(nickName);
        //userInfo.setPhone(phone);
        BeanUtils.copyProperties(registerVo, userInfo);
        //MD5加密特点：加密后长度不变，16个字节--->转换为32个字符存储，每个字符是个16进制数
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }
}
