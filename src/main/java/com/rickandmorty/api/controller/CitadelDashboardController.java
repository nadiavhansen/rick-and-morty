package com.rickandmorty.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rickandmorty.api.mapper.DashboardMapper;
import com.rickandmorty.api.model.Episode;
import com.rickandmorty.api.model.dto.CitadelDashboardDTO;
import com.rickandmorty.api.model.dto.EpisodeDashboardDTO;
import com.rickandmorty.api.service.CitadelDashboardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CitadelDashboardController {
	
	private final CitadelDashboardService citadelDashboardService;
	
	@GetMapping("/dashboard")
    public ResponseEntity<CitadelDashboardDTO> getDashboard() {
        CitadelDashboardDTO response = citadelDashboardService.generateDashboard();
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/dashboard/search")
	public Page<EpisodeDashboardDTO> searchDashboard(
	        @RequestParam(required = false) String characterName,
	        @RequestParam(required = false) String episodeName,
	        @RequestParam(required = false) String locationName,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "ASC") String sortDirection,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Page<Episode> episodePage = citadelDashboardService.getDashboard(characterName, episodeName, locationName,
	                                                                      sortBy, sortDirection, page, size);
	    return episodePage.map(DashboardMapper::mapToEpisodeDashboardDTO);
	}

}
