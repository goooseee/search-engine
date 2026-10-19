package com.example.indexator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IndexatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexatorApplication.class, args);
	}

}
