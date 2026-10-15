package com.example.indexator.DTO;

import java.util.Map;

public record WordsResponseDTO(
		Map<String, Integer> title,
		Map<String, Integer> text) {
}
