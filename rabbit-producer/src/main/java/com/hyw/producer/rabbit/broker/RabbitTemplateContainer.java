package com.hyw.producer.rabbit.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.hyw.producer.rabbit.constant.BrokerMessageStatusEnum;
import com.hyw.producer.rabbit.service.MessageStoreService;
import com.hyw.rabbit.api.Message;
import com.hyw.rabbit.api.MessageType;
import com.hyw.rabbit.api.exception.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 21:19
 * Description：RabbitTemplateContainer池化封装
 *              每一个topic 对应一个RabbitTemplate
 *              1.	提高发送的效率
 *              2. 	可以根据不同的需求制定化不同的RabbitTemplate, 比如每一个topic 都有自己的routingKey规则
 */
@Component
@Slf4j
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageStoreService messageStoreService;

    private final Map<String, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

    private final Splitter splitter = Splitter.on("#");

    /**  读写锁 */
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    /**
     * @param message 消息
     * @return 获取模板
     */
    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {

        Preconditions.checkNotNull(message.getTopic());
        //从map中获取template
        String topic = message.getTopic();
        // 加读锁
        r.lock();
        try {
            RabbitTemplate template = rabbitMap.get(topic);
            if (template != null) {
                return template;
            }
        } finally {
            r.unlock();
        }

        // 加写锁
        w.lock();
        try {
            //再次判断是否存在
            RabbitTemplate template = rabbitMap.get(topic);
            if (template != null) {
                return template;
            }

            log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exist, create on", topic);
            //当map中不存在时创建
            RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
            newTemplate.setExchange(topic);
            newTemplate.setRoutingKey(message.getRoutingKey());
            newTemplate.setRetryTemplate(new RetryTemplate());
            //当消息不为迅速消失时， 添加确认机制
            if (!message.getMessageType().equals(MessageType.RAPID.getType())) {
                newTemplate.setConfirmCallback(this);
            }
            rabbitMap.put(topic, newTemplate);
            return newTemplate;
        } finally {
            w.unlock();
        }

    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {

        List<String> strings = splitter.splitToList(correlationData.getId());
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        Integer messageType = Integer.valueOf(strings.get(2));
        if (ack) {
            if (MessageType.of(messageType) == MessageType.RELIANT) {
                this.messageStoreService.succuess(messageId);
            }
            log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        }

    }
}
