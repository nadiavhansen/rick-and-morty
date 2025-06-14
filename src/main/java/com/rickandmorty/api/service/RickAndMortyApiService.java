package com.rickandmorty.api.service;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.model.Location;

public interface RickAndMortyApiService {
	
	Character getASingleCharacter(int id);
	
	Episode getASingleEpisode (int id);
	
	Location getASingleLocation (int id);

}
