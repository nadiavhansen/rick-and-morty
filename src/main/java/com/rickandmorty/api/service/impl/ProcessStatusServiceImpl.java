package com.rickandmorty.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rickandmorty.api.model.ProcessStatus;
import com.rickandmorty.api.repository.ProcessStatusRepository;
import com.rickandmorty.api.service.ProcessStatusService;

@Service
public class ProcessStatusServiceImpl implements ProcessStatusService{

    private final ProcessStatusRepository processStatusRepository;

    public ProcessStatusServiceImpl(ProcessStatusRepository processStatusRepository) {
        this.processStatusRepository = processStatusRepository;
    }

    @Override
    public List<ProcessStatus> getAllProcessStatuses() {
        return processStatusRepository.findAll();
    }

}
