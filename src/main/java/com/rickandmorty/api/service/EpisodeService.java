package com.rickandmorty.api.service;

import java.util.List;

import com.rickandmorty.api.model.Episode;

public interface EpisodeService {
	
	Episode save(Episode episode);
    List<Episode> findAll();
    Episode findById(int id);
    void linkCharacterToEpisode(int episodeId, int characterId, String characterName, int locationId);
   
}
