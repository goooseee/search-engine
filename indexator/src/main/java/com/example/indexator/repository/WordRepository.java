package com.example.indexator.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.indexator.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long>{
	
	public Set<Word> findAllByWordIn(Set<String> words);
}
