package com.hyw.rabbit.api;

import com.hyw.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 10:31
 * Description：TODO
 */
public interface MessageProducer {

    /**
     * message消息的发送
     * @param message 消息
     * @throws MessageRunTimeException 当消息异常时
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 消息的批量发送
     * @param messages 消息
     * @throws MessageRunTimeException 当消息异常时
     */
    void send(List<Message> messages) throws MessageRunTimeException;




}
