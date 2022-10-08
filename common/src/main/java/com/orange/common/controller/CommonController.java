package com.orange.common.controller;

import com.orange.common.exception.BizException;
import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/8 13:40
 * @description: 测试controller
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping("/testNoData")
    public Result testNoData(){
        return Result.success();
    }
    @RequestMapping("/testData")
    public Result testData(){
        log.info("请求开始");
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        log.info("请求结束");
        return Result.success(list);
    }
    @RequestMapping("/testError")
    public Result testError(){
        return Result.error("操作错误");
    }
    @RequestMapping("/testErrorWithCode")
    public Result testErrorWithCode(){
        return Result.error(ResultEnum.DATA_IS_NULL);
    }
    @RequestMapping("/exception")
    public Result testException(){
        throw new BizException(-1000,"错误");
    }
}
