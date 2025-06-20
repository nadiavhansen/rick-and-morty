package com.rickandmorty.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rickandmorty.api.model.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer>{
	
	Optional<Character> findByUrl(String url);


}
