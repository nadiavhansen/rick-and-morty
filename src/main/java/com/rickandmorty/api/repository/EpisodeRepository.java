package com.rickandmorty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rickandmorty.api.model.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {

}
