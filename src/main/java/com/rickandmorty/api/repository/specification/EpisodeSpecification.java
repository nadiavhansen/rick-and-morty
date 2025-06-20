package com.rickandmorty.api.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.rickandmorty.api.model.Episode;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class EpisodeSpecification {
	
	public static Specification<Episode> hasEpisodeName(String episodeName) {
        return (root, query, cb) ->
            episodeName == null ? null :
            cb.like(cb.lower(root.get("name")), "%" + episodeName.toLowerCase() + "%");
    }

    public static Specification<Episode> hasCharacterName(String characterName) {
        return (root, query, cb) -> {
            if (characterName == null) return null;
            Join<Object, Object> characters = root.join("characters", JoinType.LEFT);
            return cb.like(cb.lower(characters.get("name")), "%" + characterName.toLowerCase() + "%");
        };
    }

    public static Specification<Episode> hasLocationName(String locationName) {
        return (root, query, cb) -> {
            if (locationName == null) return null;
            Join<Object, Object> characters = root.join("characters", JoinType.LEFT);
            Join<Object, Object> location = characters.join("location", JoinType.LEFT);
            return cb.like(cb.lower(location.get("name")), "%" + locationName.toLowerCase() + "%");
        };
    }

}
