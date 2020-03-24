package com.hyw.rabbit.api;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 10:33
 * Description：消费者监听消息
 */
public interface MessageListener {

    /**
     *  获取消息
     * @param message 消息内容
     */
    void onMessage(Message message);
}
