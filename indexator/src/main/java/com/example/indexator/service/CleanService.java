package com.example.indexator.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.example.indexator.DTO.WordsDTO;

@Service
public class CleanService {
	
	public WordsDTO cleanHTML(String url) throws IOException{
		Document html = Jsoup.connect( "https://ru.wikipedia.org/wiki/Grand_Theft_Auto_Advance" ).get();
		html.select( "script, style, noscript, svg, iframe, template, meta, link, input, button, nav, li" ).remove();
		String top = html.select("#firstHeading").text();
		String text = html.select("#bodyContent").text();
		WordsDTO dto = new WordsDTO( top, text ); 
		return dto;
		
	}
	
}
