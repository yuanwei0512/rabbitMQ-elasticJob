package com.hyw.producer.rabbit.broker;

import com.hyw.rabbit.api.Message;

import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 11:09
 * Description：具体发送不同种类型消息的接口
 */
public interface RabbitBroker {

    /**
     * 迅速消息
     */
    void rapidSend(Message message);

    /**
     * 带确认消息
     */
    void confirmSend(Message message);

    /**
     * 发送可靠消息
     */
    void reliantSend(Message message);

    /**
     *  批量发送
     */
    void sendMessages(List<Message> messages);
}
