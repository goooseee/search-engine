package com.example.internet_crawler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.internet_crawler.DTO.CrawleResponseDTO;
import com.example.internet_crawler.entity.InternetPage;
import com.example.internet_crawler.entity.Status;
import com.example.internet_crawler.kafka.KafkaProducerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlOrchestrator {
	
	private final CrawlerService crawlerService;
	
	private final InternetPageService internetPageService;
	
	private final KafkaProducerService kafkaProducerService;
	
	public Set<String> getNewUrl() throws IOException{
		InternetPage page = internetPageService.getFirst( Status.UNWATCHED );
		if (page == null) {
		    System.out.println("Нет ссылок для обхода!");
		    return null;
		}
		CrawleResponseDTO responseDTO = crawlerService.crawl( page.getUrl() );
		Set<String> links = responseDTO.links();
		if(page.getDepth()<2) {
			internetPageService.saveAll( links, page.getDepth()+1 );
		}else {
	        System.out.println("Достигнут предел глубины (2) для: " + page.getUrl() + ", новые ссылки не сохраняем.");
	    }
		page.setStatus( Status.WATCHED );
		internetPageService.update( page );
		
		kafkaProducerService.sendPage( page.getUrl(), responseDTO.html());
		
		return links;
	}
	@Scheduled(fixedDelay = 1000)
	public void crawl() {
		try {
			getNewUrl();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
