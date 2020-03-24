package com.hyw.rabbit.api;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 10:30
 * Description：回调函数处理
 */
public interface SendCallback {

    void onSuccess();

    void onFailure();
}
