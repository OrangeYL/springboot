package com.orange.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/8 15:27
 * @description:
 */
@SpringBootApplication
@MapperScan("com.orange.mybatisplus.mapper")
public class MybatisPlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class,args);
    }
}
