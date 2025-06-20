package com.rickandmorty.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.rickandmorty.api.model.*;
import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.model.dto.*;

public class DashboardMapper {

    public static EpisodeDashboardDTO mapToEpisodeDashboardDTO(Episode episode) {
        List<CharacterDashboardDTO> characterDTOs = episode.getCharacters().stream()
                .map(DashboardMapper::mapToCharacterDTO)
                .collect(Collectors.toList());

        return EpisodeDashboardDTO.builder()
                .id(episode.getId())
                .name(episode.getName())
                .airDate(episode.getAirDate())
                .episode(episode.getEpisode())
                .characters(characterDTOs)
                .build();
    }

    private static CharacterDashboardDTO mapToCharacterDTO(Character character) {
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

    private static LocationDTO mapToLocationDTO(Location location) {
        if (location == null) return null;
        return LocationDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .type(location.getType())
                .dimension(location.getDimension())
                .build();
    }

    private static OriginDTO mapToOriginDTO(Origin origin) {
        if (origin == null) return null;
        return OriginDTO.builder()
                .id(origin.getId())
                .name(origin.getName())
                .type(origin.getType())
                .dimension(origin.getDimension())
                .build();
    }
}
