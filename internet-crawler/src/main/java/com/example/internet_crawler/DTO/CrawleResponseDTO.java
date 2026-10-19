package com.example.internet_crawler.DTO;

import java.util.Set;

public record CrawleResponseDTO(String html, Set<String> links) {

}
