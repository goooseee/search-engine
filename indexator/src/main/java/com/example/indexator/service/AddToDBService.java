package com.example.indexator.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.indexator.DTO.WordsResponseDTO;
import com.example.indexator.entity.InternetPage;
import com.example.indexator.entity.Word;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddToDBService {
	
	private final InternetPageService internetPageService;
	
	private final WordOnPageService wordOnPageService;
	
	private final WordService wordService;
	@Transactional
	public void saveToBD(String url, WordsResponseDTO dto, int amountOfWord) {
		InternetPage page = internetPageService.getOrCreatePage( url, amountOfWord);
		
		Map<String,Word> words = wordService.getOrCreate( dto );
		
		wordOnPageService.saveWordOnPage( page, dto, words );
	}
	
}
