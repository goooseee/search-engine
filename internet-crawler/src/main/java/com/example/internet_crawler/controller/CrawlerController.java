package com.example.internet_crawler.controller;

import java.io.IOException;
import java.util.HashSet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.internet_crawler.service.CrawlOrchestrator;
import com.example.internet_crawler.service.CrawlerService;
import com.example.internet_crawler.service.InternetPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CrawlerController {
	
	private final CrawlOrchestrator crawlOrchestrator;
	private final InternetPageService internetPageService;
	
	@GetMapping("/get")
	public HashSet<String> getFromPage() throws IOException{
		HashSet<String> t = new HashSet<>();
		t.add( "https://ru.wikipedia.org/wiki/Grand_Theft_Auto_Advance" );
		//internetPageService.saveAll( t );
		return crawlOrchestrator.getNewUrl();
	}
	
}
