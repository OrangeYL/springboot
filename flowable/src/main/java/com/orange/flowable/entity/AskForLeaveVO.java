package com.orange.flowable.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 14:15
 * @description: 请假实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskForLeaveVO {

    //用户名
    private String name;

    //请假天数
    private Integer days;

    //请假原因
    private String reason;
}
