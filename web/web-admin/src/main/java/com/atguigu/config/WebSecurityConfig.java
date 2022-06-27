package com.atguigu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 22:06
 * @FileName: WebSecurityConfig
 */
@Configuration //声明一个配置 相当于一个xml配置文件
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
//开启基于方法级别细粒度权限控制。即:在controller方法上加权限注解即可：例如：@PreAuthorize("hasAuthority('role.show')")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 认证
     * 基于内存的认证方式 了解
     * 基于数据库的认证方式 重点
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存的认证方式 写死用户名和密码 分配空的角色 无任何权限
        /*auth.inMemoryAuthentication()
                .withUser("lucy")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("");*/
        //基于数据库的认证
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 做授权的 自定义授权操作
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //必须调用父类的方法，否则就不需要认证即可访问
        //默认的授权：不登录系统 所有资源都不允许访问
        //super.configure(http);

        //自定义权限
        //1.允许iframe显示 同源允许访问
        http.headers().frameOptions().sameOrigin();//同源（资源父路径一致的 协议 ip 端口port一致的） 资源允许访问

        //登录设置
        http.authorizeRequests()
                .antMatchers("/static/**", "/login").permitAll()  //允许匿名用户访问的路径
                .anyRequest().authenticated()    // 其它页面全部需要验证
                .and()
                .formLogin()
                .loginPage("/login")    //用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
                .defaultSuccessUrl("/") //登录认证成功后默认转跳的路径
                .and()
                .logout()
                .logoutUrl("/logout")   //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .logoutSuccessUrl("/login");//用户退出后要被重定向的url
        http.csrf().disable();//关闭跨域请求伪造功能 默认开启 认证时不光需要用户名和密码正确 还要提供_csrf令牌值（服务器给网页自动生成的 防止跨站请求伪造）
    }

    /**
     * 必须指定加密方式，上下加密方式要一致
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
