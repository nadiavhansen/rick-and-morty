package com.rickandmorty.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rickandmorty.api.model.Character;

import com.rickandmorty.api.service.RickAndMortyApiService;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private RickAndMortyApiService rickAndMortyApiService;

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable int id) {
        Character character = rickAndMortyApiService.getASingleCharacter(id);
        return ResponseEntity.ok(character);
    }}
