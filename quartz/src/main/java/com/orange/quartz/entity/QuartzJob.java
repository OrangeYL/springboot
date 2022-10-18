package com.orange.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/17 19:16
 * @description: 任务实体类，保存任务相关信息
 */
@Data
@TableName("quartz_job")
public class QuartzJob implements Serializable {

    private static final long serialVersionUID = 1L;
    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**任务类名*/
    private String jobClassName;
    /**cron表达式*/
    private String cronExpression;
    /**描述*/
    private String description;
    /**状态 0正常 -1停止*/
    private Integer status;
}
