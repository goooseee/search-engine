package com.example.internet_crawler.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internet_crawler.entity.InternetPage;
import com.example.internet_crawler.entity.Status;

public interface InternetPageRepository extends JpaRepository<InternetPage, Long>{
	
	Optional<InternetPage> findFirstByStatus(Status status);
	
	boolean existsByUrl(String url);
	
}
