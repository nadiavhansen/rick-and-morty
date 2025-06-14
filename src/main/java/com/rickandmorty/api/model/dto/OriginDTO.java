package com.rickandmorty.api.model.dto;

import lombok.Data;

@Data
public class OriginDTO {
	
	private int id;	
	private String name;
	private String type;
	private String dimension;	
	private String url;

}
