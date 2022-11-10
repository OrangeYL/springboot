package com.orange.security.controller;

import com.orange.common.utils.Result;
import com.orange.security.entity.SysUser;
import com.orange.security.service.SysUserService;
import com.orange.security.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/8 14:07
 * @description:
 */
@RestController
@RequestMapping("/system")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 流程：
     *  访问接口之后，首先走到TokenAuthenticationFilter的doFilterInternal方法，
     *  检测到是登录接口，因此无需验证token，接着走到 UserDetailsServiceImpl，验证账号是否正确，
     *  如果不正确就抛出异常，否则进入TokenLoginFilter的attemptAuthentication方法进行登录认证，
     *  首先将用户信息（即账号密码）封装成Authentication，然后调用authenticate方法进行密码的验证，
     *  不正确则进入unsuccessfulAuthentication方法抛出异常，正确则进入successfulAuthentication，生成token
     *
     **/
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginVo loginVo){
       return sysUserService.login(loginVo);
    }

    /**
     *访问该接口需要token和对应权限
     * 在登录时，走到UserDetailsServiceImpl，返回的customUser中有对应用户的权限数据，在登录成功后把权限数据保存到redis中（successfulAuthentication方法）
     * 之后在访问其他接口时，走到TokenAuthenticationFilter的getAuthentication方法时，将权限从redis中取出来，封装成完整的Authentication。
     * 在SpringSecurity中，会使用默认的FilterSecurityInterceptor来进行权限校验。在FilterSecurityInterceptor中会从SecurityContextHolder获取其中的Authentication，
     * 然后获取其中的权限信息。判断当前用户是否拥有访问当前资源所需的权限。
     **/
    @GetMapping("/listAllUser")
    @PreAuthorize("hasAuthority('bnt.listAllUser')")
    public Result<?> listAllUser(){
        List<SysUser> list = sysUserService.list();
        return Result.success(list);
    }
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('bnt.list')")
    public Result<?> list(){
        List<SysUser> list = sysUserService.list();
        return Result.success(list);
    }
}
