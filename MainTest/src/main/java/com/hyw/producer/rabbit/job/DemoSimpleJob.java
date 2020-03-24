package com.hyw.producer.rabbit.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.hyw.task.rabbit.annotation.ElasticJobConfig;
import org.springframework.stereotype.Component;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/23 21:26
 * Description：TODO
 */
/*@Component
@ElasticJobConfig(
        name = "com.hyw.producer.rabbit.job.DemoSimpleJob",
        cron = "0/5 * * * * ?",
        description = "demo定时任务",
        overwrite = true,
        shardingTotalCount = 5
)*/
public class DemoSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("demojob");
    }
}
