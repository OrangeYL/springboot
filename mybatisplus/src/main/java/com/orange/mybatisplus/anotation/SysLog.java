package com.orange.mybatisplus.anotation;

import java.lang.annotation.*;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/6 10:56
 * @description: 自定义日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
