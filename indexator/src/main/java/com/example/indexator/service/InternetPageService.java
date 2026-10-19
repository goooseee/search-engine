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
	
	public InternetPage getOrCreatePage(String url) {
		return getByUrl( url );
	}
	
	private InternetPage savePage(String Url) {
		InternetPage internetPage = new InternetPage( Url );
		return repository.save( internetPage );
	}
	
	private InternetPage getByUrl(String url) {
		return repository.findByUrl( url )
				.orElseGet(() -> savePage( url ) );
	}
	
	public void getFirstPage() {
		
	}
	
}
