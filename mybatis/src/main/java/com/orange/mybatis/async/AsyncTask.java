package com.orange.mybatis.async;

import com.orange.mybatis.entity.User;
import com.orange.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/30 11:04
 * @description: 异步任务
 */
@Component
@Slf4j
public class AsyncTask {

    @Autowired
    private UserService userService;

    @Async
    public Future<List<User>> getUserList(){
        log.info("================开始执行查询任务===============");
        List<User> users = userService.queryList();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("================结束查询任务===================");
        return new AsyncResult<>(users);
    }

    @Async
    public Future<List<User>> getList(){
        log.info("================开始执行查询任务===============");
        List<User> users = userService.queryList();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("================结束查询任务===================");
        return new AsyncResult<>(users);
    }
}
