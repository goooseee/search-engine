package com.example.indexator.search_engine.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.indexator.entity.InternetPage;
import com.example.indexator.entity.Position;
import com.example.indexator.entity.WordOnPage;
import com.example.indexator.search_engine.repository.WordOnPageRepository2;

import com.example.indexator.search_engine.dto.SearchResponseDTO;
import com.example.indexator.service.StemmingService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class SearchService {
	
	private final StemmingService stemmingService;
	
	private final WordOnPageRepository2 wordOnPageRepository;
	
	public List<SearchResponseDTO> doSearch(String request) {
		Set<String> cleanWord = stemmingService.cleanFrom( request );
		List<WordOnPage> wordsByPage = wordOnPageRepository.findAllByWord_WordIn( stemmingService.convertToMap( cleanWord ).keySet() );
		System.out.println("Запрос пользователя: " + request);
		System.out.println("Корни слов после стемминга: " + cleanWord);
		Map<InternetPage, Integer> pageByScore = wordsByPage
				.stream().collect( Collectors
						.groupingBy( WordOnPage::getInternetPage,
								Collectors.summingInt( w->
								w.getAmount()*(w.getPosition() == Position.TEXT ? 1:10))) );
		return pageByScore.entrySet().stream().sorted(Map.Entry.<InternetPage, Integer>comparingByValue().reversed())
				.map( w ->{
					return new SearchResponseDTO( w.getKey().getUrl() );
				}).toList();
	}
	
}
