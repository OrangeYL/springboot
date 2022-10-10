package com.orange.easypoi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/1 11:01
 * @description:
 */
@Data
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer employerId;

    @Excel(name = "学校",width = 15)
    private String schoolName;

    @Excel(name = "学历", replace = {"初中及以下_1", "中专_2", "高中_3", "大专_4", "本科_5", "硕士_6", "博士_7"},width = 15)
    private Integer record;

    @Excel(name = "开始年份",format = "yyyy-MM-dd HH:mm:ss",width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @Excel(name = "毕业年份",format = "yyyy-MM-dd HH:mm:ss",width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    @Excel(name = "专业",width = 15)
    private String profession;

}
