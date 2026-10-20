package com.example.indexator.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.indexator.entity.InternetPage;
import com.example.indexator.repository.InternetPageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InternetPageService {
	
	private final InternetPageRepository repository;	
	
	public InternetPage getOrCreatePage(String url, int amountOfWord) {
		return getByUrl( url, amountOfWord );
	}
	
	private InternetPage savePage(String Url, int amountOfWord) {
		InternetPage internetPage = new InternetPage( Url, amountOfWord );
		return repository.save( internetPage );
	}
	
	public long count() {
		return repository.count();
	}
	
	private InternetPage getByUrl(String url, int amountOfWord) {
		return repository.findByUrl( url )
				.orElseGet(() -> savePage( url, amountOfWord ) );
	}
	
	public void getFirstPage() {
		
	}
	
}
