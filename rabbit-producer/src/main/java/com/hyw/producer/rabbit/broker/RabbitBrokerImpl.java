package com.hyw.producer.rabbit.broker;

import com.alibaba.fastjson.JSON;
import com.hyw.producer.rabbit.constant.BrokerMessageConstant;
import com.hyw.producer.rabbit.constant.BrokerMessageStatusEnum;
import com.hyw.producer.rabbit.entity.BrokerMessage;
import com.hyw.producer.rabbit.service.MessageStoreService;
import com.hyw.rabbit.api.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import java.util.Date;
import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 11:15
 * Description：真正的发送不同类型的消息实现类
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    @Override
    public void rapidSend(Message message) {
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {

        BrokerMessage brokerMessage = messageStoreService.selectByMessageId(message.getMessageId());

        if (brokerMessage == null) {
            Date date = new Date();
            brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setMessage(JSON.toJSONString(message));
            brokerMessage.setStatus(BrokerMessageStatusEnum.SENDING.getStatus());
            //tryCount 在最开始发送的时候不需要进行设置
            brokerMessage.setNextRetry(DateUtils.addMinutes(date, BrokerMessageConstant.TIMEOUT));
            brokerMessage.setCreateTime(date);
            brokerMessage.setUpdateTime(date);
            messageStoreService.insert(brokerMessage);
        }

        sendKernel(message);
    }

    private void sendKernel(Message message) {
        AsyncBaseQueue.submit(() -> {
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();

            CorrelationData correlationData = new CorrelationData(String.format("%s#%s#%s",
                    message.getMessageId(),
                    System.currentTimeMillis(),
                    message.getMessageType()));

            RabbitTemplate template = rabbitTemplateContainer.getTemplate(message);
            template.convertAndSend(topic, routingKey, message, correlationData);
            log.info("# RabbitBrokerImpl.sendKernel # send to rabbitmq, messageId: {}", message.getMessageId());
        });

    }

    @Override
    public void sendMessages(List<Message> messages) {
        messages.forEach(message -> {
            AsyncBaseQueue.submit(() -> {
                String topic = message.getTopic();
                String routingKey = message.getRoutingKey();

                CorrelationData correlationData = new CorrelationData(String.format("%s#%s#%s",
                        message.getMessageId(),
                        System.currentTimeMillis(),
                        message.getMessageType()));

                RabbitTemplate template = rabbitTemplateContainer.getTemplate(message);
                template.convertAndSend(topic, routingKey, message, correlationData);
                log.info("# RabbitBrokerImpl.sendKernel # send to rabbitmq, messageId: {}", message.getMessageId());
            });
        });
    }


}
