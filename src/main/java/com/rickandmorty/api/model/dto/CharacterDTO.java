package com.rickandmorty.api.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class CharacterDTO {
	
	private int id;	
	private String name;
	private String status;
	private String species;
	private String type;
	private String gender;
	private OriginDTO origin;
	private LocationDTO location;
	
	private List<String> episode;
	private String url;
	
	@Data
	public static class Origin {
		private String name;
        private String url;
	}
	
	@Data
	public static class Location {
        private String name;
        private String url;
    }

}
