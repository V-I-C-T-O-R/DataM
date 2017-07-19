package com.app.service.config;

import com.app.service.common.AsyncTaskService;
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
        asyncTaskService.executeAsyncTask();
    }
}
