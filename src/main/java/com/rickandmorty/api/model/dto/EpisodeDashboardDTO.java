package com.rickandmorty.api.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EpisodeDashboardDTO {
	
	 private int id;
	 private String name;
	 private String airDate;
	 private String episode;
	 private List<CharacterDashboardDTO> characters;
	 private String url;

}
