package com.hyw.producer.rabbit.service;

import com.hyw.producer.rabbit.constant.BrokerMessageStatusEnum;
import com.hyw.producer.rabbit.entity.BrokerMessage;

import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/22 21:55
 * Description：rabbitMQ消息落库对应业务逻辑层
 */
public interface MessageStoreService {



    int insert(BrokerMessage brokerMessage);


    List<BrokerMessage> fetchTimeOutMessage4Retry(BrokerMessageStatusEnum sending);

    void failure(String messageId);

    int updateTryCount(String messageId);

    void succuess(String messageId);

    BrokerMessage selectByMessageId(String messageId);
}
