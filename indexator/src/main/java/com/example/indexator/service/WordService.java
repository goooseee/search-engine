package com.example.indexator.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.indexator.DTO.WordsResponseDTO;
import com.example.indexator.entity.Word;
import com.example.indexator.repository.WordRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WordService {
	
	private final WordRepository repository;
	
	public Map<String,Word> getOrCreate(WordsResponseDTO dto){
		Set<String> allWord = new HashSet<>( dto.title().keySet() );
		allWord.addAll( dto.text().keySet() );
		
		Set<Word> allWordFromDB = getAllWordInSet( allWord );
		Set<String> inBDWord = allWordFromDB.stream().map( Word::getWord ).collect( Collectors.toSet() );
		Set<String> restWord = new HashSet<>(allWord);
		restWord.removeAll( inBDWord );
		Set<Word> restWordFromDB = saveWords( restWord );
		Set<Word> result = new HashSet<>(allWordFromDB);
		result.addAll( restWordFromDB );
		return result.stream().collect( Collectors.toMap( Word::getWord, Function.identity() ) );
	}
	
	public Set<Word> getAllWordInSet(Set<String> words){
		return repository.findAllByWordIn( words );
	}
	
	public Set<Word> saveWords(Set<String> words) {
		List<Word> saveWord = words.stream().map( Word::new ).collect( Collectors.toList() );
		return new HashSet<>(repository.saveAll( saveWord ));
	}
}
