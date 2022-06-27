package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * title:
 *
 * @Author xu
 * @Date 2022/06/27 22:47
 * @FileName: UserDetailsServiceImpl
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username----->" + username);
        Admin admin = adminService.getByUsername(username);
        if (null == admin) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //用户功能权限
        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String code : codeList) {
            if (StringUtils.isEmpty(code)) {
                continue;
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(code);
            authorities.add(authority);
        }
        return new User(username, admin.getPassword(), authorities);

        //权限集合暂时设置为空 无任何权限
/*        return new User(username, admin.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
    }
}
