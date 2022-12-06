package com.orange.mybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.common.exception.BizException;
import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import com.orange.mybatisplus.anotation.SysLog;
import com.orange.mybatisplus.entity.User;
import com.orange.mybatisplus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Li ZhiCheng
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/mybatisplus/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * @description: 插入数据
     * @author: Li ZhiCheng
     * @date: 2022/9/6 17:01
     * @param: [user]
     * @return: com.orange.springboot.utils.Result<com.orange.springboot.entity.User>
     **/
    @PostMapping
    @SysLog("插入数据")
    @Transactional(rollbackFor = Exception.class)
    public Result<User> insert(@RequestBody User user){
        iUserService.save(user);
        return Result.success(ResultEnum.SUCCESS);
    }
    @PostMapping("/batch")
    @SysLog("批量插入数据")
    @Transactional(rollbackFor = Exception.class)
    public Result<?> batchInsert(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("test1",15,"test1"));
        users.add(new User("test2",15,"test2"));
        users.add(new User("test3",15,"test3"));
        for(int i = 0;i<users.size();i++){
            User user = users.get(i);
            if(user.getName().equals("test2")){
               throw new BizException("操作错误");
            }
            iUserService.save(user);
        }
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * @description: 列表查询
     * @author: Li ZhiCheng
     * @date: 2022/9/6 17:04
     * @param: [user]
     * @return: com.orange.springboot.utils.Result<java.util.List<com.orange.springboot.entity.User>>
     **/
    @GetMapping
    @SysLog("列表查询")
    public Result<List<User>> listByCondition(@RequestBody(required = false) User user){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(user!=null){
            wrapper.like(StringUtils.isNotEmpty(user.getName()),"name",user.getName());
        }
        List<User> list = iUserService.list(wrapper);
        return Result.success(list);
    }
    /**
     * @description: 分页查询
     * @author: Li ZhiCheng
     * @date: 2022/9/6 17:06
     * @param: [pageNum, pageSize, name]
     * @return: com.orange.springboot.utils.Result<com.baomidou.mybatisplus.core.metadata.IPage<com.orange.springboot.entity.User>>
     **/
    @GetMapping("/page")
    @SysLog("分页查询")
    public Result<IPage<User>> page(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                    @RequestParam(value = "name",required = false) String name){
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotEmpty(name),"name",name);
        IPage<User> page1 = iUserService.page(page, userQueryWrapper);
        return Result.success(page1);
    }

}
