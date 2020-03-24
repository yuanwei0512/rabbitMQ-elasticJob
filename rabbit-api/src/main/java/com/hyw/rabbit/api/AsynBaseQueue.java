package com.hyw.rabbit.api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project：rabbit-parent     @author 源伟
 * DateTime：2020/3/21 11:27
 * Description：RabbitMQ发送端线程池实现异步发送
 */
@Slf4j
public class AsynBaseQueue {

    private static final int THREAD_SIEZ = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    private static ExecutorService senderAsync = new ThreadPoolExecutor(
            THREAD_SIEZ,
            THREAD_SIEZ,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
            new MyThreadFactory(),
            (r, executor) -> log.error("async sender is error rejected, runnable: {},executor:{}", r, executor)

    );


    static class MyThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    "rabbitmq_client_async_sender" +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY){
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }



}
