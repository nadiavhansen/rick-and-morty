package com.rickandmorty.api.model.dto;

import lombok.Data;

@Data
public class CharacterDashboardDTO {
	
	private int id;	
	private String name;
	private String status;
	private String species;
	private String type;
	private String gender;
	private OriginDTO origin;
	private LocationDTO location;

}
