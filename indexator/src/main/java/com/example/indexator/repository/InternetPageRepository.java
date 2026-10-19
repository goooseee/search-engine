package com.example.indexator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.indexator.entity.InternetPage;

public interface InternetPageRepository extends JpaRepository<InternetPage, Long>{
	
	public Optional<InternetPage> findByUrl(String url);
	
}
