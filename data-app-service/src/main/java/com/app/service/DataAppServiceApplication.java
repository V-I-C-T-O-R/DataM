package com.app.service;

import com.app.service.config.ApplicationStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAsync
public class DataAppServiceApplication {
    private static Logger logger = LoggerFactory.getLogger(DataAppServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication =new SpringApplication(DataAppServiceApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
        logger.info("============= SpringBoot Start Success =============");
    }
}
