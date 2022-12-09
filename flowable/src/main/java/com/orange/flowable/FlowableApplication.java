package com.orange.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 14:04
 * @description: 项目启动的时候会自动在数据库中创建flowable对应的表和需要的数据
 */
@SpringBootApplication
public class FlowableApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class,args);
    }
}
