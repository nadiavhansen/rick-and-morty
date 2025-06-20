package com.rickandmorty.api.service;

import org.springframework.data.domain.*;

import com.rickandmorty.api.model.dto.CitadelDashboardDTO;
import com.rickandmorty.api.model.Episode;

public interface CitadelDashboardService {
	
	CitadelDashboardDTO generateDashboard();

	Page<Episode> getDashboard(String characterName, String episodeName, String locationName, String sortBy,
			String sortDirection, int page, int size);

}
