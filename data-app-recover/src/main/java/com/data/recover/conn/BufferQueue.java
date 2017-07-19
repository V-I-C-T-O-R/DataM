package com.data.recover.conn;

import com.data.recover.ApplicationService;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Victor on 17-6-29.
 */
public class BufferQueue {
    static final BlockingDeque<String> switchQueue = new LinkedBlockingDeque<>(1000);

    public static void produce(String message) {
        try {
            switchQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String consume() {
        return switchQueue.poll();
    }
}
