package com.rickandmorty.api.model;

import lombok.*;

@Data
@AllArgsConstructor
public class CharacterEntry {
	
    private int characterId;
    private String characterName;
    private int locationId;
    
}
