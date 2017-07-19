package com.message.service;

import com.message.service.config.ApplicationStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAsync
public class DataAppConsumerApplication {
    private static Logger logger = LoggerFactory.getLogger(DataAppConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication =new SpringApplication(DataAppConsumerApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
        logger.info("============= SpringBoot Start Success =============");
    }
}
