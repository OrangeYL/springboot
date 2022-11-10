package com.orange.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orange.common.exception.BizException;
import com.orange.security.entity.CustomUser;
import com.orange.security.entity.SysUser;
import com.orange.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/9 11:06
 * @description: 业务对象UserDetailsService 我们实现该接口，就完成了自己的业务
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查找用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if(null == sysUser){
            throw new BizException("用户名不存在!");
        }
        if(sysUser.getStatus() == 0) {
            throw new BizException("账号已停用!");
        }
        //存放用户权限
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //模拟一个权限,正常应该是从数据库查询对应用户的权限
        authorities.add(new SimpleGrantedAuthority("bnt.list"));
        return new CustomUser(sysUser, authorities);
    }
}
