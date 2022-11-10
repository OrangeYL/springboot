package com.orange.shiro.controller;

import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import com.orange.shiro.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/21 14:33
 * @description:
 */
@RestController
@RequestMapping("/shiro")
@Slf4j
public class LoginController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${custom.jwt.expire_time}")
    private long expireTime;

    //该接口不会被拦截
    @RequestMapping("/login")
    public Result<?> login(@RequestParam("username") String username, @RequestParam("secret") String secret){
        //模拟数据库校验
        if(!"admin".equals(username)){
            return Result.error("用户不存在");
        }
        if(!"123".equals(secret)){
            return Result.error("密码错误");
        }
        String token = JwtUtils.sign(username, secret);
        redisTemplate.opsForValue().set("token",token, expireTime*2/100, TimeUnit.SECONDS);
        return Result.success(token);
    }
    /**
     该接口被拦截，需要验证token
     流程：访问该接口后，会进入JwtFilter的isAccessAllowed方法判断有没有携带token,如果没有就抛出异常
          否则进入executeLogin方法，在这个方法里面调用getSubject方法进行shiro认证，即跳转到ShiroRealm
          的doGetAuthenticationInfo(AuthenticationToken authenticationToken)方法，认证成功就返回
          否则抛出异常。
     **/
    @RequestMapping("/test")
    public Result<?> testToken(){
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     该接口被拦截，需要验证token，并且需要“user:admin”权限
     流程：访问该接口后，会进入JwtFilter的isAccessAllowed方法判断有没有携带token,如果没有就抛出异常
          否则进入executeLogin方法，在这个方法里面调用getSubject方法进行shiro认证，即跳转到ShiroRealm
          的doGetAuthenticationInfo(AuthenticationToken authenticationToken)方法，认证失败就抛出异常
          否则进入ShiroRealm的doGetAuthorizationInfo(PrincipalCollection principalCollection)方法进行
          授权认证，成功就返回，否则抛出异常。
     **/
    @RequiresPermissions("user:admin")
    @RequestMapping("/permission")
    public Result<?> testPermission(){
        return Result.success(ResultEnum.SUCCESS);
    }
}
