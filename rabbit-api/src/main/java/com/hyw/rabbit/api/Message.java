package com.hyw.rabbit.api;

import com.hyw.rabbit.api.exception.MessageRunTimeException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/20 22:22
 * Description：TODO
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = -4622263531335967442L;

    /** 消息的唯一ID  */
    private final String messageId;

    /**	消息的主题		*/
    private final String topic;

    /**	消息的路由规则	*/
    private final String routingKey;

    /**	消息的附加属性	*/
    private final Map<String, Object> attributes;

    /**	延迟消息的参数配置	*/
    private final int delayMills;

    /**	消息类型：默认为confirm消息类型	*/
    private final Integer messageType;

    private Message(Builder builder) {
        this.messageId = builder.messageId;
        this.topic = builder.topic;
        this.routingKey = builder.routingKey;
        this.attributes = builder.attributes;
        this.delayMills = builder.delayMills;
        this.messageType = builder.messageType;
    }

    public static class Builder {

        private String messageId;
        private String topic;
        private String routingKey = "";
        private Map<String, Object> attributes = new HashMap<>();
        private int delayMills;
        private Integer messageType = MessageType.CONFIRM.getType();

        public Builder(String topic, String routingKey) {
            this.topic = topic;
            this.routingKey = routingKey;
        }

        public Builder withMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder withRoutingKey(String routingKey) {
            this.routingKey = routingKey;
            return this;
        }

        public Builder withAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder withAttribute(String key, Object value) {
            this.attributes.put(key, value);
            return this;
        }

        public Builder withDelayMills(int delayMills) {
            this.delayMills = delayMills;
            return this;
        }

        public Builder withMessageType(Integer messageType) {
            this.messageType = messageType;
            return this;
        }

        public Message build() {

            if (StringUtils.isEmpty(messageId)) {
                messageId = UUID.randomUUID().toString();
            }
            if (StringUtils.isEmpty(topic)) {
                throw new MessageRunTimeException("this topic is null");
            }
            Message message = new Message(this);
            return message;
        }

    }



}
