package com.rickandmorty.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.model.Location;
import com.rickandmorty.api.model.Character;

import com.rickandmorty.api.repository.CharacterRepository;
import com.rickandmorty.api.repository.EpisodeRepository;
import com.rickandmorty.api.repository.LocationRepository;

import com.rickandmorty.api.service.EpisodeService;

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

	@Override
	public void linkCharacterToEpisode(int episodeId, int characterId, String characterName, int locationId) {
	    // 1. Buscar episódio existente
	    Episode episode = episodeRepository.findById(episodeId)
	        .orElseThrow(() -> new RuntimeException("Episode not found: " + episodeId));

	    // 2. Buscar personagem já salvo
	    Character character = characterRepository.findById(characterId)
	        .orElseThrow(() -> new RuntimeException("Character not found: " + characterId));

	    // 3. Atualizar location se necessário
	    Location location = locationRepository.findById(locationId)
	        .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
	    
	    character.setLocation(location);

	    // 4. Atualizar nome se por algum motivo estiver diferente (por segurança)
	    if (!character.getName().equals(characterName)) {
	        character.setName(characterName);
	    }

	    // 5. Adicionar personagem ao episódio (se ainda não estiver)
	    if (!episode.getCharacters().contains(character)) {
	        episode.getCharacters().add(character);
	    }

	    // 6. Adicionar episódio ao personagem (se ainda não estiver)
	    if (!character.getEpisodes().contains(episode)) {
	        character.getEpisodes().add(episode);
	    }

	    // 7. Salvar ambos
	    characterRepository.save(character);
	    episodeRepository.save(episode);
	}

}
