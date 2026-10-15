package com.example.internet_crawler.service;

import java.io.IOException;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.example.internet_crawler.entity.InternetPage;
import com.example.internet_crawler.entity.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlOrchestrator {
	
	private final CrawlerService crawlerService;
	
	private final InternetPageService internetPageService;
	
	public HashSet<String> getNewUrl() throws IOException{
		InternetPage page = internetPageService.getFirst( Status.UNWATCHED );
		if (page == null) {
		    System.out.println("Нет ссылок для обхода!");
		    return null;
		}
		HashSet<String> links = crawlerService.crawl( page.getUrl() );
		internetPageService.saveAll( links );
		page.setStatus( Status.WATCHED );
		internetPageService.update( page );
		return links;
	}
	
}
