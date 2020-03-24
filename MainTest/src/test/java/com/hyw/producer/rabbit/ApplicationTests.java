package com.hyw.producer.rabbit;

import com.hyw.producer.rabbit.broker.ProducerClient;
import com.hyw.producer.rabbit.constant.BrokerMessageStatusEnum;
import com.hyw.rabbit.api.Message;
import com.hyw.rabbit.api.MessageType;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/24 9:37
 * Description：TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ProducerClient producerClient;

    @Test
    public void testProducerClient() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message.Builder("exchange-1", "springboot.abc")
                    .withMessageId(uniqueId)
                    .withAttributes(attributes)
                    .withMessageType(MessageType.RELIANT.getType())
                    .withDelayMills(5000).build();
            producerClient.send(message);
        }
        Thread.sleep(100000);
    }

}
