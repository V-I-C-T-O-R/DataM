package com.app.service.config;

import com.app.service.producer.InfoPublisher;
import com.app.service.producer.RedisInfoPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Victor on 17-6-29.
 */
@Configuration
public class RedisConfig {
    Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.pool.max-active}")
    private int maxCreate;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${redis.queues.topic}")
    private String topic;

    @Bean
    public StringRedisTemplate redisTemplate() {
        logger.debug("start to load redisTemplate");
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnFactory());
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnFactory() {
        logger.debug("start to load jedisConnFactory");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxCreate);
        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory(jedisPoolConfig);
        jedisConnFactory.setDatabase(database);
        jedisConnFactory.setHostName(host);
        jedisConnFactory.setPort(port);
        jedisConnFactory.setPassword(password);
        jedisConnFactory.setTimeout(timeout);
        return jedisConnFactory;
    }

    @Bean
    InfoPublisher redisPublisher() {
        return new RedisInfoPublisher(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(topic);
    }
}
