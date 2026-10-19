package com.example.indexator.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.indexator.DTO.WordsDTO;
import com.example.indexator.DTO.WordsResponseDTO;
import com.example.indexator.minIO.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexerService {
	
	private final CleanService cleanService;
	
	private final StemmingService stemmingService;
	
	private final AddToDBService dbService;
	
	private final StorageService storageService;
	
	public void parsePage(String url, String htmlKey) {
		try {
		String html = storageService.downloadHtml( htmlKey );
		WordsDTO dto = cleanService.cleanHTML( html );
		Set<String> title = stemmingService.cleanFrom( dto.top() );
		Set<String> text = stemmingService.cleanFrom( dto.text() );
		WordsResponseDTO responseDTO = new WordsResponseDTO( 
				stemmingService.convertToMap( title ), stemmingService.convertToMap( text ) );
		dbService.saveToBD( url, responseDTO );
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
