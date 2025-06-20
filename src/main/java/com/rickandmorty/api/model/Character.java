package com.rickandmorty.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Character {
	
	@Id
	private int id;
	
	private String name;
	private String status;
	private String species;
	private String type;
	private String gender;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_id")
	private Origin origin;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
	private Location location;
	
	@Builder.Default
	@ManyToMany(mappedBy = "characters", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Episode> episodes = new ArrayList<>();

	private String url;

}
