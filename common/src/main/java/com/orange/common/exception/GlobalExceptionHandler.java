package com.orange.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/8 13:58
 * @description: 全局异常捕获
 */
@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    //自定义异常 方法抛出BizException异常会被此方法捕获处理
    @ExceptionHandler({BizException.class})
    public Result<?> bizExceptionHandler(HttpServletRequest request, BizException e) {
        Result<?> result = Result.error(e.getCode() == null ? ResultEnum.UNKNOWN_ERROR.getCode() : e.getCode(), e.getMessage());
        return this.printLogAndReturn(request, result, e);
    }

    private Result<?> printLogAndReturn(HttpServletRequest request, Result<?> result, Exception e) {
        ObjectMapper mapper = new ObjectMapper();

        String requestUrl = request.getRequestURL().toString() + (!StringUtils.hasLength(request.getQueryString()) ? "" : "?" + request.getQueryString());
        try {
            log.error("<-异常返回-> 请求接口:{} | 异常时间:{} | 异常结果:{}", new Object[]{requestUrl, System.currentTimeMillis(), mapper.writeValueAsString(result)});

        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        log.error("<--异常堆栈信息-->");
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        log.error(stringWriter.toString());
        return result;
    }
}
