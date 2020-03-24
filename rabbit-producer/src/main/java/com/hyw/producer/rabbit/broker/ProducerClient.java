package com.hyw.producer.rabbit.broker;

import com.google.common.base.Preconditions;
import com.hyw.rabbit.api.Message;
import com.hyw.rabbit.api.MessageProducer;
import com.hyw.rabbit.api.MessageType;
import com.hyw.rabbit.api.SendCallback;
import com.hyw.rabbit.api.exception.MessageRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 10:49
 * Description：发送消息的实际实现类
 */
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        MessageType type = MessageType.of(message.getMessageType());
        switch (type) {
            case RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:break;
        }
    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {

        rabbitBroker.sendMessages(messages);
    }




}
