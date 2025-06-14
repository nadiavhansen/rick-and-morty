package com.rickandmorty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rickandmorty.api.model.Origin;

public interface OriginRepository extends JpaRepository<Origin, Integer>{

}
