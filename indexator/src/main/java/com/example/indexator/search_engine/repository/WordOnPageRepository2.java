package com.example.indexator.search_engine.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.indexator.entity.WordOnPage;

public interface WordOnPageRepository2 extends JpaRepository<WordOnPage, Long>{
	@EntityGraph(attributePaths = {"internetPage", "word"})
	public List<WordOnPage> findAllByWord_WordIn(Set<String> words);
	
}
