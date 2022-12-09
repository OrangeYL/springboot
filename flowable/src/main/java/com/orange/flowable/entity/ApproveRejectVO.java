package com.orange.flowable.entity;

import lombok.Data;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 14:52
 * @description: 审批实体类
 */
@Data
public class ApproveRejectVO {

    //任务id
    private String taskId;

    //是否批准 true 批准 false 拒绝
    private Boolean approve;

    //用户
    private String name;
}
