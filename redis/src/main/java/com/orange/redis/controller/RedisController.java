package com.orange.redis.controller;

import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import com.orange.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/7 11:12
 * @description: 测试Redis操作
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;
    /**
     * @description: 测试redis插入键值
     * @author: Li ZhiCheng
     * @date: 2022/9/7 11:21
     * @param: [key, value]
     * @return: com.orange.springboot.utils.Result
     **/
    @PostMapping
    public Result save(String key,String value){
        redisUtil.set(key,value);
        return Result.success(ResultEnum.SUCCESS);
    }
}
