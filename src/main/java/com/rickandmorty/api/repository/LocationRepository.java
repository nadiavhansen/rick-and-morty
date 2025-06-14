package com.rickandmorty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rickandmorty.api.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{

}
