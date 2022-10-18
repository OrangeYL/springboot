package com.orange.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/10 16:34
 * @description:
 */
@SpringBootApplication
@MapperScan("com.orange.quartz.mapper")
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class,args);
    }
}
