package com.orange.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/8 14:40
 * @description:
 */
@SpringBootApplication
@EnableAsync
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class,args);
    }
}
