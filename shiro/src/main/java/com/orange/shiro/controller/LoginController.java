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
        String token = JwtUtils.sign(username, secret);
        redisTemplate.opsForValue().set(token,token, expireTime*2/100, TimeUnit.SECONDS);
        return Result.success(token);
    }
    //该接口被拦截，需要验证token
    @RequestMapping("/test")
    public Result<?> testToken(){
        log.info("检查是否携带token");
        return Result.success(ResultEnum.SUCCESS);
    }
    //该接口被拦截，需要验证token，并且需要“user:admin”权限
    @RequiresPermissions("user:admin")
    @RequestMapping("/permission")
    public Result<?> testPermission(){
        log.info("检查是否有权限");
        return Result.success(ResultEnum.SUCCESS);
    }
}
