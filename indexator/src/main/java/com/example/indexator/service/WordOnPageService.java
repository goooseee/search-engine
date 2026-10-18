package com.example.indexator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.indexator.DTO.WordsResponseDTO;
import com.example.indexator.entity.InternetPage;
import com.example.indexator.entity.Position;
import com.example.indexator.entity.Word;
import com.example.indexator.entity.WordOnPage;
import com.example.indexator.repository.WordOnPageRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class WordOnPageService {
	
	private final WordOnPageRepository repository;
	
	public void saveWordOnPage(InternetPage page, WordsResponseDTO dto, Map<String,Word> words) {
		List<WordOnPage> title = dto.title().entrySet().stream()
				.map( w -> {
					Word word = words.get( w.getKey() );
					return  WordOnPage
							.builder()
							.word( word  )
							.amount( w.getValue().intValue() )
							.position( Position.TOP )
							.internetPage( page )
							.build();
				})
				.toList();
		List<WordOnPage> text = dto.text().entrySet().stream()
				.map( w -> {
					Word word = words.get( w.getKey() );
					return  WordOnPage
							.builder()
							.word( word  )
							.amount( w.getValue().intValue() )
							.position( Position.TEXT )
							.internetPage( page )
							.build();
				})
				.toList();
		List<WordOnPage> allWordsOnPage = new ArrayList<>(title);
		allWordsOnPage.addAll( text );
		
		repository.saveAll( allWordsOnPage );
	}
	
}
