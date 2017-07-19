package com.app.service.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * Created by Victor on 17-6-29.
 */
@Service
public class RedisInfoPublisher implements InfoPublisher {
    private Logger logger = LoggerFactory.getLogger(RedisInfoPublisher.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public RedisInfoPublisher() {
    }

    public RedisInfoPublisher(StringRedisTemplate redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(String message) {
        logger.info("producer start send message");
        redisTemplate.convertAndSend(topic.getTopic(), message);
        logger.info("producer send message completed");
    }
}
