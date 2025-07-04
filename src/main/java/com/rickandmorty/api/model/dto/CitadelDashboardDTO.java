package com.rickandmorty.api.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitadelDashboardDTO {
	
    private List<EpisodeDashboardDTO> episodes;
    private int totalLocations;
    private int totalFemaleCharacters;
    private int totalMaleCharacters;
    private int totalGenderlessCharacters;
    private int totalGenderUnknownCharacters;
    private String uploadeFilePath;

}
