package com.example.indexator.DTO;

import java.util.Map;

public record WordsResponseDTO(
		Map<String, Long> title,
		Map<String, Long> text) {
}
