package com.hyw.producer.rabbit.constant;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/22 22:05
 * Description：TODO
 */
public enum BrokerMessageStatusEnum {
    /**
     *  已发送状态
     */
    SENDING("1"),
    /**
     * 发送成功状态
     */
    SEND_OK("2"),
    /**
     * 发送失败状态
     */
    SEND_FAIL("3"),
    /**
     * 发送失败，延迟发送
     */
    SEND_FAIL_A_MOMENT("4");

    private final String status;

    private BrokerMessageStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
