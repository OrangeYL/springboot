package com.orange.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/6 10:51
 * @description: 日志实体
 */
@Data
@TableName("sys_log")
public class SysLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    //用户名
    private String username;

    //用户操作
    private String operation;

    //请求方法
    private String method;

    //请求参数
    private String params;

    //执行时长(毫秒)
    private Long time;

    //IP地址
    private String ip;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
