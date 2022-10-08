package com.orange.mybatisplus.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/6 16:29
 * @description: mybatis plus工具类-逆向工程
 */
public class MybatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/orange?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2b8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("Li ZhiCheng") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir")+"/mybatisplus"+"/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.orange") // 设置父包名
                            .moduleName("mybatisplus") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") +"/mybatisplus"+"/src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            // 设置自动填充的时间字段
                            .entityBuilder().addTableFills(
                            new Column("create_time", FieldFill.INSERT),new Column("update_time", FieldFill.INSERT_UPDATE))
                    ; // 设置过滤表前缀

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
