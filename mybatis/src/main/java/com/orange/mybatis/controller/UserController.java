package com.orange.mybatis.controller;

import com.orange.common.exception.BizException;
import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import com.orange.mybatis.entity.User;
import com.orange.mybatis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/6 11:03
 * @description:
 */
@RestController
@RequestMapping("/mybatis")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * @description: 插入数据
     * @author: Li ZhiCheng
     * @date: 2022/9/6 11:08
     * @param: [user]
     * @return: com.orange.springboot.utils.Result<com.orange.springboot.entity.User>
     **/
    @PostMapping
    public Result<User> insert(@RequestBody User user){
        userService.insert(user);
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * @description: 删除数据
     * @author: Li ZhiCheng
     * @date: 2022/9/6 11:18
     * @param: [id]
     * @return: com.orange.springboot.utils.Result<com.orange.springboot.entity.User>
     **/
    @DeleteMapping("/{id}")
    public Result<User> delete(@PathVariable Integer id){
        int i = userService.deleteByPrimaryKey(id);
        if(i<0){
            return Result.error("操作失败");
        }
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * @description: 修改数据
     * @author: Li ZhiCheng
     * @date: 2022/9/6 16:12
     * @param: [user]
     * @return: com.orange.springboot.utils.Result<com.orange.springboot.entity.User>
     **/
    @PutMapping
    public Result<User> update(@RequestBody User user){
        int i = userService.updateByPrimaryKey(user);
        if(i<0){
            throw new BizException(ResultEnum.UNKNOWN_ERROR.getCode(),"修改失败");
        }
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * @description: 查询数据
     * @author: Li ZhiCheng
     * @date: 2022/9/6 11:19
     * @param: [id]
     * @return: com.orange.springboot.utils.Result<com.orange.springboot.entity.User>
     **/
    @GetMapping("/{id}")
    public Result<User> query(@PathVariable Integer id){
        log.info("请求开始");
        User user = userService.selectByPrimaryKey(id);
        if(user==null){
            return Result.error("查不到该数据");
        }
        log.info("请求结束");
        return Result.success(user);
    }
}
