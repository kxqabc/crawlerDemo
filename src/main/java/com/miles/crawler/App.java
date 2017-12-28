package com.miles.crawler;

import com.miles.crawler.controller.CrawlerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class App {
    @Autowired
    private CrawlerController crawlerController;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @PostConstruct
    public void task(){
        crawlerController.handle();
    }
}
