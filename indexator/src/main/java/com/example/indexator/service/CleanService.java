package com.example.indexator.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.example.indexator.DTO.WordsDTO;

@Service
public class CleanService {
	
	public WordsDTO cleanHTML(String page) throws IOException{
		Document html = Jsoup.parse( page );
		html.select( "script, style, noscript, svg, iframe, template, meta, link, input, button, nav, li" ).remove();
		String top = html.select("#firstHeading").text();
		String text = html.select("#bodyContent").text();
		WordsDTO dto = new WordsDTO( top, text ); 
		return dto;
		
	}
	
}
