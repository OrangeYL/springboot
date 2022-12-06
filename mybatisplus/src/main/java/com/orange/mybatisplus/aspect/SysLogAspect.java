package com.orange.mybatisplus.aspect;

import com.orange.mybatisplus.anotation.SysLog;
import com.orange.mybatisplus.entity.SysLogEntity;
import com.orange.mybatisplus.service.SysLogService;
import com.orange.mybatisplus.utils.HttpContextUtils;
import com.orange.mybatisplus.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/6 10:59
 * @description: 日志切面类
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    //定义切点 切点表达式指向SysLog注解，我们在业务方法上可以加上SysLog注解，然后所标注
    //的方法都能进行日志记录
    @Pointcut("@annotation(com.orange.mybatisplus.anotation.SysLog)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时间
        long time = System.currentTimeMillis() - startTime;
        //保存日志
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint point,long time){
        MethodSignature signature =(MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog log = method.getAnnotation(SysLog.class);
        if(log != null){
            //注解上的描述
            sysLog.setOperation(log.value());
        }
        //请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = point.getArgs();
        String params = Arrays.toString(args);
        sysLog.setParams(params);

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //获取ip地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        //执行时间
        sysLog.setTime(time);

        sysLogService.save(sysLog);
    }
}
