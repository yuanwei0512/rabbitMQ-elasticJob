package com.hyw.producer.rabbit;

import com.hyw.task.rabbit.annotation.EnableElasticJob;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/22 21:08
 * Description：TODO
 */
@EnableElasticJob
@SpringBootApplication
@ComponentScan(basePackages = {"com.hyw.*"})
@MapperScan(basePackages="com.hyw.producer.rabbit.mapper")
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }

}
