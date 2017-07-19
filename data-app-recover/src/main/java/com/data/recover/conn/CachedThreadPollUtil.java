package com.data.recover.conn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Victor on 17-6-29.
 */
public class CachedThreadPollUtil {
    private static ExecutorService threadPool;
    private static List<String> messageQueue = new ArrayList<>();

    static {
        threadPool = Executors.newFixedThreadPool(Integer.parseInt(AttrHouse.getPropertiesValue("thread.pool.num")) + 1);
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
