package com.rickandmorty.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.model.Location;
import com.rickandmorty.api.model.Origin;
import com.rickandmorty.api.model.dto.CharacterDTO;
import com.rickandmorty.api.model.dto.EpisodeDTO;
import com.rickandmorty.api.model.dto.LocationDTO;
import com.rickandmorty.api.repository.CharacterRepository;
import com.rickandmorty.api.repository.EpisodeRepository;
import com.rickandmorty.api.repository.LocationRepository;
import com.rickandmorty.api.repository.OriginRepository;
import com.rickandmorty.api.service.RickAndMortyApiService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RickAndMortyApiServiceImpl implements RickAndMortyApiService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	private final EpisodeRepository episodeRepository;
	private final LocationRepository locationRepository; 
	private final CharacterRepository characterRepository;
	private final OriginRepository originRepository;

    public RickAndMortyApiServiceImpl(EpisodeRepository episodeRepository, 
    		LocationRepository pointOfExistenceRepository, 
    		CharacterRepository characterRepository,
    		OriginRepository originRepository) {
        this.episodeRepository = episodeRepository;
		this.locationRepository = pointOfExistenceRepository;
		this.characterRepository = characterRepository;
		this.originRepository = originRepository;
    }


    @Override
    public Character getASingleCharacter(int id) {
    	
    	return characterRepository.findById(id).orElseGet(() -> {
	        String characterUrl = "https://rickandmortyapi.com/api/character/" + id;
	        CharacterDTO dto = restTemplate.getForObject(characterUrl, CharacterDTO.class);
	
	        if (dto == null) {
	            throw new RuntimeException("Character not found for id: " + id);
	        }
	
	        Origin origin = null;
	        if (dto.getOrigin() != null && dto.getOrigin().getUrl() != null && !dto.getOrigin().getUrl().isEmpty()) {
	            try {
	                origin = restTemplate.getForObject(dto.getOrigin().getUrl(), Origin.class);
	            } catch (Exception e) {
	                log.warn("Failed to fetch origin from URL: {}", dto.getOrigin().getUrl());
	            }
	        }
	
	        Location location = null;
	        if (dto.getLocation() != null && dto.getLocation().getUrl() != null && !dto.getLocation().getUrl().isEmpty()) {
	            try {
	                location = restTemplate.getForObject(dto.getLocation().getUrl(), Location.class);
	            } catch (Exception e) {
	                log.warn("Failed to fetch location from URL: {}", dto.getLocation().getUrl());
	            }
	        }
	
	        Character character = new Character();
	        character.setId(dto.getId());
	        character.setName(dto.getName());
	        character.setStatus(dto.getStatus());
	        character.setSpecies(dto.getSpecies());
	        character.setType(dto.getType());
	        character.setGender(dto.getGender());
	        character.setOrigin(origin);
	        character.setLocation(location);
	        character.setUrl(dto.getUrl());
	        
	        characterRepository.save(character);
	
	        return character;
	        
	    	});
		
	}


	@Override
	public Episode getASingleEpisode(int id) {
		
		String episodeUrl = "https://rickandmortyapi.com/api/episode/" + id;
		EpisodeDTO episodeDto = restTemplate.getForObject(episodeUrl, EpisodeDTO.class);

        if (episodeDto == null) {
            throw new RuntimeException("Episode not found for id: " + id);
        }
        
        Episode episode = new Episode();
        episode.setId(episodeDto.getId());
        episode.setName(episodeDto.getName());
        episode.setAirDate(episodeDto.getAirDate());
        episode.setEpisode(episodeDto.getEpisode());
        //episode.setCharacters(episodeDto.getCharacters());
        episode.setUrl(episodeDto.getUrl());
        
        episodeRepository.save(episode);
		
		return episode;
	}


	@Override
	public Location getASingleLocation(int id) {
		
		String locatioUrl = "https://rickandmortyapi.com/api/location/" + id;
		LocationDTO locationDto = restTemplate.getForObject(locatioUrl, LocationDTO.class);
		
		if (locationDto == null) {
            throw new RuntimeException("Location not found for id: " + id);
        }
		
		
		Location location = new Location();
		location.setId(locationDto.getId());
		location.setName(locationDto.getName());
		location.setDimension(locationDto.getDimension());
		location.setType(locationDto.getType());
		location.setUrl(locationDto.getUrl());
		
		locationRepository.save(location);
		
		return location;
	}

}
