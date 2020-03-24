package com.hyw.producer.rabbit.autoconfigure;

import com.hyw.task.rabbit.annotation.EnableElasticJob;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 10:45
 * Description：RabbitMQ发送端自动装配
 */
@EnableElasticJob
@Configuration
@ComponentScan({"com.hyw.producer.rabbit.*"})
public class RabbitProducerAutoConfiguration {

}
