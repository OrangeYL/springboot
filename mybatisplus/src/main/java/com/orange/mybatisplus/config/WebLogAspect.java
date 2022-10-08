package com.orange.mybatisplus.config;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/8 15:05
 * @description: 声明一个切面类，打印日志
 */
@Component
@Aspect
public class WebLogAspect {

    private final static Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    //声明切入点 controller包下所有的方法
    @Pointcut("execution(* com.orange.mybatisplus.controller..*.*(..))")
    public void webLog(){}

    /**
     * @description: 在切点之前织入代码 打印请求日志
     * @author: Li ZhiCheng
     * @date: 2022/9/8 15:17
     * @param: [joinPoint]
     * @return: void
     **/
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
    }

    /**
     * @description: 在切点之后织入
     * @author: Li ZhiCheng
     * @date: 2022/9/8 15:18
     * @param: []
     * @return: void
     **/
    @After("webLog()")
    public void doAfter() throws Throwable {
        log.info("=========================================== End ===========================================");
        // 每个请求之间空一行
        log.info("");
    }

    /**
     * @description: 在切点前后织入，打印请求时间
     * @author: Li ZhiCheng
     * @date: 2022/9/8 15:19
     * @param: [proceedingJoinPoint]
     * @return: java.lang.Object
     **/
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        log.info("Response Args  : {}", new Gson().toJson(result));
        // 执行耗时
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

}
