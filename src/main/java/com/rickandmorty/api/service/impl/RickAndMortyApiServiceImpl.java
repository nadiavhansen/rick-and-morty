package com.rickandmorty.api.service.impl;

import java.util.List;
import java.util.Optional;

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
    		OriginRepository originRepository,
    		CharacterRepository characterRepository) {
        this.episodeRepository = episodeRepository;
		this.locationRepository = pointOfExistenceRepository;
		this.characterRepository = characterRepository;
		this.originRepository = originRepository;
		
    }


    @Override
    public Character getASingleCharacter(int id) {
    	
    	Optional<Character> existingCharacter = characterRepository.findById(id);
    	if (existingCharacter.isPresent()) {
    	    return existingCharacter.get();
    	}

        return characterRepository.findById(id).orElseGet(() -> {
            String characterUrl = "https://rickandmortyapi.com/api/character/" + id;
            CharacterDTO dto = restTemplate.getForObject(characterUrl, CharacterDTO.class);

            if (dto == null) {
                throw new RuntimeException("Character not found for id: " + id);
            }

            Origin origin = null;
            String originUrl = dto.getOrigin() != null ? dto.getOrigin().getUrl() : null;
            if (originUrl != null && !originUrl.isEmpty()) {
                int originId = extractIdFromUrl(originUrl);
                origin = originRepository.findById(originId).orElseGet(() -> {
                    Origin fetchedOrigin = restTemplate.getForObject(originUrl, Origin.class);
                    return originRepository.save(fetchedOrigin);
                });
            }

            Location location = null;
            String locationUrl = dto.getLocation() != null ? dto.getLocation().getUrl() : null;
            if (locationUrl != null && !locationUrl.isEmpty()) {
                int locationId = extractIdFromUrl(locationUrl);
                location = locationRepository.findById(locationId).orElseGet(() -> {
                    Location fetchedLocation = restTemplate.getForObject(locationUrl, Location.class);
                    return locationRepository.save(fetchedLocation);
                });
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
        Optional<Episode> existingEpisode = episodeRepository.findById(id);
        if (existingEpisode.isPresent()) {
            return existingEpisode.get();
        }

        String episodeUrl = "https://rickandmortyapi.com/api/episode/" + id;
        EpisodeDTO episodeDto = restTemplate.getForObject(episodeUrl, EpisodeDTO.class);

        if (episodeDto == null) {
            throw new RuntimeException("Episode not found for id: " + id);
        }

        List<Character> characters = episodeDto.getCharacters().stream()
            .map(url -> characterRepository.findByUrl(url)
                .orElseGet(() -> getASingleCharacter(extractIdFromUrl(url))))
            .toList();

        Episode episode = new Episode();
        episode.setId(episodeDto.getId());
        episode.setName(episodeDto.getName());
        episode.setAirDate(episodeDto.getAirDate());
        episode.setEpisode(episodeDto.getEpisode());
        episode.setCharacters(characters);
        episode.setUrl(episodeDto.getUrl());

        episodeRepository.save(episode);

        return episode;
    }
    
    private int extractIdFromUrl(String url) {
        String[] parts = url.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }


	@Override
	public Location getASingleLocation(int id) {
		
		Optional<Location> existingLocation = locationRepository.findById(id);
		if (existingLocation.isPresent()) {
		    return existingLocation.get();
		}

		
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
