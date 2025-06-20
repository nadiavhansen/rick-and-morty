package com.rickandmorty.api.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EpisodeDTO {

	 private int id;
	 private String name;
	 @JsonProperty("air_date")
	 private String airDate;
	 private String episode;
	 private List<String> characters;
	 private String url;
	
}
