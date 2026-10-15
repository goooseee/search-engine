package com.example.indexator.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.indexator.DTO.WordsDTO;
import com.example.indexator.service.CleanService;
import com.example.indexator.service.StemmingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CleanController {
	
	private final CleanService cleanService;
	
	private final StemmingService stemmingService;
	
	@GetMapping("/get")
	public Map<String, Long> getCleanText() throws IOException{
		WordsDTO dto = cleanService.cleanHTML();
		return stemmingService.cleanFrom( dto );
	}
	
}
