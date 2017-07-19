package com.message.service.consumer;

import com.message.service.recever.RedisInfoSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Victor on 17-6-30.
 */
@Service
public class AsyncTaskService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CollectData collectData;

    @Async
    public void executeAsyncTask(Integer i) {
        logger.info("执行异步任务: " + i);
        collectData.poll(RedisInfoSubscriber.queue);
    }
}
