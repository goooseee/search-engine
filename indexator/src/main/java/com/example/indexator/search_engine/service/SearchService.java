package com.example.indexator.search_engine.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.indexator.entity.InternetPage;
import com.example.indexator.entity.Position;
import com.example.indexator.entity.WordOnPage;
import com.example.indexator.repository.InternetPageRepository;
import com.example.indexator.search_engine.repository.WordOnPageRepository2;

import com.example.indexator.search_engine.dto.SearchResponseDTO;
import com.example.indexator.service.InternetPageService;
import com.example.indexator.service.StemmingService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class SearchService {
	
	private final StemmingService stemmingService;
	
	private final WordOnPageRepository2 wordOnPageRepository;
	
	private final InternetPageService internetPageService;
	
	@Cacheable(value = "search", key = "#request")
	public List<SearchResponseDTO> doSearch(String request) {
		List<String> cleanWord = stemmingService.cleanFrom( request );
		Set<String> words = stemmingService.convertToMap( cleanWord ).keySet();
		if(words.isEmpty()) {
			return List.of();
		}
		
		long totalPage = internetPageService.count();
		
		return wordOnPageRepository
				.searchUrls( words, Math.max( totalPage, 1L ), PageRequest.of( 0, 20 ) )
				.stream()
				.map(SearchResponseDTO::new)
				.toList();
	}
	
}
