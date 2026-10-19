package com.example.internet_crawler.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public void saveAll(Set<String> links, int depth) {
		List<InternetPage> pages = links.stream()
				.filter( link -> !internetPageRepository.existsByUrl( link ) )
				.map( link -> new InternetPage( link, depth ) )
				.toList();
		internetPageRepository.saveAll( pages );
	}
	
	public InternetPage getFirst(Status status) {
		return internetPageRepository.findFirstByStatus( status ).orElse( null );
	}
	@Transactional
	public List<InternetPage> getFirst20(Status status) {
		List<InternetPage> internetPages = internetPageRepository.findFirst20ByStatus( status );
		if(internetPages.isEmpty()) {
			return List.of();
		}
		List<Long> ids = internetPages.stream().map( InternetPage::getId ).toList();
		internetPageRepository.updateFieldForIds( Status.INPROCESS, ids );
		return internetPages;
	}
	
	public InternetPage update(InternetPage internetPage) {
		return internetPageRepository.save( internetPage );
	}
	
	public InternetPage failed(InternetPage internetPage) {
		internetPage.setStatus( Status.FAILED );
		return internetPageRepository.save( internetPage );
	}
}
