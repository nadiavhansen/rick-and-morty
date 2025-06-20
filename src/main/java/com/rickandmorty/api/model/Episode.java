package com.rickandmorty.api.model;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "episodes")
public class Episode {
	
	@Id
	private int id;
	
	private String name;
	private String airDate;
	private String episode;
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable( name = "episode_character",
	        joinColumns = @JoinColumn(name = "episode_id"),
	        inverseJoinColumns = @JoinColumn(name = "character_id"))
	@JsonIgnore
	@Builder.Default
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Character> characters = new ArrayList<>();

	private String url;
	

}
