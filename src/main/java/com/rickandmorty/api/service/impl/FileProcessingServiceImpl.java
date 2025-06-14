package com.rickandmorty.api.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rickandmorty.api.model.CharacterEntry;
import com.rickandmorty.api.model.Character;
import com.rickandmorty.api.service.FileProcessingService;
import com.rickandmorty.api.service.RickAndMortyApiService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessingServiceImpl implements FileProcessingService {
	
	private final RickAndMortyApiService rickAndMortyApiService;
	
	public FileProcessingServiceImpl(RickAndMortyApiService rickAndMortyApiService) {
	    this.rickAndMortyApiService = rickAndMortyApiService;
	}

	@Override
	public void processFile(String filePath) {
		log.info("Initializing the file processing: {}", filePath);
		
		File file = new File(filePath);
		if(!file.exists()) {
			log.error("File not found: {}" + filePath);
			return;
		}
		
		Map<Integer, List<CharacterEntry>> episodeCharactersMap = new HashMap<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String headerLine = reader.readLine();
			if(headerLine == null) {
				log.warn("The file is empty");
				return;				
			}
			
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if(values.length < 4) {
					log.warn("Skipping malformed line: {}" + line);
					continue;
				}
				
				int episodeId = Integer.parseInt(values[0].trim());
			    int characterId = Integer.parseInt(values[1].trim());
			    String characterName = values[2].trim();
			    int locationId = Integer.parseInt(values[3].trim());
			    
			    CharacterEntry characterEntry = new CharacterEntry(characterId, characterName, locationId);
	    
			    episodeCharactersMap.computeIfAbsent(episodeId, k -> new ArrayList<>())
			    .add(characterEntry);
			    
			    log.info("Read entry - episodeId: {}, characterId: {}, characterName: {}, locationId: {}",
	                    episodeId, characterId, characterName, locationId); 	
				
			    Character character = rickAndMortyApiService.getASingleCharacter(characterId);
			    log.info("Fetched character from API: {}", character.getName());

			    
			}
			
			log.info("Finished reading CSV. Found {} episodes.", episodeCharactersMap.size());
			
			// Temporário: apenas listar os episódios com seus personagens
			episodeCharactersMap.forEach((epId, characters) -> {
			    log.info("Processing Episode {}", epId);

			    for (CharacterEntry entry : characters) {
			        try {
			            Character character = rickAndMortyApiService.getASingleCharacter(entry.getCharacterId());

			            String origin = character.getOrigin() != null ? character.getOrigin().getName() : "Unknown Origin";
			            String location = character.getLocation() != null ? character.getLocation().getName() : "Unknown Location";

			            log.info("Character ID {} - Name: {}, Origin: {}, Location: {}",
			                    character.getId(),
			                    character.getName(),
			                    origin,
			                    location);

			        } catch (Exception e) {
			            log.error("Failed to fetch character with ID {} from API", entry.getCharacterId(), e);
			        }
			    }
			});
			
		} catch (IOException e) {
	        log.error("Error reading file", e);
	    } catch (Exception e) {
	        log.error("Error processing CSV", e);
	    }
		
	}

}
