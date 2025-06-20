package com.rickandmorty.api.service;

import java.io.FileReader;

public interface EpisodeCsvService {
	
	void process(FileReader fr);

}
