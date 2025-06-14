package com.rickandmorty.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rickandmorty.api.model.ProcessStatus;

public interface ProcessStatusRepository extends JpaRepository<ProcessStatus, Integer>{
	
	Optional<ProcessStatus> findByFilePath(String filePath);
	
	Optional<ProcessStatus> findByFileName(String fileName);

	Optional<ProcessStatus> findById(Long statusId);
}
