package com.example.internet_crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InternetCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetCrawlerApplication.class, args);
	}

}
