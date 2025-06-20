package com.rickandmorty.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.model.Location;
import com.rickandmorty.api.model.Character;

import com.rickandmorty.api.repository.CharacterRepository;
import com.rickandmorty.api.repository.EpisodeRepository;
import com.rickandmorty.api.repository.LocationRepository;

import com.rickandmorty.api.service.EpisodeService;

import jakarta.transaction.Transactional;

@Service
public class EpisodeServiceImpl implements EpisodeService{

	private final EpisodeRepository episodeRepository;
	private final LocationRepository locationRepository; 
	private final CharacterRepository characterRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository, LocationRepository pointOfExistenceRepository, CharacterRepository characterRepository) {
        this.episodeRepository = episodeRepository;
		this.locationRepository = pointOfExistenceRepository;
		this.characterRepository = characterRepository;
    }
	
	@Override
	public Episode save(Episode episode) {
		
		return episodeRepository.save(episode);
	}

	@Override
	public List<Episode> findAll() {
		
		return episodeRepository.findAll();
	}

	@Override
	public Episode findById(int id) {
		
		return episodeRepository.findById(id).orElseThrow(() -> new RuntimeException("Episode not found with ID: " + id));
	}

	@Transactional
	@Override
	public void linkCharacterToEpisode(int episodeId, int characterId, String characterName, int locationId) {
	    Episode episode = episodeRepository.findById(episodeId)
	        .orElseThrow(() -> new RuntimeException("Episode not found: " + episodeId));

	    Character character = characterRepository.findById(characterId)
	        .orElseThrow(() -> new RuntimeException("Character not found: " + characterId));

	    Location location = locationRepository.findById(locationId)
	        .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
	    
	    character.setLocation(location);

	    if (!character.getName().equals(characterName)) {
	        character.setName(characterName);
	    }

	    boolean episodeAlreadyLinked = character.getEpisodes()
	        .stream()
	        .anyMatch(e -> e.getId() == episode.getId());

	    if (!episodeAlreadyLinked) {
	        character.getEpisodes().add(episode);
	    }

	    boolean characterAlreadyLinked = episode.getCharacters()
	        .stream()
	        .anyMatch(c -> c.getId() == character.getId());

	    if (!characterAlreadyLinked) {
	        episode.getCharacters().add(character);
	    }

	    characterRepository.save(character);
	}


}
