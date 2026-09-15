package com.example.internet_crawler.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.internet_crawler.entity.InternetPage;
import com.example.internet_crawler.entity.Status;
import com.example.internet_crawler.repository.InternetPageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternetPageService {
	
	private final InternetPageRepository internetPageRepository;
	@Transactional
	public void saveAll(HashSet<String> links) {
		List<InternetPage> pages = links.stream()
				.filter( link -> !internetPageRepository.existsByUrl( link ) )
				.map( InternetPage::new )
				.toList();
		internetPageRepository.saveAll( pages );
	}
	
	public InternetPage getFirst(Status status) {
		return internetPageRepository.findFirstByStatus( status ).orElse( null );
	}
	
	public InternetPage update(InternetPage internetPage) {
		return internetPageRepository.save( internetPage );
	} 
}
