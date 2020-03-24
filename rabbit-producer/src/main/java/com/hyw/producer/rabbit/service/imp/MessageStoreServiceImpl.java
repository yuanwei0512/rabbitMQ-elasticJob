package com.hyw.producer.rabbit.service.imp;

import com.hyw.producer.rabbit.constant.BrokerMessageStatusEnum;
import com.hyw.producer.rabbit.entity.BrokerMessage;
import com.hyw.producer.rabbit.mapper.BrokerMessageMapper;
import com.hyw.producer.rabbit.service.MessageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/23 22:55
 * Description：TODO
 */
@Service
public class MessageStoreServiceImpl implements MessageStoreService {

    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    @Override
    public int insert(BrokerMessage brokerMessage) {
        return this.brokerMessageMapper.insert(brokerMessage);
    }

    @Override
    public List<BrokerMessage> fetchTimeOutMessage4Retry(BrokerMessageStatusEnum brokerMessageStatus) {
        return this.brokerMessageMapper.queryBrokerMessageStatus4Timeout(brokerMessageStatus.getStatus());
    }

    @Override
    public void failure(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId,
                BrokerMessageStatusEnum.SEND_FAIL.getStatus(),
                new Date());
    }

    @Override
    public int updateTryCount(String messageId) {
        return this.brokerMessageMapper.update4TryCount(messageId, new Date());
    }

    @Override
    public void succuess(String messageId) {
        this.brokerMessageMapper.changeBrokerMessageStatus(messageId,
                BrokerMessageStatusEnum.SEND_OK.getStatus(),
                new Date());
    }

    @Override
    public BrokerMessage selectByMessageId(String messageId) {
        return this.brokerMessageMapper.selectByPrimaryKey(messageId);
    }
}
