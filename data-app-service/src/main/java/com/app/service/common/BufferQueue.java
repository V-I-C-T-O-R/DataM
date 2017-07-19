package com.app.service.common;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

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
        return switchQueue.take();
    }
}
