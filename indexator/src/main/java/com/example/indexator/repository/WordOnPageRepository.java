package com.example.indexator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.indexator.entity.WordOnPage;

public interface WordOnPageRepository extends JpaRepository<WordOnPage, Long>{

}
