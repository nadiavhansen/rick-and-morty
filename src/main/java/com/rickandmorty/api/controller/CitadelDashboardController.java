package com.rickandmorty.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rickandmorty.api.model.dto.CitadelDashboardDTO;
import com.rickandmorty.api.service.CitadelDashboardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CitadelDashboardController {
	
	private final CitadelDashboardService dashboardService;
	
	@GetMapping("/dashboard")
    public ResponseEntity<CitadelDashboardDTO> getDashboard() {
        CitadelDashboardDTO response = dashboardService.generateDashboard();
        return ResponseEntity.ok(response);
    }

}
