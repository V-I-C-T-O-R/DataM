package com.message.service.recever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * Created by Victor on 17-6-29.
 */
@Service
public class RedisInfoSubscriber implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(RedisInfoSubscriber.class);
    public static final BufferQueue queue = new BufferQueue();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        queue.produce(message.toString());
        logger.info("Message received: " + message.toString());
    }
}
