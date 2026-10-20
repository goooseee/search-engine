package com.example.indexator.search_engine.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.indexator.entity.WordOnPage;
import com.example.indexator.search_engine.dto.SearchResponseDTO;

public interface WordOnPageRepository2 extends JpaRepository<WordOnPage, Long>{
//	@Query("""
//	        SELECT new com.example.indexator.search_engine.dto.SearchResponseDTO(p.url)
//	        FROM WordOnPage wop
//	        JOIN wop.internetPage p
//	        JOIN wop.word w
//	        WHERE w.word IN :words
//	        GROUP BY p.id, p.url
//	        ORDER BY SUM(wop.amount * CASE WHEN wop.position = com.example.indexator.entity.Position.TOP THEN 10 ELSE 1 END) DESC
//	    """)
//	    List<SearchResponseDTO> searchPagesByWords(
//	    		@Param("words") Set<String> words,
//	    		Pageable pageable);
	
	@Query(value = """
		    SELECT p.url
		    FROM word_on_page wop
		    JOIN internet_page p ON p.id = wop.internet_page_id
		    JOIN word w ON w.id = wop.word_id
		    WHERE w.word IN (:words)
		    GROUP BY p.id, p.url, p.amount_of_word
		    ORDER BY SUM(
		        (CAST(wop.amount AS double precision) / NULLIF(p.amount_of_word, 0))
		        * (CASE WHEN wop.position = 'TOP' THEN 5.0 ELSE 1.0 END)
		        * LN(1.0 + (CAST(:totalPages AS double precision) / w.amount_of_page_where))
		    ) DESC
		""", nativeQuery = true)
		List<String> searchUrls(
		    @Param("words") Set<String> words, 
		    @Param("totalPages") long totalPages, 
		    Pageable pageable
		);
	
}
