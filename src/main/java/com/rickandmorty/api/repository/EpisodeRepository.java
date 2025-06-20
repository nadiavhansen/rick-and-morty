package com.rickandmorty.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.rickandmorty.api.model.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>, JpaSpecificationExecutor<Episode> {
	
	@EntityGraph(attributePaths = {
		    "characters",
		    "characters.origin",
		    "characters.location"
		})
		@Query("SELECT e FROM Episode e")
		List<Episode> findAllWithCharactersAndLocations();

}
