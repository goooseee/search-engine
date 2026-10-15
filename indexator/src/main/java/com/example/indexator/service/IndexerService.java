package com.example.indexator.service;

import org.springframework.stereotype.Service;

import com.example.indexator.DTO.WordsDTO;
import com.example.indexator.DTO.WordsResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexerService {
	
	private final CleanService cleanService;
	
	private final StemmingService stemmingService;
	
	private final AddToDBService dbService;
	
	public void parsePage(String url) {
		try {
		WordsDTO dto = cleanService.cleanHTML( url );
		WordsResponseDTO responseDTO = stemmingService.cleanFrom( dto );
		dbService.saveToBD( url, responseDTO );
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
