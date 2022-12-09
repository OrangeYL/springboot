package com.orange.flowable.controller;

import com.orange.common.utils.Result;
import com.orange.flowable.entity.ApproveRejectVO;
import com.orange.flowable.entity.AskForLeaveVO;
import com.orange.flowable.service.AskForLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 14:18
 * @description:
 */
@RestController
@RequestMapping("/flowable")
public class AskForLeaveController {

    @Autowired
    AskForLeaveService askForLeaveService;

    /**
     * @description: 用户提交请假申请
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:10
     * @param: [askForLeaveVO]
     * @return: com.orange.common.utils.Result<?>
     **/
    @PostMapping("/submit")
    public Result<?> askForLeave(@RequestBody AskForLeaveVO askForLeaveVO){
        return askForLeaveService.askForLeave(askForLeaveVO);
    }

    /**
     * @description: 展示所有用户提交的申请（经理查看）
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:29
     * @param: [identity] 用户的身份
     * @return: com.orange.common.utils.Result<?>
     **/
    @GetMapping("/list")
    public Result<?> leaveList(String identity){
        return askForLeaveService.leaveList(identity);
    }

    /**
     * @description: 审批请假申请
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:36
     * @param: [approveRejectVO]
     * @return: com.orange.common.utils.Result<?>
     **/
    @PostMapping("/handler")
    public Result<?> askForLeaveHandler(@RequestBody ApproveRejectVO approveRejectVO){
        return askForLeaveService.askForLeaveHandler(approveRejectVO);
    }

    /**
     * @description: 用户查询自己提交的记录（只查询流程结束的记录）
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:40
     * @param: [name]
     * @return: com.orange.common.utils.Result<?>
     **/
    @GetMapping("/search")
    public Result<?> searchResult(String name) {
        return askForLeaveService.searchResult(name);
    }
}
