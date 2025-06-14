package com.rickandmorty.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RickandmortyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickandmortyApiApplication.class, args);
	}

}
