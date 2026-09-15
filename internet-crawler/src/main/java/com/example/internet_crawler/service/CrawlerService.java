package com.example.internet_crawler.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64.Decoder;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
@Service
public class CrawlerService {
	
	public HashSet<String> crawl(String url) throws IOException {
		String rule[] = {"Служебная:", "Википедия:", "Справка:", "Файл:", "Категория:", "?action=edit&redlink=1"};
		Document doc = Jsoup.connect( url ).get();
		Elements links = doc.select( "a[href]" );
		HashSet<String> lin = new HashSet<>(); 

		for(Element link : links) {
			String pureURL = URLDecoder.decode(link.absUrl( "href" ), StandardCharsets.UTF_8);
			if(link.attr( "href" ).startsWith( "#" ) || 
					Arrays.stream( rule ).anyMatch( badword -> pureURL.contains( badword ) )) {
				continue;
			}else {
				if(pureURL.contains( "https://ru.wikipedia.org/wiki/" )) {
					if(pureURL.contains( "#" )) {
						int index = pureURL.indexOf( "#" );
						lin.add(pureURL.substring( 0, index ) ) ;
					}else {
						lin.add( pureURL ) ;
					}
				}
			}
		}
		System.out.println( lin.size() );
		return lin;
	}

}
