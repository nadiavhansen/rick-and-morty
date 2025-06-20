package com.rickandmorty.api.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDTO {
	
	private int id;	
	private String name;
	private String type;
	private String dimension;	
	private String url;

}
