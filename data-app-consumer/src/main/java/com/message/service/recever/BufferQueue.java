package com.message.service.recever;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 17-6-29.
 */
public class BufferQueue {
    final BlockingDeque<String> switchQueue = new LinkedBlockingDeque<>(1000);

    public void produce(String message) {
        try {
            switchQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String consume() throws InterruptedException {
        return switchQueue.poll(1, TimeUnit.SECONDS);
    }
}
