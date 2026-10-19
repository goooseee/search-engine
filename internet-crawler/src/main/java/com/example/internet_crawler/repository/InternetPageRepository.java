package com.example.internet_crawler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.internet_crawler.entity.InternetPage;
import com.example.internet_crawler.entity.Status;

public interface InternetPageRepository extends JpaRepository<InternetPage, Long>{
	
	Optional<InternetPage> findFirstByStatus(Status status);
	
	List<InternetPage> findFirst20ByStatus(Status status);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE InternetPage e SET e.status = :newValue WHERE e.id IN :ids")
    int updateFieldForIds(@Param("newValue") Status status, @Param("ids") List<Long> ids);
	
	boolean existsByUrl(String url);
	
}
