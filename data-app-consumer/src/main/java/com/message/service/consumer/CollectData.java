package com.message.service.consumer;

import com.alibaba.fastjson.JSON;
import com.message.service.recever.BufferQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Victor on 17-6-29.
 */
@Component
public class CollectData {

    private Logger logger = LoggerFactory.getLogger(CollectData.class);
    @Autowired
    private DataHandle dataHandle;

    public void poll(BufferQueue queue) {
        String content = null;
        while (true) {
            try {
                content = queue.consume();
                if (content == null)
                    continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("start to resolve for table");
            dataHandle.resolveTable((Map<String, Object>) JSON.parse(content));
        }
    }
}
