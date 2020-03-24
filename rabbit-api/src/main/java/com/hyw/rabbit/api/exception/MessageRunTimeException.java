package com.hyw.rabbit.api.exception;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/20 22:45
 * Description：TODO
 */
public class MessageRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -1931989244435966413L;

    public MessageRunTimeException() {
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
