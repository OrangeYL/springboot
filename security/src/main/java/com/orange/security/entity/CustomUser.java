package com.orange.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/9 11:01
 * @description: 应该继承用户对象UserDetails，但是实际开发中我们的用户属性各种各样，UserDetails这些默认属性可能是满足不了，
 *               所以我们一般会自己实现该接口，然后设置好我们实际的用户实体对象。实现此接口要重写很多方法比较麻烦，
 *               我们可以继承Spring Security提供的`org.springframework.security.core.userdetails.User`类，该类实现了`UserDetails`接口帮我们省去了重写方法的工作：
 */
public class CustomUser extends User {

    //我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象
    private SysUser sysUser;

    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities){
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser=sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
