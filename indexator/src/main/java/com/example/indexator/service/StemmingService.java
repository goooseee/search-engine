package com.example.indexator.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.ru.RussianLightStemFilter;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.ext.RussianStemmer;

import com.example.indexator.DTO.WordsDTO;
import com.example.indexator.DTO.WordsResponseDTO;
@Service
public class StemmingService {
	
	Set<String> stopWords = Set.of(
		    "и", "в", "во", "не", "что", "он", "на", "я", "с", "со", "как", 
		    "а", "то", "все", "она", "так", "его", "но", "да", "ты", "к", 
		    "у", "же", "вы", "за", "бы", "по", "только", "ее", "мне", "было", 
		    "вот", "от", "меня", "еще", "о", "из", "ему", "теперь", "когда",
		    "те", "для", "при", "под", "до", "об", "про", "через", "между",
		    "их", "им", "них", "ним", "него", "чем", "свой", "свои", "свое"
	);
	
	public Set<String> cleanFrom(String rawText) {
		String regex = "[^а-яёa-z\\s]";
		String text = rawText.toLowerCase().replaceAll( regex, " " );
		List<String> text1 = Arrays.asList(text.split( "\\s+" ));
		return new HashSet<>(text1);
	}
	
	public Map<String, Long> convertToMap(Set<String> text){
		RussianStemmer stemmer = new RussianStemmer();
		return text.stream().filter( w -> !stopWords.contains( w ) )
				.map( w -> {
					stemmer.setCurrent( w ); 
					stemmer.stem();
					return stemmer.getCurrent();
					})
				.filter( w-> w.length()>2)
				.collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ) );
	}
}
