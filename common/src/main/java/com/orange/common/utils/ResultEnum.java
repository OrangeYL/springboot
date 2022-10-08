package com.orange.common.utils;


/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/8 10:55
 * @description: 枚举常见错误
 */
public enum ResultEnum {

    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(200,"操作成功"),
    DATA_IS_NULL(-100,"数据为空");

    //状态码
    private Integer code;
    //返回信息
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
