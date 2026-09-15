package com.example.internet_crawler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class InternetPage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, columnDefinition = "TEXT")
	private String url;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.UNWATCHED;
	
	public InternetPage(String url) {
		this.url = url;
	}
}
