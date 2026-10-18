package com.example.internet_crawler.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.internet_crawler.DTO.CrawlerEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
	
	private final KafkaTemplate<String, CrawlerEvent> kafkaTemplate;
	
	public void sendPage(String url, String html) {
		CrawlerEvent event = new CrawlerEvent( url, html );
		
		kafkaTemplate.send( "crawled-page", event );
	}
}
