package com.rickandmorty.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.service.EpisodeService;
import com.rickandmorty.api.service.RickAndMortyApiService;

@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeService episodeService;
    
    @Autowired
    private RickAndMortyApiService rickAndMortyApiService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping
    public Episode createEpisode(@RequestBody Episode episode) {
        return episodeService.save(episode);
    }

    @GetMapping
    public List<Episode> getAllEpisodes() {
        return episodeService.findAll();
    }
    
    @GetMapping("/{id}")
    public Episode getEpisodeById(@PathVariable int id) {
        return rickAndMortyApiService.getASingleEpisode(id);
    }
}
