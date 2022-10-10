package com.orange.easypoi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/9 10:01
 * @description:
 */
@SpringBootApplication
@MapperScan("com.orange.easypoi.mapper")
public class EasypoiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasypoiApplication.class,args);
    }
}
