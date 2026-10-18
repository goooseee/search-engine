package com.example.indexator.search_engine.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.indexator.search_engine.dto.SearchResponseDTO;
import com.example.indexator.search_engine.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchContoller {
	
	private final SearchService searchService;
	
	@GetMapping("/getUrl")
	public List<SearchResponseDTO> getUrl(@RequestParam("request") String request){
		return searchService.doSearch( request );
	}
	
}
