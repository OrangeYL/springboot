package com.orange.common.exception;

import com.orange.common.utils.ResultEnum;
import lombok.Data;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/8 13:55
 * @description: 自定义异常
 */
@Data
public class BizException extends RuntimeException{
    //状态码
    private Integer code;

    public BizException(){
    }

    public BizException(String msg){
        super(msg);
    }
    public BizException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
    public BizException(ResultEnum resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(ResultEnum resultCode, Throwable cause) {
        super(resultCode.getMsg(), cause);
        this.code = resultCode.getCode();
    }
}
