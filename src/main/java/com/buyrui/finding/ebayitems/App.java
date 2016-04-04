package com.buyrui.finding.ebayitems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class App {

    public static void main(String[] args) {
        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        SpringApplication.run(App.class, args);
    }
}
