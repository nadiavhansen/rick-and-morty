package com.rickandmorty.api.service;

import org.springframework.web.multipart.MultipartFile;

import com.rickandmorty.api.model.dto.UploadResponseDTO;

public interface UploadService {
	
	UploadResponseDTO handleFileUpload(MultipartFile file);
	
	public String getLastUploadedFilePath();

}
