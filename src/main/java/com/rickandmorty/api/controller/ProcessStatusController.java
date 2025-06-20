package com.rickandmorty.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rickandmorty.api.model.ProcessStatus;
import com.rickandmorty.api.service.ProcessStatusService;

@RestController
@RequestMapping("/process-status")
public class ProcessStatusController {
	
	private final ProcessStatusService processStatusService;

    public ProcessStatusController(ProcessStatusService processStatusService) {
        this.processStatusService = processStatusService;
    }

    @GetMapping
    public List<ProcessStatus> getAllStatuses() {
        return processStatusService.getAllProcessStatuses();
    }

}
