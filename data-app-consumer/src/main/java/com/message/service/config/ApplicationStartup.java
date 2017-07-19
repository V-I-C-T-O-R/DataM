package com.message.service.config;

import com.message.service.consumer.AsyncTaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by Victor on 17-6-30.
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        AsyncTaskService asyncTaskService = applicationContext.getBean(AsyncTaskService.class);
        int threadNum = Integer.parseInt(applicationContext.getEnvironment().getProperty("consumer.thread.num"));
        for (int i = 0; i < threadNum; i++) {
            asyncTaskService.executeAsyncTask(i);
        }
    }
}
