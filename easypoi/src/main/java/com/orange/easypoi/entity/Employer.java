package com.orange.easypoi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/1 11:00
 * @description:
 */
@Data
public class Employer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @Excel(name = "姓名",width = 15)
    private String name;

    @Excel(name = "性别",replace = {"男_0", "女_1"},width = 15)
    private Integer gender;

    @Excel(name = "手机号",width = 25)
    private String phone;

    @Excel(name = "开始工作时间",format = "yyyy-MM-dd HH:mm:ss",width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date workTime;

    @Excel(name = "民族",width = 15)
    private String national;

    @Excel(name = "语言水平",width = 15)
    private String languageProficiency;

    @Excel(name = "出生日期",format = "yyyy-MM-dd HH:mm:ss",width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birth;

    @Excel(name = "职位",width = 15)
    private String jobsName;

    @Excel(name = "职位类型",width = 15)
    private String categoryName;

    @Excel(name = "薪资",replace = {"3K以下_1", "3K-5K_2", "5K-10K_3", "10K-20K_4", "20K-50K_5", "50K以上_6"},width = 15)
    private Integer salary;

    @Excel(name = "工作地点",width = 15)
    private String workArea;

    @ExcelCollection(name = "教育经历")
    private List<Education> educationList;


}
