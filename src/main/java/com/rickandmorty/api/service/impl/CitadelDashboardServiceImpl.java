package com.rickandmorty.api.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.model.Location;
import com.rickandmorty.api.model.Origin;
import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.dto.CharacterDashboardDTO;
import com.rickandmorty.api.model.dto.CitadelDashboardDTO;
import com.rickandmorty.api.model.dto.EpisodeDashboardDTO;
import com.rickandmorty.api.model.dto.LocationDTO;
import com.rickandmorty.api.model.dto.OriginDTO;
import com.rickandmorty.api.repository.CharacterRepository;
import com.rickandmorty.api.repository.EpisodeRepository;
import com.rickandmorty.api.repository.LocationRepository;
import com.rickandmorty.api.repository.specification.EpisodeSpecification;
import com.rickandmorty.api.service.CitadelDashboardService;
import com.rickandmorty.api.service.UploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitadelDashboardServiceImpl implements CitadelDashboardService{

	private final EpisodeRepository episodeRepository;
    private final CharacterRepository characterRepository;
    private final LocationRepository locationRepository;
    private final UploadService uploadService;

    public CitadelDashboardDTO generateDashboard() {
        List<Episode> episodes = episodeRepository.findAllWithCharactersAndLocations(); 
        List<EpisodeDashboardDTO> episodeDTOs = episodes.stream().map(this::mapToEpisodeDashboardDTO).toList();

        List<Character> allCharacters = characterRepository.findAll();
        long totalLocations = locationRepository.count();
        long totalFemale = allCharacters.stream().filter(c -> "Female".equalsIgnoreCase(c.getGender())).count();
        long totalMale = allCharacters.stream().filter(c -> "Male".equalsIgnoreCase(c.getGender())).count();
        long totalGenderless = allCharacters.stream().filter(c -> "Genderless".equalsIgnoreCase(c.getGender())).count();
        long totalUnknown = allCharacters.stream().filter(c -> "unknown".equalsIgnoreCase(c.getGender())).count();

        String uploadedFilePath = uploadService.getLastUploadedFilePath();

        return CitadelDashboardDTO.builder()
                .episodes(episodeDTOs)
                .totalLocations((int) totalLocations)
                .totalFemaleCharacters((int) totalFemale)
                .totalMaleCharacters((int) totalMale)
                .totalGenderlessCharacters((int) totalGenderless)
                .totalGenderUnknownCharacters((int) totalUnknown)
                .uploadeFilePath(uploadedFilePath)
                .build();
    }

    private EpisodeDashboardDTO mapToEpisodeDashboardDTO(Episode episode) {
        List<CharacterDashboardDTO> characterDTOs = episode.getCharacters().stream()
                .map(this::mapToCharacterDTO)
                .toList();

        return EpisodeDashboardDTO.builder()
                .id(episode.getId())
                .name(episode.getName())
                .airDate(episode.getAirDate())
                .episode(episode.getEpisode())
                .characters(characterDTOs)
                .build();
    }

    private CharacterDashboardDTO mapToCharacterDTO(Character character) {
        return CharacterDashboardDTO.builder()
                .id(character.getId())
                .name(character.getName())
                .status(character.getStatus())
                .species(character.getSpecies())
                .type(character.getType())
                .gender(character.getGender())
                .origin(mapToOriginDTO(character.getOrigin()))
                .location(mapToLocationDTO(character.getLocation()))
                .build();
    }

    private LocationDTO mapToLocationDTO(Location location) {
    	if (location == null) {
            return null;
        }    	
        return LocationDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .dimension(location.getDimension())
                .build();
    }
    
    private OriginDTO mapToOriginDTO(Origin origin) {
    	if (origin == null) {
            return null;
        }
        return OriginDTO.builder()
                .id(origin.getId())
                .name(origin.getName())
                .type(origin.getType())
                .dimension(origin.getDimension())
                .build();
    }

	@Override
	public Page getDashboard(String characterName, String episodeName, String locationName, String sortBy,
			String sortDirection, int page, int size) {
		Specification<Episode> spec = Specification.where(EpisodeSpecification.hasEpisodeName(episodeName))
		        .and(EpisodeSpecification.hasCharacterName(characterName))
		        .and(EpisodeSpecification.hasLocationName(locationName));

		    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
		    Pageable pageable = PageRequest.of(page, size, sort);

		    return episodeRepository.findAll(spec, pageable);
	}

}
