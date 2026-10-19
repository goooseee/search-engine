package com.example.indexator.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.indexator.DTO.CrawlerEvent;
import com.example.indexator.service.IndexerService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class KafkaListenerService{
	
	private final IndexerService indexerService;
	@KafkaListener(topics = "crawled-page", groupId = "indexer-group")
	public void listen(CrawlerEvent event) {
		System.out.println( event.url() );
		
		indexerService.parsePage( event.url(), event.html() );
	}
	
}

