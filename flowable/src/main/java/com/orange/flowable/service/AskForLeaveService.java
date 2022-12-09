package com.orange.flowable.service;

import com.orange.common.utils.Result;
import com.orange.flowable.entity.ApproveRejectVO;
import com.orange.flowable.entity.AskForLeaveVO;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 14:19
 * @description:
 */
public interface AskForLeaveService {

    /**
     * @description: 用户提交请假申请
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:10
     * @param: [askForLeaveVO]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> askForLeave(AskForLeaveVO askForLeaveVO);

    /**
     * @description: 展示所有用户提交的申请（经理查看）
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:29
     * @param: [identity] 用户的身份
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> leaveList(String identity);

    /**
     * @description: 审批请假申请
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:36
     * @param: [approveRejectVO]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> askForLeaveHandler(ApproveRejectVO approveRejectVO);

    /**
     * @description: 用户查询自己提交的记录（只查询流程结束的记录）
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:40
     * @param: [name]
     * @return: com.orange.common.utils.Result<?>
     **/
    public Result<?> searchResult(String name);
}
