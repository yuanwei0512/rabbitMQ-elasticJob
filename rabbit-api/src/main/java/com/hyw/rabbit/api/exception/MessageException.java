package com.hyw.rabbit.api.exception;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/20 22:45
 * Description：TODO
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = 3649304039478352716L;

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
