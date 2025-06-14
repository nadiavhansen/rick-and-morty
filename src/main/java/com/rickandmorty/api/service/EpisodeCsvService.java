package com.rickandmorty.api.service;

import java.io.FileReader;

import org.springframework.web.multipart.MultipartFile;

public interface EpisodeCsvService {
	
	//void process(MultipartFile file);

	void process(FileReader fr);

}
