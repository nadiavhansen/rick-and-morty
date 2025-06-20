package com.rickandmorty.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	
	String handleFileUpload(MultipartFile file);
	
	public String getLastUploadedFilePath();

}
