package com.hyw.rabbit.api;

import com.hyw.rabbit.api.exception.MessageRunTimeException;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/20 22:25
 * Description： 消息对应类型
 */
@Getter
public enum MessageType {
    /**
     *  迅速消息：不需要保障消息的可靠性, 也不需要做confirm确认
     */
    RAPID(1),
    /**
     *  确认消息：不需要保障消息的可靠性，但是会做消息的confirm确认
     */
    CONFIRM(2),
    /**
     * 可靠性消息： 一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
     * PS: 保障数据库和所发的消息是原子性的（最终一致的）
     */
    RELIANT(4);



    private final Integer Type;

    MessageType(Integer type) {
        Type = type;
    }

    /**
     * @return 对应枚举类型
     */
    public static MessageType of(Integer type) {
        Objects.requireNonNull(type);
        return Stream.of(values())
                .filter(bean -> ((bean.getType() & type)) == type)
                .findAny()
                .orElseThrow(() -> new MessageRunTimeException("this msg type is not found"));

    }

}
