package com.orange.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/6 10:52
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer age;

    private String address;

    private Date createTime;

    private Date updateTime;
}
