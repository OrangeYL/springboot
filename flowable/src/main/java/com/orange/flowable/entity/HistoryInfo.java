package com.orange.flowable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 15:05
 * @description: 申请详情实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryInfo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date endTime;

    private String name;

    private String reason;

    private boolean status;

    private Integer days;
}
