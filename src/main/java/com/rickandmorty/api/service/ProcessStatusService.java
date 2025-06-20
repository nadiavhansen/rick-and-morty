package com.rickandmorty.api.service;

import java.util.List;

import com.rickandmorty.api.model.ProcessStatus;

public interface ProcessStatusService {
	
	List<ProcessStatus> getAllProcessStatuses();
	
}
