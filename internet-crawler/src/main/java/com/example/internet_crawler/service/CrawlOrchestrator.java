package com.example.internet_crawler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.internet_crawler.DTO.CrawleResponseDTO;
import com.example.internet_crawler.entity.InternetPage;
import com.example.internet_crawler.entity.Status;
import com.example.internet_crawler.kafka.KafkaProducerService;
import com.example.internet_crawler.minIO.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CrawlOrchestrator {
	
	private final CrawlerService crawlerService;
	
	private final InternetPageService internetPageService;
	
	private final KafkaProducerService kafkaProducerService;
	
	private final StorageService storageService;
	
	public void getNewUrl(){
		List<InternetPage> internetPages = internetPageService.getFirst20( Status.UNWATCHED );
		if(internetPages.isEmpty()) {
			return;
		}
		try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			for(InternetPage page : internetPages) {
				executor.submit( () -> {
					try {
						CrawleResponseDTO responseDTO = crawlerService.crawl( page.getUrl() );
						Set<String> links = responseDTO.links();
						if(page.getDepth()<2) {
							internetPageService.saveAll( links, page.getDepth()+1 );
						}else {
					        System.out.println("Достигнут предел глубины (2) для: " + page.getUrl() + ", новые ссылки не сохраняем.");
					    }
						page.setStatus( Status.WATCHED );
						internetPageService.update( page );
						String key = storageService.uploadHTML( responseDTO.html() );
						kafkaProducerService.sendPage( page.getUrl(), key );
					} catch(Exception e) {
						e.printStackTrace();
						internetPageService.failed( page );
					}
				} );
			}
		}catch (Exception e) {
			log.error(e.getMessage());
		}
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
