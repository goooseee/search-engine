package com.example.internet_crawler.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internet_crawler.service.CrawlerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CrawlerController {
	
	private final CrawlerService crawlerService;
	
	@GetMapping("/get")
	public String getFromPage() throws IOException{
		return crawlerService.crawl( "https://ru.wikipedia.org/wiki/Grand_Theft_Auto_Advance" );
	}
	
}
