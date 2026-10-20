package com.example.indexator.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.indexator.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long>{
	
	//public Set<Word> findAllByWordIn(Set<String> words);
	
	@Query(value = """
			INSERT INTO word (word, amountOfPageWhere)
			SELECT w, 1 FROM unnest(cast(:words as text[])) AS w
			ON CONFLICT (word)
			DO UPDATE SET amountOfPageWhere = word.amount_of_page_where + 1
			RETURNING *
			""", nativeQuery = true)
	public List<Word> upsertWordIn(@Param("words") String[] words);
}
