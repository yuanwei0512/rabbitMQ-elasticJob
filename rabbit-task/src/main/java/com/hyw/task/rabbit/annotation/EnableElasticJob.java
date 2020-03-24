package com.hyw.task.rabbit.annotation;

import com.hyw.task.rabbit.autoconfig.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/23 10:46
 * Description：启用分布式定时任务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JobParserAutoConfiguration.class)
@Inherited
public @interface EnableElasticJob {
}
