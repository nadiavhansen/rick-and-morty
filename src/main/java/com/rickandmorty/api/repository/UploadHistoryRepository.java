package com.rickandmorty.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rickandmorty.api.model.UploadHistory;

public interface UploadHistoryRepository extends JpaRepository<UploadHistory, Integer> {
	
	Optional<UploadHistory> findTopByOrderByCreatedTimestampDesc();

}
