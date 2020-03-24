package com.hyw.producer.rabbit.task;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.hyw.producer.rabbit.broker.RabbitBroker;
import com.hyw.producer.rabbit.constant.BrokerMessageStatusEnum;
import com.hyw.producer.rabbit.entity.BrokerMessage;
import com.hyw.producer.rabbit.service.MessageStoreService;
import com.hyw.rabbit.api.Message;
import com.hyw.task.rabbit.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/23 22:12
 * Description：可靠性投递消息补偿任务
 */
@Component
@ElasticJobConfig(
        name = "com.hyw.producer.rabbit.task.RetryMessageDataflowJob",
        cron= "0/10 * * * * ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1
)
@Slf4j
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private RabbitBroker rabbitBroker;

    private static final int MAX_RETRY_COUNT = 3;

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatusEnum.SENDING);
        log.info("--------@@@@@ 抓取数据集合, 数量：	{} 	@@@@@@-----------" , list.size());
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> dataList) {

        dataList.forEach( brokerMessage -> {

            String messageId = brokerMessage.getMessageId();
            if(brokerMessage.getTryCount() >= MAX_RETRY_COUNT) {
                this.messageStoreService.failure(messageId);
                log.warn(" -----消息设置为最终失败，消息ID: {} -------", messageId);
            } else {
                //	每次重发的时候要更新一下try count字段
                this.messageStoreService.updateTryCount(messageId);
                // 	重发消息
                this.rabbitBroker.reliantSend(JSON.parseObject(brokerMessage.getMessage(), Message.class));
            }

        });

    }
}
